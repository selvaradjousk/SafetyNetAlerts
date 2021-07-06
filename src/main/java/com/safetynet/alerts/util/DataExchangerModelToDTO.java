package com.safetynet.alerts.util;

import org.springframework.stereotype.Component;

import com.safetynet.alerts.dto.PersonEndpointDTO;
import com.safetynet.alerts.model.Person;

@Component
public class DataExchangerModelToDTO {

    public PersonEndpointDTO transferToPersonEndpointDTO(final Person person) {

        return new PersonEndpointDTO(person.getFirstName(),
        		person.getLastName(),
        		person.getAddress(),
                person.getCity(),
                person.getZip(),
                person.getPhone(),
                person.getEmail());
    }
	
}