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

@RestController
public class FireStationController {

   private final IFireStationService fireStationService;

   @Autowired
   public FireStationController(final IFireStationService fireStationService) {
       this.fireStationService = fireStationService;
   }
   
   
   @GetMapping("/fireStation")
   public ResponseEntity<FireStationDTO> getFireStation(
		   @RequestParam("address") final String address,
		   @RequestParam("station") final Integer station) {

       fireStationValidityCheckForDeleteAndGet(address, station);
       FireStationDTO fireDTO = fireStationService
    		   .getFireStationById(station, address);

       return new ResponseEntity<>(fireDTO, HttpStatus.OK);
   }
   
   
   @PostMapping("/firestation")
   public ResponseEntity<FireStationDTO> addNewFireStation(
		   @RequestBody final FireStationDTO fireStation) {

       fireStationValidityCheckForAddAndUpdate(fireStation);
       FireStationDTO fireStationCreated = fireStationService
    		   .addNewFireStation(fireStation);

       return new ResponseEntity<>(fireStationCreated, HttpStatus.CREATED);
   }
   
   @PutMapping("/firestation")
   public ResponseEntity<FireStationDTO> updateFireStation(
		   @RequestBody final FireStationDTO fireStation) {

       fireStationValidityCheckForAddAndUpdate(fireStation);
       FireStationDTO fireStationUpdated = fireStationService
    		   .updateExistingStation(fireStation);

       return new ResponseEntity<>(fireStationUpdated, HttpStatus.OK);
   } 
   
   @DeleteMapping("/firestation")
   public ResponseEntity<Void> deleteFireStation(
		   @RequestParam("address") final String address,
		   @RequestParam("station") final Integer station) {

       fireStationValidityCheckForDeleteAndGet(address, station);
       fireStationService.deleteExistingStation(station, address);

       return new ResponseEntity<>(HttpStatus.OK);
   }
   
   
	/**
	 * FireStation Validity Check For Add and Update Request Methods
	 * @param fireStation
	 */
	private void fireStationValidityCheckForAddAndUpdate(
			final FireStationDTO fireStation) {
		boolean fireStationAddressIsNull = (fireStation.getAddress() == null);
		boolean fireStationAddressIsEmpty = (fireStation.getAddress().isEmpty());
		
		if (fireStationAddressIsNull
				|| fireStationAddressIsEmpty) {
          throw new BadRequestException("Response Status:"
          		+ " 400 Bad request - invalid or empty request body");
      }
	}


	/**
	 * FireStation Validity Check For Delete And Get Request Methods
	 * 
	 * @param address
	 * @param station
	 */
	private void fireStationValidityCheckForDeleteAndGet(
			final String address,
			final Integer station) {
		boolean fireStationAddressIsNull = (address == null);
		boolean fireStationAddressLengthIsZero = (address.trim().length() == 0);
		boolean stationIdIsNull = (station == null);
		
		if (fireStationAddressIsNull
				|| fireStationAddressLengthIsZero
				|| stationIdIsNull) {
          throw new BadRequestException("Response Status:"
          		+ " 400 Bad request - invalid parameter input");
      }
	} 
 
}