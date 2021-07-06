
package com.safetynet.alerts.util;

import com.safetynet.alerts.dto.PersonEndpointDTO;
import com.safetynet.alerts.model.Person;

public class DataExchangerModelFromDTO {

    public Person toPerson(final PersonEndpointDTO personDTO) {

        return new Person(personDTO.getFirstName(),
        		personDTO.getLastName(),
        		personDTO.getAddress(),
                personDTO.getCity(),
                personDTO.getZip(),
                personDTO.getPhone(),
                personDTO.getEmail());
    }
}