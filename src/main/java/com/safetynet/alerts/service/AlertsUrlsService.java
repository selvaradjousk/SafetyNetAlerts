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
import com.safetynet.alerts.model.PersonInfo;
import com.safetynet.alerts.model.PersonStation;
import com.safetynet.alerts.util.ComputeAgeOfPerson;

/**
 * Alerts Service class.
 * @author Senthil
 *
 */
@Service
public class AlertsUrlsService implements IAlertsUrlsService {

    /**
     * Person service class interface instance.
     */
    private final IPersonService iPersonService;

    /**
     * fire station service class interface instance.
     */
    private final IFireStationService iFireStationService;

    /**
     * medical record service class interface instance.
     */
    private final IMedicalRecordService iMedicalRecordService;

    /**
     * compute age of person class instance.
     */
    private final ComputeAgeOfPerson computeAgeOfPerson;

    /**
     * constant value representing adult age threshold.
     */
    private static final int AGE_LIMIT_TO_BE_ADULT = 19;


    /**
     * Instantiates a new alerts urls service.
     *
     * @param personService the person service
     * @param fireStationService the fire station service
     * @param medicalRecordService the medical record service
     * @param computeTheAgeOfPerson the compute the age of person
     */
    @Autowired
    public AlertsUrlsService(
    		final IPersonService personService,
    		final IFireStationService fireStationService,
    		final IMedicalRecordService medicalRecordService,
    		final ComputeAgeOfPerson computeTheAgeOfPerson) {
        this.iPersonService = personService;
        this.iFireStationService = fireStationService;
        this.iMedicalRecordService = medicalRecordService;
        this.computeAgeOfPerson = computeTheAgeOfPerson;
    }

	// http://localhost:8080/firestation?stationNumber=<station_number>
	// i.e. personsByStation(station_number)
    /**
     * Gets Persons By Station.
     * @param station
     * @return PersonsByStationDTO(list, adultCount, childCount)
     */
	public PersonsByStationDTO getPersonsByStation(final int station) {

        int adultCount = 0;
        int childCount = 0;

        // Retrieves person list
        List<Person> persons = iPersonService.getAllPersonList();

        // Retrieves addresses covered by the given station number
        List<String> addresses = iFireStationService
        		.getAddressesByStation(station);
        List<PersonStation> list = new ArrayList<>();

        // Identify persons in the person list
        // to find the persons at the addresses.
        for (Person person : persons) {
            for (String address : addresses) {

                if (person.getAddress().equals(address)) {

                	// - for a given address - Retrieving
                	//    person medical record
                    MedicalRecordDTO medicalRecordDTO
                    = retrieveMedicalRecordById(person);

                    // - Check the person is adult or
                    //   a child and count accordingly
                    int age = getAgeOfPerson(medicalRecordDTO);

                    // Check the person is adult or a
                    // child and count accordingly
                    if (isChild(age)) {
                        childCount++;
                    } else {
                        adultCount++;
                    }
                    // Add PersonStation with information on person
                    // covered by the fire station number to array list
                    list.add(new PersonStation(
                    		person.getFirstName(),
                    		person.getLastName(),
                    		person.getAddress(),
                            person.getPhone()));
                }
            }
        }
        // return PersonsByStationDTO with list of addresses
        // and its adult and child count
        return new PersonsByStationDTO(list, adultCount, childCount);
    }

	// http://localhost:8080/childAlert?address=<address>
	// i.e. childAlert(address)
    /**
     * Get child by address.
     * @param address
     * @return ChildAlertDTO(childList, adultList)
     */
    public ChildAlertDTO getChildByAddress(
    		final String address) {

    	// Retrieves person list based on address
        List<Person> personsByAddress = iPersonService
        		.getPersonsByAddress(address);
        List<Child> childList = new ArrayList<>();
        List<String> adultList = new ArrayList<>();

        // Identify persons in the person list with
        // given last name based on address.
        for (Person person : personsByAddress) {

        	// - for a given address - Retrieving
        	// person medical record
            MedicalRecordDTO medicalRecordDTO
            = retrieveMedicalRecordById(person);

            // - Calculate the age of the person
            int age = getAgeOfPerson(medicalRecordDTO);

            // - Check the person is adult or a child
            // and count accordingly
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

	// http://localhost:8080/phoneAlert?firestation=<firestation_number>
	// i.e. phonealert(station_number)
    /**
     * Get phone by station.
     * @param station
     * @return PhoneAlertDTO(phones)
     */
    public PhoneAlertDTO getPhonesByStation(final int station) {

    	// Retrieves person list
        List<Person> persons = iPersonService
        		.getAllPersonList();

        // Retrieves addresses covered by the given station number
        List<String> addresses = iFireStationService
        		.getAddressesByStation(station);
        List<String> phones = new ArrayList<>();

        // Identify persons in the person list based
        // on addresses covered by station
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

	// http://localhost:8080/fire?address=<address>
	// i.e. fire(address)
    /**
     * Gets persons by address.
     * @param address
     * @return FireDTO(station, persons)
     */
    public FireDTO getPersonsByAddress(
    		final String address) {

    	// Retrieves person list based on address
        List<Person> personsByAddress = iPersonService
        		.getPersonsByAddress(address);
        List<PersonAddress> persons = new ArrayList<>();

        // Identify persons in the person list with
        // given last name based on address.
        for (Person person : personsByAddress) {

        	// - for a given address
        	// - Retrieving person medical record
            MedicalRecordDTO medicalRecordDTO
            = retrieveMedicalRecordById(person);

            // - Calculate the age of the person
            int age = getAgeOfPerson(medicalRecordDTO);

            // - Add to ArrayList a PersonAddress with
            //   each person in a address
            persons.add(new PersonAddress(
            		person.getLastName(),
            		person.getPhone(), age,
            		medicalRecordDTO.getMedications(),
            		medicalRecordDTO.getAllergies()));
        }

		// Retrieves fire station that covered the given
        // address to get it station number
		// return station and the persons list covered
        FireStation fireStation = iFireStationService
        		.getFireStationByAddress(address);
        int station = fireStation.getStationId();

        // return station and the persons list covered
        return new FireDTO(station, persons);
    }


	// http://localhost:8080/flood/stations?stations
    // =<a list of station_numbers>
	// i.e. flood(stations_list)
    /**
     * Gets houses covered by station.
     * @param stations
     * @return FloodDTO(housesCoveredByStationDTO)
     */
    public FloodDTO getHousesCoveredByStation(
    		final List<Integer> stations) {

    	List<HousesCoveredByStationDTO>
        housesCoveredByStationDTO = new ArrayList<>();

    	List<String> allAddresses = new ArrayList<>();

        for (int station : stations) {

            // For each given fire station numbers retrieves
        	// addresses covered by it.
            List<String> addressesByStation = iFireStationService
            		.getAddressesByStation(station);
            List<House> houses = new ArrayList<>();

            for (String address : addressesByStation) {

            	// For each address check for address duplication
            	// in fire station records
            	// before adding to ArrayList.
                // Check If not duplicated then Retrieves
            	//   persons living at given address.
                if (!allAddresses.contains(address)) {
                    allAddresses.add(address);

                    // - for a given address - Retrieving persons
                    //   living at given address
                    List<Person> persons = iPersonService
                    		.getPersonsByAddress(address);
                    List<PersonAddress> personAddress = new ArrayList<>();

                    // Calculates age for each person from household.
                    for (Person person : persons) {

                    	// - For each person in the house Retrieving
                    	//   person medical record.
                        MedicalRecordDTO medicalRecordDTO
                        = retrieveMedicalRecordById(person);

                        // - calculates age for each person.
                        int age = getAgeOfPerson(medicalRecordDTO);

                        // - Add to ArrayList a PersonAddress with
                        //   each person in a address
                        personAddress.add(
                        		new PersonAddress(person
                        				.getLastName(),
                        				person.getPhone(),
                        				age,
                        				medicalRecordDTO
                        				.getMedications(),
                        				medicalRecordDTO
                        				.getAllergies()));
                    }
                    // Add to House object with address and list of
                    // persons living there.
                    houses.add(
                    		new House(address, personAddress));
                }
            }
            housesCoveredByStationDTO.add(
            		new HousesCoveredByStationDTO(station, houses));
        }
        // return houses covered by the station with
        // station number and houses covered info details.
        return new FloodDTO(housesCoveredByStationDTO);
    }

	// http://localhost:8080/personInfo
    // ?firstName=<firstName>&lastName=<lastName>
	// i.e. personInfo(personId)
    /**
     * Get person info by id.
     * @param firstName
     * @param lastName
     * @return PersonInfoDTO(personsInfo)
     */
    public PersonInfoDTO getInfoPersonByIdentity(
    		final String firstName,
    		final String lastName) {

    	// Retrieves person list
        List<Person> persons = iPersonService
        		.getAllPersonList();
        List<PersonInfo> personsInfo = new ArrayList<>();

        // - for a given address
        // - Retrieving persons living at given address
        for (Person person : persons) {

            // Calculates person's age by retrieving his medical
        	// record to obtain his date of birth.
            if (person.getLastName().equals(lastName)) {

            	// - For each person Retrieving
            	//   person medical record.
                MedicalRecordDTO medicalRecordDTO
                = retrieveMedicalRecordById(person);

                // - calculates age for each person.
                int age = getAgeOfPerson(medicalRecordDTO);

                // Add a new PersonInfo object with
                //   each person data based on last name
                personsInfo.add(new PersonInfo(
                		person.getLastName(),
                		person.getAddress(),
                        age,
                        person.getEmail(),
                        medicalRecordDTO.getMedications(),
                        medicalRecordDTO.getAllergies()));
            }
        }
	// return person info details.
        return new PersonInfoDTO(personsInfo);
    }

	// http://localhost:8080/communityEmail?city=<city>
	// i.e. communityEmail(city)
    /**
     * Get community email by city.
     * @param city
     * @return CommunityEmailDTO(emails)
     */
    public CommunityEmailDTO getEmailsByCity(
    		final String city) {

    	// Retrieves person list based on the city name
        List<Person> personsByCity = iPersonService
        		.getPersonsByCity(city);
        List<String> emails = new ArrayList<>();

        // Identify persons in the person list based on city
        for (Person person : personsByCity) {
		// - get persons email id
            emails.add(person.getEmail());
        }
	// return list info with email id details.
        return new CommunityEmailDTO(emails);
    }

	// *********** REPEATED COMMON METHODS **********************
	// calculate the age for each person
		// - formating date
		// - calculate age
		// - check if infant
		// - birthdate validity check
		// - etc
	// Retrieving medical record based on person id

	/**
	 * Calculate age of the person.
	 * @param medicalRecordDTO
	 * @return age
	 */
	private int getAgeOfPerson(
			final MedicalRecordDTO medicalRecordDTO) {
		// Format the birthDate
		LocalDate birthDate = formatBirthDate(medicalRecordDTO);

		// Compute the person age
		int age = computeAgeOfPerson.getAge(birthDate);
		return age;
	}

	/**
	 * Retrieve medical record by id.
	 * @param person
	 * @return medicalRecordDTO
	 */
	private MedicalRecordDTO retrieveMedicalRecordById(
			final Person person) {
		MedicalRecordDTO medicalRecordDTO = iMedicalRecordService
				.getMedicalRecordById(
						person.getFirstName(),
						person.getLastName());
		return medicalRecordDTO;
	}

	/**
	 * format the birthdate.
	 * @param medicalRecordDTO
	 * @return birthDate
	 */
	private LocalDate formatBirthDate(
			final MedicalRecordDTO medicalRecordDTO) {
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("MM/d/yyyy");
		LocalDate birthDate = LocalDate.parse(medicalRecordDTO
				.getBirthDate(), formatter);
		return birthDate;
	}

	/**
	 * Check if is child.
	 * @param age
	 * @return boolean
	 */
	private boolean isChild(final int age) {
		return age < AGE_LIMIT_TO_BE_ADULT;
	}
}
