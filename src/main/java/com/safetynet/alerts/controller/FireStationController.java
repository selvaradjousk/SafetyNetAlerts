package com.safetynet.alerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.service.IFireStationService;

@RestController
public class FireStationController {

   private final IFireStationService fireStationService;

   @Autowired
   public FireStationController(final IFireStationService fireStationService) {
       this.fireStationService = fireStationService;
   }
 
}