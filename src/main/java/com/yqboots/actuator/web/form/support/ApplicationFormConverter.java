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
package com.yqboots.actuator.web.form.support;

import com.yqboots.actuator.web.form.ApplicationForm;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;

/**
 * Convert health info to {@link com.yqboots.actuator.web.form.ApplicationForm}.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class ApplicationFormConverter implements Converter<Map, ApplicationForm> {
    private static final String KEY_STATUS = "status";
    private static final String KEY_DISK_SPACE = "diskSpace";
    private static final String KEY_DB = "db";

    /**
     * {@inheritDoc}
     */
    @Override
    public ApplicationForm convert(final Map source) {
        final ApplicationForm result = new ApplicationForm();
        result.setStatus((String) source.get(KEY_STATUS));

        if (source.containsKey(KEY_DISK_SPACE)) {
            final Map diskSpace = (Map) source.get(KEY_DISK_SPACE);
            long total = (long) diskSpace.get("total");
            long free = (long) diskSpace.get("free");
            result.setStatusOfDiskSpace((String) diskSpace.get(KEY_STATUS));
            result.setDiskSpace(Double.toString((total - free) * 100 / total));
        }

        if (source.containsKey(KEY_DB)) {
            final Map db = (Map) source.get(KEY_DB);
            result.setStatusOfDatabase((String) db.get(KEY_STATUS));
            result.setDatabase((String) db.get("database"));
        }

        return result;
    }
}
