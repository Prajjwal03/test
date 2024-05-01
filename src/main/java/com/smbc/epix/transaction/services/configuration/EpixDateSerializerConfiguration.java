package com.smbc.epix.transaction.services.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class EpixDateSerializerConfiguration {

    @Bean
    @Primary
    public ObjectMapper objectMapper(CustomSqlDateSerializer customSqlDateSerializer) {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        module.addSerializer(java.sql.Date.class, customSqlDateSerializer);
        objectMapper.registerModule(module);
        return objectMapper;
    }
}
