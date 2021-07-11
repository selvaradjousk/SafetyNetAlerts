package com.safetynet.alerts.dao;

import java.util.List;

import com.safetynet.alerts.model.Person;

/**
 * Interface class Data Access Objects for Person resources.
 * @author Senthil
 *
 */
public interface IPersonDAO {

	/**
	 * Gets a Person data resource from the Map list
	 *  on it key value sent.
	 * @param firstName identifier for the person to be found
	 * @param lastName identifier for the person to be found
	 * @return the person data
	 */
	Person getPersonByName(String firstName, String lastName);

	/**
	 * Gets the Person data resource list form the Map.
	 * @return person list
	 */
	List<Person> getPersonList();

	/**
	 * Saves the requested Person data resource form the Map list.
	 * @param person identifier for the person to be saved
	 * @return person saved
	 */
	Person savePerson(Person person);

	/**
	 * Deletes the requested Person data resource form the Map list.
	 * @param person identifier for the person to be deleted
	 */
	void deletePerson(Person person);

}
