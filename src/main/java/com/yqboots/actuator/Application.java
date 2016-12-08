package com.yqboots.actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The entrance of a project.
 * it contains a main method to bootstrap the whole project.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@ComponentScan(basePackages = {"com.yqboots"})
@EntityScan(basePackages = {"com.yqboots"})
@EnableJpaRepositories(basePackages = {"com.yqboots"})
@SpringBootApplication(scanBasePackages = {"com.yqboots"}, exclude = {SecurityAutoConfiguration.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
