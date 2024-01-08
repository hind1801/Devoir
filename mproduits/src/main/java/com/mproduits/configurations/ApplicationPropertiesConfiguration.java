package com.mproduits.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("mes-configs")
@RefreshScope
@Getter
@Setter
public class ApplicationPropertiesConfiguration {

    private int limitDeProduits;
}
