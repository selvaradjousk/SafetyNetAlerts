package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dao.IPersonDAO;
import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.exception.DataAlreadyRegisteredException;
import com.safetynet.alerts.exception.DataNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.PersonMapper;

/**
 * Person service class.
 * @author Senthil
 *
 */
@Service
public class PersonService implements IPersonService {

    /**
     * person DAO instance.
     */
	private IPersonDAO personDAO;

    /**
     * person mapper instance.
     */
	private PersonMapper personMapper;

    /**
     * Person service constructor.
     * @param personDAO
     * @param personMapper
     */
    @Autowired
    public PersonService(
    		final IPersonDAO personDAO,
    		final PersonMapper personMapper) {
        this.personDAO = personDAO;
        this.personMapper = personMapper;
    }

    /**
     * Get person by id.
     * @return person
     */
    @Override
	public PersonDTO getPersonById(
			final String firstName,
			final String lastName) {

        Person person = personDAO
        		.getPersonByName(firstName, lastName);

		if (person == null) {
			throw new DataNotFoundException("Person "
					+ "with this ID is not Found");
		}

        return personMapper.toPersonDTO(person);
    }

    /**
     * Get list of persons.
     * @return person list
     */
    @Override
	public List<Person> getAllPersonList() {
        List<Person> listOfPerson = personDAO.getPersonList();

		if (listOfPerson.isEmpty()) {
			throw new DataNotFoundException("Person"
					+ " List is Empty");
        }
       return listOfPerson;
   }
    
    
    /**
     * Get person by address.
     * @param address
     * @return personByAddress
     */
    public List<Person> getPersonsByAddress(final String address) {

        List<Person> personsByAddress = personDAO.getPersonByAddress(address);

        if (personsByAddress.isEmpty()) {
			throw new DataNotFoundException("Failed to get persons for the address : " + address);
		}

        return personsByAddress;
    }
    
    

	/**
	 * Get Person list by city
	 * @param city
	 * @return person list by city
	 */
    public List<Person> getPersonsByCity(final String city) {

        List<Person> personsByCity = personDAO.getPersonByCity(city);

		if (personsByCity.isEmpty()) {
			throw new DataNotFoundException("Failed to get persons for the city : " + city);
        }

        return personsByCity;
    }
	
	

    /**
     * Add new person.
     * @param newPerson
     * @return personSaved saved
     */
    public PersonDTO addNewPerson(
    		final PersonDTO newPerson) {

    	Person personAlreadyExists
        = personDAO.getPersonByName(
        		newPerson.getFirstName(),
        		newPerson.getLastName());

		if (personAlreadyExists != null) {
			throw new DataAlreadyRegisteredException("Person"
					+ " already registered");
        }

        Person personToSave = personMapper.toPerson(newPerson);
        Person personSaved = personDAO.savePerson(personToSave);

        return personMapper.toPersonDTO(personSaved);
    }

    /**
     * Update existing person.
     * @param existingPerson
     * @return person found
     */
    public PersonDTO updateExistingPerson(
    		final PersonDTO existingPerson) {

        Person personFound = personDAO
        		.getPersonByName(
        				existingPerson.getFirstName(),
        				existingPerson.getLastName());

		if (personFound == null) {
			throw new DataNotFoundException("Response Status: 404"
					+ " - Person requested not found");
        }

        personFound.setAddress(existingPerson.getAddress());
        personFound.setCity(existingPerson.getCity());
        personFound.setZip(existingPerson.getZip());
        personFound.setPhone(existingPerson.getPhone());
        personFound.setEmail(existingPerson.getEmail());

        return personMapper.toPersonDTO(personFound);
    }

    /**
     * Delete existing person.
     * @param firstName
     * @param lastName
     */
    public void deleteExistingPerson(
    		final String firstName,
    		final String lastName) {

        Person personToDelete = personDAO
        		.getPersonByName(firstName, lastName);

		if (personToDelete == null) {
			throw new DataNotFoundException("Person not found");
        }

        personDAO.deletePerson(personToDelete);
    }

}
