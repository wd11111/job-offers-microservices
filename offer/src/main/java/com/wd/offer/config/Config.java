package com.wd.offer.config;

import com.wd.exceptionhandler.handler.ExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ExceptionHandler exceptionHandler() {
        return new ExceptionHandler();
    }
}
