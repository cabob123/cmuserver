package com.formssi.cas.config;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration("CasOverlayOverrideConfiguration")
@EnableConfigurationProperties(CasConfigurationProperties.class)
@ComponentScan("com.formssi.cas")
public class CasOverlayOverrideConfiguration {

    static {
        System.out.println("=====================================================");
    }

}
