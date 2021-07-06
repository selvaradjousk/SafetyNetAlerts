package com.safetynet.alerts.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.util.DataExchangerJsonToArrayList;
import com.safetynet.alerts.util.DataExchangerModelToDTO;

@Repository
public class PersonDAO {

	private DataExchangerModelToDTO dataExchangerModelToDTO;
	
	private Map<String, Person> personsMap = new HashMap<>();
	
    @Autowired
    public PersonDAO(DataExchangerJsonToArrayList jsonDataArrayList) {
        jsonDataArrayList.getPersonList().forEach(person -> personsMap.put(person.getFirstName()
                + person.getLastName(), person));
    }

	public Person getPersonByName(String firstName, String lastName) {
        return personsMap.get(firstName + lastName);
    }
	
	public List<Person> getPersonList() {
        Collection personList = personsMap.values();
        return new ArrayList<>(personList);

    }
	
}
