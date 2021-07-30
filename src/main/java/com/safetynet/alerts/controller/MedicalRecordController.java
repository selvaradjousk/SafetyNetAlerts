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

/**
 * The Class MedicalRecordController.
 */
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
		   @Valid @RequestBody
		   final MedicalRecordDTO medicalRecord) {

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
		  @Valid @RequestBody final MedicalRecordDTO medicalRecord) {

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
		   @RequestParam("firstName")
		   @Valid @NotBlank final String firstName,
		   @RequestParam("lastName")
		   @Valid @NotBlank final String lastName) {

       medicalRecordService
       .deleteMedicalRecord(firstName, lastName);

       return new ResponseEntity<>(HttpStatus.OK);
   }
}
