package com.wd.offer.config;

import com.wd.exceptionhandler.handler.Handler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Handler exceptionHandler() {
        return new Handler();
    }
}
