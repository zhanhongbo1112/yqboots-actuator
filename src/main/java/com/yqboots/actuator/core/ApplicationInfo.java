package com.yqboots.actuator.core;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The application information.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Entity
@Table(name = "ACT_APPLICATION_INFO")
public class ApplicationInfo extends AbstractPersistable<Long> {
    private String name;
    private String path;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }
}
