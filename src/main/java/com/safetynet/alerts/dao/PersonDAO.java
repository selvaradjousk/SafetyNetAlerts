package com.safetynet.alerts.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.DataExchangerJsonToArrayList;

@Repository
public class PersonDAO {

	public PersonDAO(DataExchangerJsonToArrayList dataExchangerJsonToArrayList) {
		// TODO Auto-generated constructor stub
	}

	public List<Person> getPersonList() {
		// TODO Auto-generated method stub
		return null;
	}

	public Person getPersonByName(String firstName, String lastName) {
		// TODO Auto-generated method stub
		return null;
	}

}
