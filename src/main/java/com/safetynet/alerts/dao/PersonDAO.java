package com.safetynet.alerts.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.DataFileReader;

/**
 * Provides methods for Data Access Objects for Person resources.
 * @author Senthil
 *
 */
@Repository
public class PersonDAO implements IPersonDAO {

	/**
	 * Mapping Person data resource.
	 */
	private Map<String, Person> personsMap = new HashMap<>();

    /**
     * Constructor of PersonDAO class.
     * @param dataFileReader
     */
    @Autowired
    public PersonDAO(final DataFileReader dataFileReader) {
        dataFileReader.getPersonList().forEach(person
        		-> personsMap.put(person.getFirstName()
                + person.getLastName(), person));
    }

	/**
	 * Gets a Person data resource from the Map list on it key value sent.
	 *
	 * @param firstName identifier for the person to be found
	 * @param lastName identifier for the person to be found
	 * @return the person by name
	 */
	@Override
	public Person getPersonByName(
			final String firstName,
			final String lastName) {
        return personsMap.get(firstName + lastName);
    }

	/**
	 * Gets the Person data resource list form the Map.
	 *
	 * @return the person list
	 */
	public List<Person> getPersonList() {
        Collection personList = personsMap.values();
        return new ArrayList<>(personList);

    }

	/**
	 * Get person list by address.
	 *
	 * @param address the address
	 * @return personsByAddress
	 */
	public List<Person> getPersonByAddress(final String address) {
        Collection<Person> persons = personsMap.values();
        List<Person> personsByAddress = new ArrayList<>();

        for (Person person : persons) {
            if (person.getAddress().equals(address)) {
                personsByAddress.add(person);
            }
        }

        return personsByAddress;
    }

	/**
	 * get Person By City.
	 *
	 * @param city the city
	 * @return personsByCity
	 */
	public List<Person> getPersonByCity(final String city) {

        Collection<Person> persons = personsMap.values();
        List<Person> personsByCity = new ArrayList<>();

        for (Person person : persons) {
            if (person.getCity().equals(city)) {
                personsByCity.add(person);
            }
        }

        return personsByCity;
    }

	/**
	 * Saves the requested Person data resource form the Map list.
	 * @param person identifier for the person to be saved
	 * @return person saved
	 */
	public Person savePerson(final Person person) {
        personsMap.put(person.getFirstName()
        		+ person.getLastName(), person);

        return personsMap.get(person.getFirstName()
        		+ person.getLastName());
    }

	/**
	 * Deletes the requested Person data resource form the Map list.
	 * @param personToDelete identifier for the person to be deleted
	 */
	public void deletePerson(final Person personToDelete) {
		personsMap.remove(personToDelete.getFirstName()
				+ personToDelete.getLastName());
	}
}
