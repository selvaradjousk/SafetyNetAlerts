package com.safetynet.alerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
   public ResponseEntity<FireStationDTO> getFireStation(@RequestParam("address") final String address,
                                                        @RequestParam("station") final Integer station) {

       fireStationValidityCheckForDeleteAndGet(address, station);
       FireStationDTO fireDTO = fireStationService.getFireStationById(station, address);

       return new ResponseEntity<>(fireDTO, HttpStatus.OK);
   }
   
   
   
   
	/**
	 * FireStation Validity Check For Delete And Get Request Methods
	 * 
	 * @param address
	 * @param station
	 */
	private void fireStationValidityCheckForDeleteAndGet(final String address, final Integer station) {
		boolean fireStationAddressIsNull = (address == null);
		boolean fireStationAddressLengthIsZero = (address.trim().length() == 0);
		boolean stationIdIsNull = (station == null);
		
		if (fireStationAddressIsNull || fireStationAddressLengthIsZero || stationIdIsNull) {
          throw new BadRequestException("Bad request : missing or incomplete parameter");
      }
	} 
 
}