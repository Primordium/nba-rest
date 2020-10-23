package com.hro.exercise.nbachallenge.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Configuration Class
 * If need to change what profile is running
 * @see {src\main\resources\application.properties}
 */
@Configuration
@ConfigurationProperties("spring.datasource")
public class DBConfiguration {

    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private static final Logger LOG = LoggerFactory.getLogger(DBConfiguration.class);


    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Profile("dev")
    @Bean
    public String devDatabaseConnection() {
        LOG.info("LOADING DEVELOPMENT PROFILE");
        LOG.info("DB connection for DEV - H2");
        LOG.info(driverClassName);
        LOG.info(url);

        return "DB connection for DEV - H2";
    }

    @Profile("prod")
    @Bean
    public String prodDatabaseConnection() {
        LOG.info("LOADING PRODUCTION PROFILE");
        LOG.info("DB Connection to RDS_PROD - High Performance Instance");
        LOG.info(driverClassName);
        LOG.info(url);

        return "DB Connection to RDS_PROD - High Performance Instance";
    }
}
