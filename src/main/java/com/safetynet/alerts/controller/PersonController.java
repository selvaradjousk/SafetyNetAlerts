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

import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.exception.BadRequestException;
import com.safetynet.alerts.service.IPersonService;

import lombok.extern.log4j.Log4j2;


/**
 * Provides methods for CRUD operations on Person data resource
 *  using HTTP requests to access and use data.
 * @author Senthil
 *
 */
@Log4j2
@RestController
@Validated
public class PersonController {

	/**
	 * Person Service interface class.
	 */
	private final IPersonService personService;

	/**
	 * Constructor for PersonController Class.
	 * @param personEndpointService
	 */
	@Autowired
	public PersonController(
			final IPersonService personEndpointService) {
		this.personService = personEndpointService;
	}

    /**
	 * Gets a person data from the Person resource list
     *  based on its requested URI specified.
     * @param firstName key value of the requested URI
     * @param lastName  key value of the requested URI
     * @return Response Entity return HTTP response
     *  - includes status, body and header
     */
	@GetMapping("/person")
	public ResponseEntity<PersonDTO> getPerson(
			@RequestParam("firstName")
			@Valid @NotBlank final String firstName,
			@RequestParam("lastName")
			@Valid @NotBlank final String lastName) {
		log.debug("Person GET Request : {} {}", firstName, lastName);
		PersonDTO personDTO = personService
				.getPersonById(firstName, lastName);
		log.debug("Person GET Request : {} {}"
				+ "- 200 OK", firstName, lastName);
		return new ResponseEntity<>(personDTO, HttpStatus.OK);
	}

	/**
     * Adds a new person resource based
     *  on its requested URI specified.
     * @param person input value of the requested URI
     * @return Response Entity return HTTP response
     *  - includes status, body and header
     */
    @PostMapping("/person")
    public ResponseEntity<PersonDTO> addNewPerson(
    		@Valid @RequestBody final PersonDTO person) {
    	log.debug("Person ADD Request : "
    		+ person.getFirstName() + " " + person.getLastName());
        PersonDTO personAdded = personService
        		.addNewPerson(person);
    	log.debug("Person ADD Request : "
        		+ person.getFirstName() + " " + person.getLastName()
        		+ "- 201 CREATED");
        return new ResponseEntity<>(personAdded, HttpStatus.CREATED);
    }

    /**
     * Updates a existing person resource
     *  based on its requested URI specified.
     * @param person input value of the requested URI
     * @return Response Entity return HTTP response
     *  - includes status, body and header
     */
    @PutMapping("/person")
    public ResponseEntity<PersonDTO> updateExistingPerson(
    		@Valid @RequestBody
    		final PersonDTO person) throws BadRequestException {
    	log.debug("Person UPDATE Request : "
        		+ person.getFirstName() + " " + person.getLastName());
        PersonDTO personUpdated = personService
        		.updateExistingPerson(person);
    	log.debug("Person UPDATE Request : "
        		+ person.getFirstName() + " " + person.getLastName()
        		+ "- 200 OK");
        return new ResponseEntity<>(personUpdated, HttpStatus.OK);
    }

    /**
     * Deletes a person resource based
     *  on its requested URI specified.
     * @param firstName key value of the requested URI
     * @param lastName  key value of the requested URI
     * @return Response Entity return HTTP status response
     */
    @DeleteMapping("/person")
    public ResponseEntity<Void> deletePerson(
    		@RequestParam("firstName")
    		@Valid @NotBlank final String firstName,
    		@RequestParam("lastName")
    		@Valid @NotBlank final String lastName) {
		log.debug("Person DELETE Request : {} {}",
				firstName, lastName);
        personService.deleteExistingPerson(firstName, lastName);
		log.debug("Person DELETE Request : {} {}"
				+ "- 200 OK", firstName, lastName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
