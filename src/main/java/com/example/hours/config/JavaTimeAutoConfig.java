package com.example.hours.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 字符串转 LocalDateTime 配置类
 */
@Configuration
@ConditionalOnClass(JavaTimeModule.class)
public class JavaTimeAutoConfig {

    @Bean
    @ConditionalOnProperty("spring.jackson.date-format")
    public Jackson2ObjectMapperBuilderCustomizer customizeLocalDateTimeFormat(@Value("${spring.jackson.date-format}") String dateFormat) {
        return jacksonObjectMapperBuilder -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

            jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
            jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        };
    }
}
