package com.safetynet.alerts.dao;

import java.util.List;

import com.safetynet.alerts.model.Person;

/**
 * The Interface IPersonDAO.
 * @author Senthil
 */
public interface IPersonDAO {

	/**
	 * Gets the person by name.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 * @return the person by name
	 */
	Person getPersonByName(String firstName, String lastName);

	/**
	 * Gets the person list.
	 *
	 * @return the person list
	 */
	List<Person> getPersonList();

	/**
	 * Gets the person by city.
	 *
	 * @param city the city
	 * @return the person by city
	 */
	List<Person> getPersonByCity(String city);

	/**
	 * Gets the person by address.
	 *
	 * @param address the address
	 * @return the person by address
	 */
	List<Person> getPersonByAddress(String address);

	/**
	 * Save person.
	 *
	 * @param person the person
	 * @return the person
	 */
	Person savePerson(Person person);

	/**
	 * Delete person.
	 *
	 * @param person the person
	 */
	void deletePerson(Person person);

}
