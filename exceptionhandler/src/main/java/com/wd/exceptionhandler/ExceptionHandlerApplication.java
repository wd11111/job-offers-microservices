package com.wd.exceptionhandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ExceptionHandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExceptionHandlerApplication.class, args);
    }
}
