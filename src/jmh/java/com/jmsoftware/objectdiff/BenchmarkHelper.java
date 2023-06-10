package com.jmsoftware.objectdiff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;

/**
 * <h1>BenchmarkHelper</h1>
 * <p>
 * Change description here.
 *
 * @author Johnny Miller (鍾俊), email: johnnysviva@outlook.com, 6/9/23 11:47 PM
 **/
public class BenchmarkHelper {
    private BenchmarkHelper() {
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    @SneakyThrows
    public static <T> T parse(String jsonPath, Class<T> clazz) {
        @Cleanup val in = BenchmarkHelper.class.getResourceAsStream(jsonPath);
        return OBJECT_MAPPER.readValue(in, clazz);
    }
}
