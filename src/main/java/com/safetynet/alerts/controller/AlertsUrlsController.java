package com.safetynet.alerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.safetynet.alerts.exception.BadRequestException;
import com.safetynet.alerts.service.IAlertsUrlsService;

/**
 * The Class AlertsUrlsController.
 */
@RestController
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
			@RequestParam("stationNumber") final Integer station) {

		if (station == null) {
			throw new BadRequestException(
					"Response Status: 404 Bad request"
					+ " - missing input values for station number");
		}
		PersonsByStationDTO personsByStationDTO = alertsUrlsService
				.getPersonsByStation(station);

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
			@RequestParam("address") final String address) {

		if (address.trim().length() == 0) {
			throw new BadRequestException(
					"Response Status: 404 Bad request"
					+ " - missing input values for child address");
		}
		ChildAlertDTO childAlertDTO = alertsUrlsService
				.getChildByAddress(address);

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
			@RequestParam("firestation") final Integer station) {


		if (station == null) {
			throw new BadRequestException(
					"Response Status: 404 Bad request"
					+ " - missing input values for stations");
		}
		PhoneAlertDTO phoneAlertDTO = alertsUrlsService
				.getPhonesByStation(station);

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
			@RequestParam("address") final String address) {

		if (address.trim().length() == 0) {
			throw new BadRequestException(
					"Response Status: 404 Bad request"
					+ " - missing input values for address");
		}
		FireDTO fireDTO = alertsUrlsService
				.getPersonsByAddress(address);

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
			final List<Integer> stations) {

		if (stations.isEmpty()) {
			throw new BadRequestException(
					"Response Status: 404 Bad request"
					+ " - missing input values for station numbers");
		}
		FloodDTO floodDTO = alertsUrlsService
				.getHousesCoveredByStation(stations);

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
			@RequestParam("firstName") final String firstName,
			@RequestParam("lastName") final String lastName) {

		if (firstName.trim().length() == 0
				|| lastName.trim().length() == 0) {
			throw new BadRequestException(
					"Response Status: 404 Bad request"
					+ " - missing input values for person id");
		}
		PersonInfoDTO personInfoDTO = alertsUrlsService
				.getInfoPersonByIdentity(firstName, lastName);

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
			@RequestParam("city") final String city) {

		if (city.trim().length() == 0) {
			throw new BadRequestException(
					"Response Status: 404 Bad request"
					+ " - missing input values for email");
		}
		CommunityEmailDTO communityEmailDTO = alertsUrlsService
				.getEmailsByCity(city);

		return new ResponseEntity<>(communityEmailDTO, HttpStatus.OK);
	}
}
