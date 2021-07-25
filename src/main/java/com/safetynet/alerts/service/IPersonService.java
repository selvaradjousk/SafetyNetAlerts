package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.model.Person;

/**
 * Person service interface class.
 * @author Senthil
 *
 */
public interface IPersonService {

    /**
     * Get person by id.
     * @param firstName
     * @param lastName
     * @return person
     */
	PersonDTO getPersonById(String firstName, String lastName);

    /**
     * Get list of persons.
     * @return person list
     */
	List<Person> getAllPersonList();
	
	
    /**
     * Get list of persons by address.
     * @param address
     * @return person list
     */
	List<Person> getPersonsByAddress(String address);
	

    /**
     * Add new person.
     * @param newPerson
     * @return newPerson saved
     */
    PersonDTO addNewPerson(PersonDTO newPerson);

    /**
     * Update existing person.
     * @param person
     * @return person found
     */
	PersonDTO updateExistingPerson(PersonDTO person);

    /**
     * Delete existing person.
     * @param firstName
     * @param lastName
     */
	void deleteExistingPerson(String firstName, String lastName);
}
