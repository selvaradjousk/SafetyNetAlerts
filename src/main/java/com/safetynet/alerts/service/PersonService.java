package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dao.PersonDAO;
import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.PersonMapper;

@Service
public class PersonService implements IPersonService {

	private PersonDAO personDAO;

	private PersonMapper personMapper;
	
    @Autowired
    public PersonService(
    		final PersonDAO personDAO,
    		final PersonMapper personMapper) {
        this.personDAO = personDAO;
        this.personMapper = personMapper;
    }
	
    @Override
	public PersonDTO getPersonById(String firstName, String lastName) {

        Person person = personDAO.getPersonByName(firstName, lastName);
        return personMapper.toPersonDTO(person);
    }
    
    @Override
	public List<Person> getAllPersonList() throws Exception {
        List<Person> listOfPerson = personDAO.getPersonList();
        
		if (listOfPerson.isEmpty()) {
			throw new Exception("Person List is Empty");
        }
       return listOfPerson;
   }

}
