package com.virtual.powerplant.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableMongoTestServer
@ComponentScan(basePackages = { "com.virtual.powerplant" }, excludeFilters = {
        @ComponentScan.Filter(classes = { SpringBootApplication.class }) })
public class TestConfiguration {
}
