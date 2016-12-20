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

import com.yqboots.actuator.web.form.ApplicationForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Manages the applications.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public interface ApplicationManager {
    /**
     * Gets the health information of registered applications.
     *
     * @param filter   filter
     * @param pageable pageable
     * @return page of ApplicationForm
     */
    Page<ApplicationForm> getApplications(String filter, Pageable pageable);

    /**
     * Gets the health information of the specified application.
     *
     * @param id application id
     * @return ApplicationForm
     */
    ApplicationForm getApplication(Long id);

    /**
     * Gets the health details of the specified application.
     *
     * @param id application id
     * @return ApplicationForm
     */
    ApplicationForm getApplicationDetails(Long id);

    /**
     * New/Update the application information.
     *
     * @param form form
     */
    void updateApplication(ApplicationForm form);

    /**
     * Removes application by id.
     *
     * @param id application id
     */
    void removeApplication(Long id);
}
