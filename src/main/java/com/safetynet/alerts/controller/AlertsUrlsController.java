package com.safetynet.alerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.PersonsByStationDTO;
import com.safetynet.alerts.exception.BadRequestException;
import com.safetynet.alerts.service.IAlertsUrlsService;

@RestController
public class AlertsUrlsController {

	private final IAlertsUrlsService alertsUrlsService;

	@Autowired
	public AlertsUrlsController(final IAlertsUrlsService alertsEndpointUrlsService) {
		this.alertsUrlsService = alertsEndpointUrlsService;
	}
	
	// TODO - http://localhost:8080/firestation?stationNumber=<station_number>
	// i.e. personsByStation(station_number)
	@GetMapping("/firestation")
	public ResponseEntity<PersonsByStationDTO> getPersonsByStation(
			@RequestParam("stationNumber") final Integer station) {
		
		if (station == null) {
			throw new BadRequestException(
					"Response Status: 404 Bad request - missing input values");
		}
		PersonsByStationDTO personsByStationDTO = alertsUrlsService
				.getPersonsByStation(station);

		return new ResponseEntity<>(personsByStationDTO, HttpStatus.OK);
	}
	
	

	// TODO - http://localhost:8080/childAlert?address=<address>
	// i.e. childAlert(address)

	// TODO - http://localhost:8080/phoneAlert?firestation=<firestation_number>
	// i.e. phonealert(station_number)

	// TODO - http://localhost:8080/fire?address=<address>
	// i.e. fire(address)

	// TODO - http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	// i.e. flood(stations_list)
	
	// TODO - http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	// i.e. personInfo(personId)
	
	// TODO - http://localhost:8080/communityEmail?city=<city>
	// i.e. communityEmail(city)
	
	
}
