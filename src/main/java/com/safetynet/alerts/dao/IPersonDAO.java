package com.safetynet.alerts.dao;

import java.util.List;

import com.safetynet.alerts.model.Person;

public interface IPersonDAO {

	Person getPersonByName(String firstName, String lastName);

	List<Person> getPersonList();
	
	Person savePerson(Person pers);

}