package com.safetynet.alerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.FireStationDTO;
import com.safetynet.alerts.exception.BadRequestException;
import com.safetynet.alerts.service.IFireStationService;

/**
 * The Class FireStationController.
 */
@RestController
public class FireStationController {

   /** The fire station service. */
   private final IFireStationService fireStationService;

   /**
    * Instantiates a new fire station controller.
    *
    * @param fireStationEndpointService the fire station service
    */
   @Autowired
   public FireStationController(
		   final IFireStationService fireStationEndpointService) {
       this.fireStationService = fireStationEndpointService;
   }

   /**
    * Gets the fire station.
    *
    * @param station the station
    * @param address the address
    * @return the fire station
    */
   @GetMapping("/firestation")
   public ResponseEntity<FireStationDTO> getFireStation(
		   @RequestParam("station") final Integer station,
		   @RequestParam("address") final String address) {

       fireStationValidityCheckForDeleteAndGet(station, address);
       FireStationDTO fireDTO = fireStationService
    		   .getFireStationById(station, address);

       return new ResponseEntity<>(fireDTO, HttpStatus.OK);
   }

   /**
    * Adds the new fire station.
    *
    * @param fireStation the fire station
    * @return the response entity
    */
   @PostMapping("/firestation")
   public ResponseEntity<FireStationDTO> addNewFireStation(
		   @RequestBody final FireStationDTO fireStation) {

       fireStationValidityCheckForAddAndUpdate(fireStation);
       FireStationDTO fireStationCreated = fireStationService
    		   .addNewFireStation(fireStation);

       return new ResponseEntity<>(fireStationCreated, HttpStatus.CREATED);
   }

   /**
    * Update fire station.
    *
    * @param fireStation the fire station
    * @return the response entity
    */
   @PutMapping("/firestation")
   public ResponseEntity<FireStationDTO> updateFireStation(
		   @RequestBody final FireStationDTO fireStation) {

       fireStationValidityCheckForAddAndUpdate(fireStation);
       FireStationDTO fireStationUpdated = fireStationService
    		   .updateExistingStation(fireStation);

       return new ResponseEntity<>(fireStationUpdated, HttpStatus.OK);
   }

   /**
    * Delete fire station.
    *
    * @param station the station
    * @param address the address
    * @return the response entity
    */
   @DeleteMapping("/firestation")
   public ResponseEntity<Void> deleteFireStation(
		   @RequestParam("station") final Integer station,
		   @RequestParam("address") final String address) {

       fireStationValidityCheckForDeleteAndGet(station, address);
       fireStationService.deleteExistingStation(station, address);

       return new ResponseEntity<>(HttpStatus.OK);
   }

	/**
	 * Fire station validity check for add and update.
	 *
	 * @param fireStation the fire station
	 */
	private void fireStationValidityCheckForAddAndUpdate(
			final FireStationDTO fireStation) {
		boolean fireStationAddressIsNull = (fireStation
				.getAddress() == null);
		boolean fireStationAddressIsEmpty = (fireStation
				.getAddress().isEmpty());

		if (fireStationAddressIsNull
				|| fireStationAddressIsEmpty) {
          throw new BadRequestException("Response Status:"
          		+ " 400 Bad request - invalid or empty request body");
      }
	}

	/**
	 * Fire station validity check for delete and get.
	 *
	 * @param station the station
	 * @param address the address
	 */
	private void fireStationValidityCheckForDeleteAndGet(
			final Integer station,
			final String address) {
		boolean fireStationAddressIsNull = (address == null);
		boolean fireStationAddressLengthIsZero
		= (address.trim().length() == 0);
		boolean stationIdIsNull = (station == null);

		if (fireStationAddressIsNull
				|| fireStationAddressLengthIsZero
				|| stationIdIsNull) {
          throw new BadRequestException("Response Status:"
          		+ " 400 Bad request - invalid parameter input");
      }
	}
}
