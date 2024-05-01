package com.smbc.epix.transaction.services.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

@Profile("development")
@SpringBootApplication
@ComponentScan(basePackages = "com.smbc.epix")
@Configuration
@EnableCaching
@ImportResource({"classpath*:applicationContext-application-transactionServices.xml"})
public class EPIXTransactionServicesDevApplication {

    public static void main(String[] args) {
        SpringApplication.run(EPIXTransactionServicesDevApplication.class, args);
    }
}
