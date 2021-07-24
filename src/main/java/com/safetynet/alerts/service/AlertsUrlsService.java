package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.ChildAlertDTO;
import com.safetynet.alerts.dto.CommunityEmailDTO;
import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.dto.FloodDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.dto.PersonsByStationDTO;
import com.safetynet.alerts.dto.PhoneAlertDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonStation;

@Service
public class AlertsUrlsService implements IAlertsUrlsService {

    private final IPersonService iPersonService;


    private final IFireStationService iFireStationService;


    private final IMedicalRecordService iMedicalRecordService;
    

    @Autowired
    public AlertsUrlsService(final IPersonService personService, final IFireStationService fireStationService,
                         final IMedicalRecordService medicalRecordService) {
        this.iPersonService = personService;
        this.iFireStationService = fireStationService;
        this.iMedicalRecordService = medicalRecordService;
    }


	// TODO - http://localhost:8080/firestation?stationNumber=<station_number>
	// i.e. personsByStation(station_number)
	@Override
	public PersonsByStationDTO getPersonsByStation(int station) {
		
		// ************ TODO Steps ****************************
		
        // Retrieves person list
        List<Person> persons = iPersonService.getAllPersonList();
        
        // Retrieves addresses covered by the given station number
        List<String> addresses = iFireStationService.getAddressesByStation(station);
        List<PersonStation> list = new ArrayList<>();
        
		// Identify persons in the person list to find the persons at the addresses
			// - for a given address - Retrieving person medical record
		 	// - Calculate the age of the person
			// - Check the person is adult or a child and count accordingly
			// - Add PersonStation with information on person covered by the fire station number to array list
		// return PersonsByStationDTO with list of addresses and its adult and child count
		return null;
	}


	// TODO - http://localhost:8080/childAlert?address=<address>
	// i.e. childAlert(address)
	@Override
	public ChildAlertDTO getChildByAddress(String address) {
		
		// ************ TODO Steps ****************************
		
		// Retrieves person list based on address
		// Identify persons in the person list with given last name based on address.
			// - for a given address - Retrieving person medical record
		 	// - Calculate the age of the person
			// - Check the person is adult or a child and count accordingly
				// if child add to child list
				// if adult add to adult list
		// return Child and Adult list information
		return null;
	}

	// TODO - http://localhost:8080/phoneAlert?firestation=<firestation_number>
	// i.e. phonealert(station_number)
	@Override
	public PhoneAlertDTO getPhonesByStation(int station) {
		
		// ************ TODO Steps ****************************
		
		// Retrieves person list
		// Retrieves addresses covered by the given station number
        // Identify persons in the person list based on addresses covered by station
        //     to get their phone number and adds it to an ArrayList.
		// return list of phones
		return null;
	}


	// TODO - http://localhost:8080/fire?address=<address>
	// i.e. fire(address)
	@Override
	public FireDTO getPersonsByAddress(String address) {
		
		// ************ TODO Steps ****************************
		
		// Retrieves person list based on address
		// Identify persons in the person list with given last name based on address
			// - for a given address - Retrieving person medical record
		 	// - Calculate the age of the person
			// - Add to ArrayList a PersonAddress with each person in a address
		// Retrieves fire station that covered the given address to get it station number
		// return station and the persons list covered
		return null;
	}


	// TODO - http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	// i.e. flood(stations_list)
	@Override
	public FloodDTO getHousesCoveredByStation(List<Integer> stations) {
		
		// ************ TODO Steps ****************************
		
		// For each given fire station numbers retrieves addresses covered by it
		// For each address check for address duplication in fire station records before adding to ArrayList.
		// If not duplicated then Retrieves persons living at given address.
			// - for a given address - Retrieving persons living at given address
			// - For each person in the house Retrieving person medical record.
		 	// - calculates age for each person.
			// - Add to ArrayList a PersonAddress with each person in a address
		// Add to houses object address and list of persons living there
		// return houses coered by the station with (station number and houses covered info details. 
		return null;
	}

	// TODO - http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	// i.e. personInfo(personId)
	@Override
	public PersonInfoDTO getInfoPersonByIdentity(String firstName, String lastName) {
		
		// ************ TODO Steps ****************************
		
		// Retrieves person list based on person id
		// Identify persons in the person list to get persons with the given last name.
		// Check / Scan for the presence of lastname
			// - for a given address - Retrieving persons living at given address
			// - For each person Retrieving person medical record.
		 	// - calculates age for each person.
			// Add a new PersonInfo object with each person data based on last name
		// return person info details. 
		return null;
	}

	// TODO - http://localhost:8080/communityEmail?city=<city>
	// i.e. communityEmail(city)
	@Override
	public CommunityEmailDTO getEmailsByCity(String city) {
		
		// ************ TODO Steps ****************************
		
		// Retrieves person list based on the city name
		// Identify persons in the person list based on city
			// - get persons email id
		// return person list info with email id details. 
		return null;
	}


	// *********** REPEATED COMMON METHODS **********************
	// calculate the age for each person
		// - formating date
		// - calculate age
		// - check if infant
		// - birthdate validity check
		// - etc
	// Retrieving medical record based on person id
	
	

    
}
