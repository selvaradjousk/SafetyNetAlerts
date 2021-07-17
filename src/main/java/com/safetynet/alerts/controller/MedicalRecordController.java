package com.safetynet.alerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.service.IMedicalRecordService;

@RestController
public class MedicalRecordController {

    private final IMedicalRecordService medicalRecordService;


   @Autowired
   public MedicalRecordController(final IMedicalRecordService medicalRecordService) {
       this.medicalRecordService = medicalRecordService;
   }

}
