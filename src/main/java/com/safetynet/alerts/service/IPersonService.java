package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.model.Person;

public interface IPersonService {

	PersonDTO getPersonById(String firstName, String lastName);

	List<Person> getAllPersonList() throws Exception;

}