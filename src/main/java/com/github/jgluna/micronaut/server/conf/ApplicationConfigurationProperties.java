package com.github.jgluna.micronaut.server.conf;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("application")
public class ApplicationConfigurationProperties {

    private final Integer DEFAULT_MAX = 10;

    private Integer max = DEFAULT_MAX;

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        if(max != null) {
            this.max = max;
        }
    }
}
