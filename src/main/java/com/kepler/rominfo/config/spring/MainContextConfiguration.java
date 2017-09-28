package com.kepler.rominfo.config.spring;

import com.kepler.rominfo.support.ApplicationConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.kepler.rominfo")
public class MainContextConfiguration {

    @Bean
    public ApplicationConstants applicationConstants() {
        return ApplicationConstants.getInstance();
    }

}
