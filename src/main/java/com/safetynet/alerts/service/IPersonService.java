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
     *
     * @param firstName the first name
     * @param lastName the last name
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
     *
     * @param address the address
     * @return person list
     */
	List<Person> getPersonsByAddress(String address);

	/**
	 * Get Person list by city.
	 *
	 * @param city the city
	 * @return person list by city
	 */
	List<Person> getPersonsByCity(String city);

    /**
     * Add new person.
     *
     * @param newPerson the new person
     * @return newPerson saved
     */
    PersonDTO addNewPerson(PersonDTO newPerson);

    /**
     * Update existing person.
     *
     * @param person the person
     * @return person found
     */
	PersonDTO updateExistingPerson(PersonDTO person);

    /**
     * Delete existing person.
     *
     * @param firstName the first name
     * @param lastName the last name
     */
	void deleteExistingPerson(String firstName, String lastName);
}
