package com.library.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDateTime;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Configuration
@Slf4j
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, FALSE);
        mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, TRUE);
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, TRUE);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, FALSE);
        mapper.registerModule(new ParameterNamesModule());
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(customDoubleDeserialization());
        mapper.registerModule(getCustomLocalDateTimeSerializationModule());
        mapper.addHandler(deserializationHandler());
        return mapper;
    }

    SimpleModule getCustomLocalDateTimeSerializationModule() {
        final var module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);
        module.addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
        return module;
    }

    @Bean
    SimpleModule customDoubleDeserialization() {
        return new SimpleModule(
                "DoubleCustomDeserializer", new Version(1, 0, 0, null))
                .addDeserializer(Double.class, new JsonDeserializer<>() {
                    @Override
                    public Double deserialize(JsonParser jp, DeserializationContext context) throws IOException {
                        String valueAsString = jp.getValueAsString();
                        return isEmpty(valueAsString) ? null : Double.parseDouble(valueAsString.replaceAll(",", "\\."));
                    }
                });
    }

    @Bean
    public DeserializationProblemHandler deserializationHandler() {
        return new DeserializationProblemHandler() {
            @Override
            public Object handleWeirdStringValue(DeserializationContext context, Class<?> targetType,
                                                 String valueToConvert, String failureMsg) throws IOException {
                if (targetType == Boolean.class) {
                    return TRUE.toString().equalsIgnoreCase(valueToConvert);
                }
                return super.handleWeirdStringValue(context, targetType, valueToConvert, failureMsg);
            }
        };
    }
}

