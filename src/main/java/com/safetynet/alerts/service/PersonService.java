package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dao.IPersonDAO;
import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.exception.DataNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.PersonMapper;

@Service
public class PersonService implements IPersonService {

	private IPersonDAO personDAO;

	private PersonMapper personMapper;
	
    @Autowired
    public PersonService(
    		final IPersonDAO personDAO,
    		final PersonMapper personMapper) {
        this.personDAO = personDAO;
        this.personMapper = personMapper;
    }
	
    @Override
	public PersonDTO getPersonById(String firstName, String lastName) {

        Person person = personDAO.getPersonByName(firstName, lastName);
        
		if (person == null) {
			throw new DataNotFoundException("Person with this ID is not Found");
		}
		
        return personMapper.toPersonDTO(person);
    }
    
    @Override
	public List<Person> getAllPersonList() {
        List<Person> listOfPerson = personDAO.getPersonList();
        
		if (listOfPerson.isEmpty()) {
			throw new DataNotFoundException("Person List is Empty");
        }
       return listOfPerson;
   }

}
