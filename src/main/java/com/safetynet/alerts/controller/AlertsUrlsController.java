package com.safetynet.alerts.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.ChildAlertDTO;
import com.safetynet.alerts.dto.CommunityEmailDTO;
import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.dto.FloodDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.dto.PersonsByStationDTO;
import com.safetynet.alerts.dto.PhoneAlertDTO;
import com.safetynet.alerts.service.IAlertsUrlsService;

import lombok.extern.log4j.Log4j2;

/**
 * The Class AlertsUrlsController.
 */
@Log4j2
@RestController
@Validated
public class AlertsUrlsController {

	/** The alerts urls service. */
	private final IAlertsUrlsService alertsUrlsService;

	/**
	 * Instantiates a new alerts urls controller.
	 *
	 * @param alertsEndpointUrlsService the alerts endpoint urls service
	 */
	@Autowired
	public AlertsUrlsController(
			final IAlertsUrlsService alertsEndpointUrlsService) {
		this.alertsUrlsService = alertsEndpointUrlsService;
	}

	/**
	 * Gets the persons by station.
	 *
	 * @param station the station
	 * @return the persons by station
	 */
	@GetMapping("/fireStation")
	public ResponseEntity<PersonsByStationDTO> getPersonsByStation(
			@RequestParam("stationNumber")
			@Valid @NotNull final Integer station) {
		log.debug("GET Request - /fireStation"
				+ " for station number {}", station);
		PersonsByStationDTO personsByStationDTO = alertsUrlsService
				.getPersonsByStation(station);
		log.info("GET Request - /fireStation for station number {}"
				+ " - 200 OK", station);
		return new ResponseEntity<>(personsByStationDTO, HttpStatus.OK);
	}

	/**
	 * Gets the child by address.
	 *
	 * @param address the address
	 * @return the child by address
	 */
	@GetMapping("/childAlert")
	public ResponseEntity<ChildAlertDTO> getChildByAddress(
			@RequestParam("address")
			@Valid @NotBlank final String address) {
		log.debug("GET Request - /childAlert for address {}", address);
		ChildAlertDTO childAlertDTO = alertsUrlsService
				.getChildByAddress(address);
		log.debug("GET Request - /childAlert for address {}"
				+ " - 200 OK", address);
		return new ResponseEntity<>(childAlertDTO, HttpStatus.OK);
	}

	/**
	 * Gets the phones by station.
	 *
	 * @param station the station
	 * @return the phones by station
	 */
	@GetMapping("/phoneAlert")
	public ResponseEntity<PhoneAlertDTO> getPhonesByStation(
			@RequestParam("firestation")
			@Valid @NotNull final Integer station) {
		log.debug("GET Request - /phoneAlert for"
				+ " station number {}", station);
		PhoneAlertDTO phoneAlertDTO = alertsUrlsService
				.getPhonesByStation(station);
		log.debug("GET Request - /phoneAlert"
				+ " for station number {}"
				+ " - 200 OK", station);
		return new ResponseEntity<>(phoneAlertDTO, HttpStatus.OK);
	}

	/**
	 * Gets the persons by address.
	 *
	 * @param address the address
	 * @return the persons by address
	 */
	@GetMapping("/fire")
	public ResponseEntity<FireDTO> getPersonsByAddress(
			@RequestParam("address")
			@Valid @NotBlank final String address) {
		log.debug("GET Request - /fire for address {}", address);
		FireDTO fireDTO = alertsUrlsService
				.getPersonsByAddress(address);
		log.debug("GET Request - /fire for address {}"
				+ " - 200 OK", address);
		return new ResponseEntity<>(fireDTO, HttpStatus.OK);
	}

	/**
	 * Gets the households by station.
	 *
	 * @param stations the stations
	 * @return the households by station
	 */
	@GetMapping("/flood/stations")
	public ResponseEntity<FloodDTO> getHouseholdsByStation(
			@RequestParam("stations")
			@Valid @NotEmpty final List<Integer> stations) {
		log.debug("GET Request - /flood for stations {}", stations);
		FloodDTO floodDTO = alertsUrlsService
				.getHousesCoveredByStation(stations);
		log.debug("GET Request - /flood for stations {}"
				+ " - 200 OK", stations);
		return new ResponseEntity<>(floodDTO, HttpStatus.OK);
	}

	/**
	 * Gets the person info by identity.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 * @return the person info by identity
	 */
	@GetMapping("/personInfo")
	public ResponseEntity<PersonInfoDTO> getPersonInfoByIdentity(
			@RequestParam("firstName")
			@Valid @NotBlank final String firstName,
			@RequestParam("lastName")
			@Valid @NotBlank final String lastName) {
		log.debug("GET Request - /personInfo for first name {}"
				+ " and last name {}", firstName, lastName);
		PersonInfoDTO personInfoDTO = alertsUrlsService
				.getInfoPersonByIdentity(firstName, lastName);
		log.debug("GET Request - /personInfo for first name {}"
				+ " and last name {}"
				+ " - 200 OK", firstName, lastName);
		return new ResponseEntity<>(personInfoDTO, HttpStatus.OK);
	}

	/**
	 * Gets the emails by city.
	 *
	 * @param city the city
	 * @return the emails by city
	 */
	@GetMapping("/communityEmail")
	public ResponseEntity<CommunityEmailDTO> getEmailsByCity(
			@RequestParam("city")
			@Valid @NotBlank final String city) {
		log.debug("GET Request - /communityEmail for city {}", city);
		CommunityEmailDTO communityEmailDTO = alertsUrlsService
				.getEmailsByCity(city);
		log.debug("GET Request - /communityEmail for city {}"
				+ " - 200 OK", city);
		return new ResponseEntity<>(communityEmailDTO, HttpStatus.OK);
	}
}
