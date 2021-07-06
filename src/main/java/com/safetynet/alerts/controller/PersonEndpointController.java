package com.safetynet.alerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.PersonEndpointDTO;
import com.safetynet.alerts.service.PersonEndpointService;

@RestController
public class PersonEndpointController {

	private final PersonEndpointService personEndpointService;

	@Autowired
	public PersonEndpointController(final PersonEndpointService personEndpointService) {
		this.personEndpointService = personEndpointService;
	}

	@GetMapping("/person")
	public ResponseEntity<PersonEndpointDTO> getPerson(@RequestParam("firstName") final String firstName,
			@RequestParam("lastName") final String lastName) {

		PersonEndpointDTO personDTO = personEndpointService.getPersonById(firstName, lastName);

		return new ResponseEntity<>(personDTO, HttpStatus.OK);
	}

}