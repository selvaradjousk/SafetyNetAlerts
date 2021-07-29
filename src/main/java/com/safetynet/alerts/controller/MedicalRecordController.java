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

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.exception.BadRequestException;
import com.safetynet.alerts.service.IMedicalRecordService;

/**
 * The Class MedicalRecordController.
 */
@RestController
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
		   @RequestParam("firstName") final String firstName,
		   @RequestParam("lastName") final String lastName) {

       if (firstName == null
    		   || firstName.trim().length() == 0
    		   || lastName == null
               || lastName.trim().length() == 0) {
           throw new BadRequestException("Response: "
           		+ "404 Bad request missing parameter values");
       }
       MedicalRecordDTO medDTO = medicalRecordService
    		   .getMedicalRecordById(firstName, lastName);

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
		   @RequestBody final MedicalRecordDTO medicalRecord) {

       if (medicalRecord.getFirstName() == null
    		   || medicalRecord.getFirstName().isEmpty()
    		   || medicalRecord.getLastName() == null
    		   || medicalRecord.getLastName().isEmpty()) {
           throw new BadRequestException("Response:"
           		+ " 404 Bad request missing info in request body");
       }
       MedicalRecordDTO medicalRecordCreated = medicalRecordService
    		   .addNewMedicalRecord(medicalRecord);

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
		   @RequestBody final MedicalRecordDTO medicalRecord) {

       if (medicalRecord.getFirstName() == null
    		   || medicalRecord.getFirstName().isEmpty()
    		   || medicalRecord.getLastName() == null
    		   || medicalRecord.getLastName().isEmpty()) {
           throw new BadRequestException("Response:"
              		+ " 404 Bad request missing info in request body");
       }
       MedicalRecordDTO medicalRecordUpdated = medicalRecordService
    		   .updateMedicalRecord(medicalRecord);

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
		   @RequestParam("firstName") final String firstName,
		   @RequestParam("lastName") final String lastName) {

       if (firstName == null
    		   || firstName.trim().length() == 0
    		   || lastName == null
               || lastName.trim().length() == 0) {
           throw new BadRequestException("Response: "
              		+ "400 Bad request missing parameter values");
       }
       medicalRecordService
       .deleteMedicalRecord(firstName, lastName);

       return new ResponseEntity<>(HttpStatus.OK);
   }
}
