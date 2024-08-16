package com.example.l3_pre.utils;

import com.example.l3_pre.helper.DateHelper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {

    public static String convertToJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .setDateFormat(new SimpleDateFormat(DateHelper.DB_DATE))
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            log.info("Fail convert to Json with error Message = {}", exception.getMessage());
            return "{}";
        }
    }
}
