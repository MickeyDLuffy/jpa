package com.github.mickeydeelufy.jpa.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestUtil {


     public static String stringify(Object content) throws Exception {
         ObjectMapper mapper = new ObjectMapper();
         mapper.registerModule(new JavaTimeModule());
         mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
         var d = mapper.writeValueAsString(content);
        return mapper.writeValueAsString(content);
    }
}
