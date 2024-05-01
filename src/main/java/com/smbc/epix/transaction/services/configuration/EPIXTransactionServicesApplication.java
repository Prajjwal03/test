package com.smbc.epix.transaction.services.configuration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

@Profile("!development")
@SpringBootApplication
@ComponentScan(basePackages = "com.smbc.epix")
@Configuration
@EnableCaching
@ImportResource({"classpath*:applicationContext-application-transactionServices.xml"})
public class EPIXTransactionServicesApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EPIXTransactionServicesApplication.class);
    }
}
