package com.nddcoder.demorabbitmq.conf

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import groovy.transform.CompileStatic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@CompileStatic
@Configuration
class AppConfig {

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper().with {
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
        }
    }
}
