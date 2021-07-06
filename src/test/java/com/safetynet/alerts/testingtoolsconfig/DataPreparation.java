package com.safetynet.alerts.testingtoolsconfig;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DataPreparation {

    private ObjectMapper objectMapper;
    public static String displayAsJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}