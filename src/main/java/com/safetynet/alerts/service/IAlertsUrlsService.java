package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.ChildAlertDTO;
import com.safetynet.alerts.dto.PersonsByStationDTO;
import com.safetynet.alerts.dto.PhoneAlertDTO;

public interface IAlertsUrlsService {
	
	// TODO - http://localhost:8080/firestation?stationNumber=<station_number>
	// i.e. personsByStation(station_number)
    PersonsByStationDTO getPersonsByStation(int station);

	// TODO - http://localhost:8080/childAlert?address=<address>
	// i.e. childAlert(address)
    ChildAlertDTO getChildByAddress(String address);
    
	// TODO - http://localhost:8080/phoneAlert?firestation=<firestation_number>
	// i.e. phonealert(station_number)
    PhoneAlertDTO getPhonesByStation(int station);

	// TODO - http://localhost:8080/fire?address=<address>
	// i.e. fire(address)

	// TODO - http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	// i.e. flood(stations_list)
	
	// TODO - http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	// i.e. personInfo(personId)
	
	// TODO - http://localhost:8080/communityEmail?city=<city>
	// i.e. communityEmail(city)
	
}