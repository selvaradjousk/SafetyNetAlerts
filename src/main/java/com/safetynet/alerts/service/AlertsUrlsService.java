package com.safetynet.alerts.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.ChildAlertDTO;
import com.safetynet.alerts.dto.CommunityEmailDTO;
import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.dto.FloodDTO;
import com.safetynet.alerts.dto.HousesCoveredByStationDTO;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.dto.PersonsByStationDTO;
import com.safetynet.alerts.dto.PhoneAlertDTO;
import com.safetynet.alerts.model.Child;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.House;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PersonAddress;
import com.safetynet.alerts.model.PersonStation;
import com.safetynet.alerts.util.ComputeAgeOfPerson;

@Service
public class AlertsUrlsService implements IAlertsUrlsService {

    private final IPersonService iPersonService;


    private final IFireStationService iFireStationService;


    private final IMedicalRecordService iMedicalRecordService;
    
    private final ComputeAgeOfPerson computeAgeOfPerson;
    
    private static final int AGE_LIMIT_TO_BE_ADULT = 19;

    
    @Autowired
    public AlertsUrlsService(final IPersonService personService, final IFireStationService fireStationService,
                         final IMedicalRecordService medicalRecordService, final ComputeAgeOfPerson computeAgeOfPerson) {
        this.iPersonService = personService;
        this.iFireStationService = fireStationService;
        this.iMedicalRecordService = medicalRecordService;
        this.computeAgeOfPerson = computeAgeOfPerson;
    }


	// TODO - DONE  http://localhost:8080/firestation?stationNumber=<station_number>
	// i.e. personsByStation(station_number)
	@Override
	public PersonsByStationDTO getPersonsByStation(int station) {
		
		// ************ TODO Steps ****************************
        int adultCount = 0;
        int childCount = 0;
		
        // Retrieves person list
        List<Person> persons = iPersonService.getAllPersonList();
        
        // Retrieves addresses covered by the given station number
        List<String> addresses = iFireStationService.getAddressesByStation(station);
        List<PersonStation> list = new ArrayList<>();
 		
        // Identify persons in the person list  to find the persons at the addresses.
        for (Person person : persons) {
            for (String address : addresses) {

                if (person.getAddress().equals(address)) {
                	
                	// - for a given address - Retrieving person medical record
                    MedicalRecordDTO medicalRecordDTO = retrieveMedicalRecordById(person);
                    
                    // - Check the person is adult or a child and count accordingly
                    int age = getAgeOfPerson(medicalRecordDTO);
                    
                    // Check the person is adult or a child and count accordingly
                    if (isChild(age)) {
                        childCount++;
                    } else {
                        adultCount++;
                    }
                    // Add PersonStation with information on person covered by the fire station number to array list
                    list.add(new PersonStation(person.getFirstName(), person.getLastName(), person.getAddress(),
                            person.getPhone()));
                }
            }
        }
     // return PersonsByStationDTO with list of addresses and its adult and child count
        return new PersonsByStationDTO(list, adultCount, childCount);
    }

	// TODO - DONE  http://localhost:8080/childAlert?address=<address>
	// i.e. childAlert(address)
    public ChildAlertDTO getChildByAddress(final String address) {
    	
    	// Retrieves person list based on address
        List<Person> personsByAddress = iPersonService.getPersonsByAddress(address);
        List<Child> childList = new ArrayList<>();
        List<String> adultList = new ArrayList<>();

        // Identify persons in the person list with given last name based on address.
        for (Person person : personsByAddress) {

        	
        	// - for a given address - Retrieving person medical record
            MedicalRecordDTO medicalRecordDTO = retrieveMedicalRecordById(person);
            
            // - Calculate the age of the person
            int age = getAgeOfPerson(medicalRecordDTO);
            

            // - Check the person is adult or a child and count accordingly
            if (isChild(age)) {
            	// if child add to child list
                childList.add(new Child(
                		person.getFirstName(),
                		person.getLastName(), age));
            } else {
            	// if adult add to adult list
                adultList.add(
                		"FirstName : " + person.getFirstName()
                		+ " LastName : " + person.getLastName());
            }
        }
        // return Child and Adult list information
        return new ChildAlertDTO(childList, adultList);
    }
	
		


	// TODO - http://localhost:8080/phoneAlert?firestation=<firestation_number>
	// i.e. phonealert(station_number)
    public PhoneAlertDTO getPhonesByStation(final int station) {
    	
    	// Retrieves person list
        List<Person> persons = iPersonService.getAllPersonList();

        // Retrieves addresses covered by the given station number
        List<String> addresses = iFireStationService.getAddressesByStation(station);
        List<String> phones = new ArrayList<>();

        // Identify persons in the person list based on addresses covered by station
        // to get their phone number and adds it to an ArrayList.
        for (Person person : persons) {
            for (String address : addresses) {
                if (person.getAddress().equals(address)) {
                    phones.add(person.getPhone());
                }
            }
        }
	// return list of phones
        return new PhoneAlertDTO(phones);
    }


	// TODO - http://localhost:8080/fire?address=<address>
	// i.e. fire(address)
    public FireDTO getPersonsByAddress(final String address) {

    	// Retrieves person list based on address
        List<Person> personsByAddress = iPersonService.getPersonsByAddress(address);
        List<PersonAddress> persons = new ArrayList<>();

     // Identify persons in the person list with given last name based on address.
        for (Person person : personsByAddress) {

        	// - for a given address - Retrieving person medical record
            MedicalRecordDTO medicalRecordDTO = retrieveMedicalRecordById(person);

            // - Calculate the age of the person
            int age = getAgeOfPerson(medicalRecordDTO);

            // - Add to ArrayList a PersonAddress with each person in a address
            persons.add(new PersonAddress(person.getLastName(), person.getPhone(),
                    age, medicalRecordDTO.getMedications(), medicalRecordDTO.getAllergies()));
        }

		// Retrieves fire station that covered the given address to get it station number
		// return station and the persons list covered
        FireStation fireStation = iFireStationService.getFireStationByAddress(address);
        int station = fireStation.getStationId();

	// return station and the persons list covered
        return new FireDTO(station, persons);
    }


	// TODO - http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	// i.e. flood(stations_list)
    public FloodDTO getHousesCoveredByStation(final List<Integer> stations) {
        List<HousesCoveredByStationDTO> housesCoveredByStationDTO = new ArrayList<>();
        List<String> allAddresses = new ArrayList<>();

        for (int station : stations) {

            // For each given fire station numbers retrieves addresses covered by it.
            List<String> addressesByStation = iFireStationService.getAddressesByStation(station);
            List<House> houses = new ArrayList<>();

            for (String address : addressesByStation) {

		// For each address check for address duplication in fire station records
		// before adding to ArrayList.
                // Check If not duplicated then Retrieves persons living at given address.
                if (!allAddresses.contains(address)) {
                    allAddresses.add(address);

                    // - for a given address - Retrieving persons living at given address
                    List<Person> persons = iPersonService.getPersonsByAddress(address);
                    List<PersonAddress> personAddress = new ArrayList<>();

                    // Calculates age for each person from household.
                    for (Person person : persons) {
                    	
                    	// - For each person in the house Retrieving person medical record.
                        MedicalRecordDTO medicalRecordDTO = retrieveMedicalRecordById(person);
                        
                        // - calculates age for each person.
                        int age = getAgeOfPerson(medicalRecordDTO);
                        
                        // - Add to ArrayList a PersonAddress with each person in a address 
                        personAddress.add(new PersonAddress(person.getLastName(), person.getPhone(),
                                age, medicalRecordDTO.getMedications(), medicalRecordDTO.getAllergies()));
                    }
                    // Add to House object with address and list of persons living there.
                    houses.add(new House(address, personAddress));
                }
            }
            housesCoveredByStationDTO.add(new HousesCoveredByStationDTO(station, houses));
        }
	// return houses covered by the station with (station number and houses covered info details.
        return new FloodDTO(housesCoveredByStationDTO);
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
	

	private int getAgeOfPerson(MedicalRecordDTO medicalRecordDTO) {
		// Format the birthDate
		LocalDate birthDate = formatBirthDate(medicalRecordDTO);
		
		// Compute the person age
		int age = computeAgeOfPerson.getAge(birthDate);
		return age;
	}
    
    
	private MedicalRecordDTO retrieveMedicalRecordById(Person person) {
		MedicalRecordDTO medicalRecordDTO = iMedicalRecordService.getMedicalRecordById(person.getFirstName(),
		        person.getLastName());
		return medicalRecordDTO;
	}
	

	private LocalDate formatBirthDate(MedicalRecordDTO medicalRecordDTO) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
		LocalDate birthDate = LocalDate.parse(medicalRecordDTO.getBirthDate(), formatter);
		return birthDate;
	}
	
	private boolean isChild(int age) {
		return age < AGE_LIMIT_TO_BE_ADULT;
	}

    
}

