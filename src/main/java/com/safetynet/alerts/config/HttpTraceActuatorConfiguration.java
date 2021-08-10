package com.safetynet.alerts.config;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.log4j.Log4j2;

/**
 * Provides methods for actuator configuration.
 * @author Senthil
 *
 */
@Log4j2
@Configuration
public class HttpTraceActuatorConfiguration {

    /**
     * Http trace repository.
     *
     * @return the http trace repository
     */
    @Bean
    public HttpTraceRepository httpTraceRepository() {
    	log.debug("htttpTraceRepository()");
        return new InMemoryHttpTraceRepository();
    }
}
