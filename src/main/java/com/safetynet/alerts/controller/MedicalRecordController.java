package com.safetynet.alerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.exception.BadRequestException;
import com.safetynet.alerts.service.IMedicalRecordService;

@RestController
public class MedicalRecordController {

    private final IMedicalRecordService medicalRecordService;


   @Autowired
   public MedicalRecordController(final IMedicalRecordService medicalRecordService) {
       this.medicalRecordService = medicalRecordService;
   }

   @GetMapping("/medicalRecord")
   public ResponseEntity<MedicalRecordDTO> getMedicalRecord(@RequestParam("firstName") final String firstName,
                                                            @RequestParam("lastName") final String lastName) {

       if (firstName == null || firstName.trim().length() == 0 || lastName == null
               || lastName.trim().length() == 0) {
           throw new BadRequestException("Bad request : missing or incomplete parameter");
       }
       MedicalRecordDTO medDTO = medicalRecordService.getMedicalRecordById(firstName, lastName);

       return new ResponseEntity<>(medDTO, HttpStatus.OK);
   }
   
   
}
