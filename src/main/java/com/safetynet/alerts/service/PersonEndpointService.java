package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dao.PersonDAO;
import com.safetynet.alerts.dto.PersonEndpointDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.DataExchangerModelToDTO;

@Service
public class PersonEndpointService {

	private PersonDAO personDAO;

	private DataExchangerModelToDTO dataExchangerModelToDTO;
	
    @Autowired
    public PersonEndpointService(
    		final PersonDAO personDAO,
    		final DataExchangerModelToDTO dataExchangerModelToDTO) {
        this.personDAO = personDAO;
        this.dataExchangerModelToDTO = dataExchangerModelToDTO;
    }
	
    public PersonEndpointDTO getPersonById(String firstName, String lastName) {

        Person person = personDAO.getPersonByName(firstName, lastName);
        return dataExchangerModelToDTO.transferToPersonEndpointDTO(person);
    }
    
    public List<Person> getAllPersonList() {
        List<Person> listOfPerson = personDAO.getPersonList();
       return listOfPerson;
   }

}
