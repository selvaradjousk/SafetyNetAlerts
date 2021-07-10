package com.safetynet.alerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.exception.BadRequestException;
import com.safetynet.alerts.service.IPersonService;

@RestController
public class PersonController {

	private final IPersonService personEndpointService;

	@Autowired
	public PersonController(final IPersonService personEndpointService) {
		this.personEndpointService = personEndpointService;
	}

	@GetMapping("/person")
	public ResponseEntity<PersonDTO> getPerson(@RequestParam("firstName") final String firstName,
			@RequestParam("lastName") final String lastName) {

        if (firstName == null
        		|| firstName.trim().length() == 0
        		|| lastName == null
                || lastName.trim().length() == 0) {
            throw new BadRequestException("Response Status: 400 Bad Request"
            		+ " -> The parameter entered has missing values or invalid");
        }
		PersonDTO personDTO = personEndpointService
				.getPersonById(firstName, lastName);

		return new ResponseEntity<>(personDTO, HttpStatus.OK);
	}
	
    @PostMapping("/person")
    public ResponseEntity<PersonDTO> addNewPerson(@RequestBody final PersonDTO person) {

        if (person.getFirstName() == null
        		|| person.getFirstName().isEmpty()
        		|| person.getLastName() == null
                || person.getLastName().isEmpty()) {
            throw new BadRequestException("Response Status: 401 Bad request "
            		+ "The request body is incomplete or missing required entries");
        }
        PersonDTO personAdded = personEndpointService
        		.addNewPerson(person);

        return new ResponseEntity<>(personAdded, HttpStatus.CREATED);
    }
}