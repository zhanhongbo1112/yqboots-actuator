/*
 *
 *  * Copyright 2015-2016 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.yqboots.actuator.web;

import com.yqboots.actuator.core.Application;
import com.yqboots.actuator.core.repository.ApplicationRepository;
import com.yqboots.actuator.web.form.ApplicationForm;
import com.yqboots.actuator.web.form.support.ApplicationFormConverter;
import com.yqboots.core.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link ApplicationManager}.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Service
public class ApplicationManagerImpl implements ApplicationManager {
    private static final String ENDPOINT_INFO = "/info";
    private static final String ENDPOINT_CONFIGPROPS = "/configprops";
    private static final String ENDPOINT_DUMP = "/dump";
    private static final String ENDPOINT_HEALTH = "/health";
    private static final String ENDPOINT_MAPPINGS = "/mappings";
    private static final String ENDPOINT_METRICS = "/metrics";
    private static final String ENDPOINT_BEANS = "/beans";
    private static final String ENDPOINT_ENV = "/env";
    private static final String ENDPOINT_AUTOCONFIG = "/autoconfig";
    private static final String ENDPOINT_TRACE = "/trace";

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ApplicationRepository applicationRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ApplicationForm> getApplications(final String filter, final Pageable pageable) {
        final Page<Application> applications = applicationRepository.findByNameLike(DBUtils.wildcard(filter), pageable);

        final List<ApplicationForm> results = new ArrayList<>();
        for (Application application : applications) {
            final Map health = restTemplate.getForObject(application.getPath() + ENDPOINT_HEALTH, Map.class);
            final ApplicationForm form = new ApplicationFormConverter().convert(health);
            form.setId(application.getId());
            form.setName(application.getName());
            form.setPath(application.getPath());
            results.add(form);
        }

        return new PageImpl<>(results, pageable, applications.getTotalElements());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApplicationForm getApplication(final Long id) {
        final Application application = applicationRepository.findOne(id);

        final Map health = restTemplate.getForObject(application.getPath() + ENDPOINT_INFO, Map.class);

        final ApplicationForm result = new ApplicationFormConverter().convert(health);
        result.setId(application.getId());
        result.setName(application.getName());
        result.setPath(application.getPath());

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateApplication(final ApplicationForm form) {
        final Application application = new Application(form.getId());
        application.setPath(form.getPath());
        application.setName(form.getName());

        applicationRepository.save(application);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeApplication(final Long id) {
        applicationRepository.delete(id);
    }
}
