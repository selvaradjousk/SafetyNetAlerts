package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.ChildAlertDTO;
import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.dto.FloodDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.dto.PersonsByStationDTO;
import com.safetynet.alerts.dto.PhoneAlertDTO;

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
		// TODO Auto-generated method stub
		return null;
	}


	// TODO - http://localhost:8080/childAlert?address=<address>
	// i.e. childAlert(address)
	@Override
	public ChildAlertDTO getChildByAddress(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO - http://localhost:8080/phoneAlert?firestation=<firestation_number>
	// i.e. phonealert(station_number)
	@Override
	public PhoneAlertDTO getPhonesByStation(int station) {
		// TODO Auto-generated method stub
		return null;
	}


	// TODO - http://localhost:8080/fire?address=<address>
	// i.e. fire(address)
	@Override
	public FireDTO getPersonsByAddress(String address) {
		// TODO Auto-generated method stub
		return null;
	}


	// TODO - http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	// i.e. flood(stations_list)
	@Override
	public FloodDTO getHousesCoveredByStation(List<Integer> stations) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO - http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	// i.e. personInfo(personId)
	@Override
	public PersonInfoDTO getInfoPersonByIdentity(String firstName, String lastName) {
		// TODO Auto-generated method stub
		return null;
	}


	
	// TODO - http://localhost:8080/communityEmail?city=<city>
	// i.e. communityEmail(city)
    
}
