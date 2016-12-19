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
package com.yqboots.actuator.web.form;

import com.yqboots.actuator.core.Application;

/**
 * Form for {@link com.yqboots.actuator.core.Application}.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class ApplicationForm extends Application {
    private String status;

    private String diskSpace;

    private String statusOfDiskSpace;

    private String database;

    private String statusOfDatabase;

    @Override
    public void setId(final Long id) {
        super.setId(id);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getDiskSpace() {
        return diskSpace;
    }

    public void setDiskSpace(final String diskSpace) {
        this.diskSpace = diskSpace;
    }

    public String getStatusOfDiskSpace() {
        return statusOfDiskSpace;
    }

    public void setStatusOfDiskSpace(final String statusOfDiskSpace) {
        this.statusOfDiskSpace = statusOfDiskSpace;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(final String database) {
        this.database = database;
    }

    public String getStatusOfDatabase() {
        return statusOfDatabase;
    }

    public void setStatusOfDatabase(final String statusOfDatabase) {
        this.statusOfDatabase = statusOfDatabase;
    }
}
