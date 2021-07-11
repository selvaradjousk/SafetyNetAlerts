
package com.safetynet.alerts.util;

import org.springframework.stereotype.Component;

import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.model.Person;

/**
 * MApper for PErson Data resource.
 * @author Senthil
 *
 */
@Component
public class PersonMapper {

    /**
     * Person DTO toPerson DO.
     * @param personDTO
     * @return Person
     */
    public Person toPerson(final PersonDTO personDTO) {

        return new Person(personDTO.getFirstName(),
        		personDTO.getLastName(),
        		personDTO.getAddress(),
                personDTO.getCity(),
                personDTO.getZip(),
                personDTO.getPhone(),
                personDTO.getEmail());
    }

    /**
     * Person DO toPerson DTO.
     * @param person
     * @return PersonDTO
     */
    public PersonDTO toPersonDTO(final Person person) {

        return new PersonDTO(person.getFirstName(),
        		person.getLastName(),
        		person.getAddress(),
                person.getCity(),
                person.getZip(),
                person.getPhone(),
                person.getEmail());
    }
}
