package com.tw.prograd.image.storage.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("storage")
@Configuration
public class StorageProperties {

    private String location;

    private boolean initialize;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isInitialize() {
        return initialize;
    }

    public void setInitialize(boolean initialize) {
        this.initialize = initialize;
    }
}
