package com.safetynet.alerts.config;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provides methods for actuator configuration.
 * @author Senthil
 *
 */
@Configuration
public class HttpTraceActuatorConfiguration {
    /**
     * Http trace actuator configuration bean.
     * @return in memory HTTP trace
     */
    @Bean
    public HttpTraceRepository httpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }
}
