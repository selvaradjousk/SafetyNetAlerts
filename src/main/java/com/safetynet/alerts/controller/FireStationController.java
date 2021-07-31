package com.safetynet.alerts.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import lombok.extern.log4j.Log4j2;

/**
 * The Class FireStationController.
 */
@Log4j2
@RestController
@Validated
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
		   @RequestParam("station")
		   @Valid @NotNull final Integer station,
		   @RequestParam("address")
		   @Valid @NotBlank final String address) {
	   log.debug("FireStation GET Request : {} {}",
			   address, station);
       FireStationDTO fireDTO = fireStationService
    		   .getFireStationById(station, address);
	   log.debug("FireStation GET Request : {} {} - 200 OK",
			   address, station );
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
       log.debug("FireStation ADD Request - address :"
       		+ " {} and station number : {}",
               fireStation.getStationId(), fireStation.getAddress());
       fireStationValidityCheckForAddAndUpdate(fireStation);
       FireStationDTO fireStationCreated = fireStationService
    		   .addNewFireStation(fireStation);
       log.debug("FireStation ADD Request - address :"
       		+ " {} and station number : {}",
               fireStation.getStationId(), fireStation.getAddress()
               + " - 200 OK");
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
       log.debug("FireStation UPDATE Request - address :"
          		+ " {} and station number : {}",
                  fireStation.getStationId(), fireStation.getAddress());
       fireStationValidityCheckForAddAndUpdate(fireStation);
       FireStationDTO fireStationUpdated = fireStationService
    		   .updateExistingStation(fireStation);
       log.debug("FireStation UPDATE Request - address :"
          		+ " {} and station number : {}",
                  fireStation.getStationId(), fireStation.getAddress()
                  + " - 200 OK");
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
		   @RequestParam("station")
		   @Valid @NotNull final Integer station,
		   @RequestParam("address")
		   @Valid @NotBlank final String address) {
	   log.debug("FireStation DELETE Request : {} {}",
			   address, station);
       fireStationService.deleteExistingStation(station, address);
	   log.debug("FireStation DELETE Request : {} {} - 200 OK",
			   address, station);
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
}
