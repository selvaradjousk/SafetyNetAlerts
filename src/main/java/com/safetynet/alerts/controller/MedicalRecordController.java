package com.safetynet.alerts.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

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

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.service.IMedicalRecordService;

import lombok.extern.log4j.Log4j2;

/**
 * The Class MedicalRecordController.
 */
@Log4j2
@RestController
@Validated
public class MedicalRecordController {

    /** The medical record service. */
    private final IMedicalRecordService medicalRecordService;

   /**
    * Instantiates a new medical record controller.
    *
    * @param medicalRecordEndPointService the medical record service
    */
   @Autowired
   public MedicalRecordController(
		   final IMedicalRecordService medicalRecordEndPointService) {
       this.medicalRecordService = medicalRecordEndPointService;
   }

   /**
    * Gets the medical record.
    *
    * @param firstName the first name
    * @param lastName the last name
    * @return the medical record
    */
   @GetMapping("/medicalRecord")
   public ResponseEntity<MedicalRecordDTO> getMedicalRecord(
		   @RequestParam("firstName")
		   @Valid @NotBlank final String firstName,
		   @RequestParam("lastName")
		   @Valid @NotBlank final String lastName) {
	   log.debug("MedicalRecord GET Request - {} {}",
			   firstName, lastName);
       MedicalRecordDTO medDTO = medicalRecordService
    		   .getMedicalRecordById(firstName, lastName);
	   log.debug("MedicalRecord GET Request - {} {} - 200 OK",
			   firstName, lastName);
       return new ResponseEntity<>(medDTO, HttpStatus.OK);
   }

   /**
    * Creates the medical record.
    *
    * @param medicalRecord the medical record
    * @return the response entity
    */
   @PostMapping("/medicalRecord")
   public ResponseEntity<MedicalRecordDTO> createMedicalRecord(
		   @Valid @RequestBody
		   final MedicalRecordDTO medicalRecord) {
	   log.debug("MedicalRecord ADD Request - "
		   + medicalRecord.getFirstName()
		   + " " + medicalRecord.getLastName());
       MedicalRecordDTO medicalRecordCreated = medicalRecordService
    		   .addNewMedicalRecord(medicalRecord);
	   log.debug("MedicalRecord ADD Request - "
			   + medicalRecord.getFirstName()
			   + " " + medicalRecord.getLastName()
			   + " - 201 CREATED");
        return new ResponseEntity<>(medicalRecordCreated, HttpStatus.CREATED);
   }

   /**
    * Update medical record.
    *
    * @param medicalRecord the medical record
    * @return the response entity
    */
   @PutMapping("/medicalRecord")
   public ResponseEntity<MedicalRecordDTO> updateMedicalRecord(
		  @Valid @RequestBody final MedicalRecordDTO medicalRecord) {
	   log.debug("MedicalRecord UPDATE Request - "
			   + medicalRecord.getFirstName()
			   + " " + medicalRecord.getLastName());
       MedicalRecordDTO medicalRecordUpdated = medicalRecordService
    		   .updateMedicalRecord(medicalRecord);
	   log.debug("MedicalRecord UPDATE Request - "
			   + medicalRecord.getFirstName()
			   + " " + medicalRecord.getLastName()
			   + " - 200 OK");
         return new ResponseEntity<>(medicalRecordUpdated, HttpStatus.OK);
   }

   /**
    * Delete medical record.
    *
    * @param firstName the first name
    * @param lastName the last name
    * @return the response entity
    */
   @DeleteMapping("/medicalRecord")
   public ResponseEntity<Void> deleteMedicalRecord(
		   @RequestParam("firstName")
		   @Valid @NotBlank final String firstName,
		   @RequestParam("lastName")
		   @Valid @NotBlank final String lastName) {
	   log.debug("MedicalRecord DELETE Request - {} {}",
			   firstName, lastName);
       medicalRecordService
       .deleteMedicalRecord(firstName, lastName);
	   log.debug("MedicalRecord DELETE Request - {} {} - 200 OK",
			   firstName, lastName);
       return new ResponseEntity<>(HttpStatus.OK);
   }
}
