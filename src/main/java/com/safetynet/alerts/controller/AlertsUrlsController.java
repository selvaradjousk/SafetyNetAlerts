package com.safetynet.alerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.ChildAlertDTO;
import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.dto.FloodDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.dto.PersonsByStationDTO;
import com.safetynet.alerts.dto.PhoneAlertDTO;
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
	@GetMapping("/childAlert")
	public ResponseEntity<ChildAlertDTO> getChildByAddress(
			@RequestParam("address") final String address) {

		if (address.trim().length() == 0) {
			throw new BadRequestException(
					"Response Status: 404 Bad request - missing input values");
		}
		ChildAlertDTO childAlertDTO = alertsUrlsService
				.getChildByAddress(address);

		return new ResponseEntity<>(childAlertDTO, HttpStatus.OK);
	}
	

	// TODO - http://localhost:8080/phoneAlert?firestation=<firestation_number>
	// i.e. phonealert(station_number)
	@GetMapping("/phoneAlert")
	public ResponseEntity<PhoneAlertDTO> getPhonesByStation(
			@RequestParam("firestation") final Integer station) {


		if (station == null) {
			throw new BadRequestException(
					"Response Status: 404 Bad request - missing input values");
		}
		PhoneAlertDTO phoneAlertDTO = alertsUrlsService
				.getPhonesByStation(station);

		return new ResponseEntity<>(phoneAlertDTO, HttpStatus.OK);
	}
	
	
	// TODO - http://localhost:8080/fire?address=<address>
	// i.e. fire(address)
	@GetMapping("/fire")
	public ResponseEntity<FireDTO> getPersonsByAddress(
			@RequestParam("address") final String address) {

		if (address.trim().length() == 0) {
			throw new BadRequestException(
					"Response Status: 404 Bad request - missing input values");
		}
		FireDTO fireDTO = alertsUrlsService
				.getPersonsByAddress(address);

		return new ResponseEntity<>(fireDTO, HttpStatus.OK);
	}

	// TODO - http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	// i.e. flood(stations_list)

	@GetMapping("/flood/stations")
	public ResponseEntity<FloodDTO> getHouseholdsByStation(
			@RequestParam("stations") final List<Integer> stations) {


		if (stations.isEmpty()) {
			throw new BadRequestException(
					"Response Status: 404 Bad request - missing input values");
		}
		FloodDTO floodDTO = alertsUrlsService
				.getHousesCoveredByStation(stations);

		return new ResponseEntity<>(floodDTO, HttpStatus.OK);
	}
	
	
	// TODO - http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	// i.e. personInfo(personId)
	@GetMapping("/personInfo")
	public ResponseEntity<PersonInfoDTO> getPersonInfoByIdentity(
			@RequestParam("firstName") final String firstName,
			@RequestParam("lastName") final String lastName) {

		if (firstName.trim().length() == 0
				|| lastName.trim().length() == 0) {
			throw new BadRequestException(
					"Response Status: 404 Bad request - missing input values");
		}
		PersonInfoDTO personInfoDTO = alertsUrlsService
				.getInfoPersonByIdentity(firstName, lastName);

		return new ResponseEntity<>(personInfoDTO, HttpStatus.OK);
	}
	
	// TODO - http://localhost:8080/communityEmail?city=<city>
	// i.e. communityEmail(city)
	
	
}
