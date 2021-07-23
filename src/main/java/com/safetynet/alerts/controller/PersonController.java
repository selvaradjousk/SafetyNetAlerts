package com.safetynet.alerts.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator;
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

import com.safetynet.alerts.dto.PersonDTO;
import com.safetynet.alerts.exception.BadRequestException;
import com.safetynet.alerts.service.IPersonService;


/**
 * Provides methods for CRUD operations on Person data resource
 *  using HTTP requests to access and use data.
 * @author Senthil
 *
 */
@RestController
public class PersonController {

	/**
	 * Person Service interface class.
	 */
	private final IPersonService personService;

	/**
	 * Constructor for PersonController Class.
	 * @param personService
	 */
	@Autowired
	public PersonController(
			final IPersonService personService) {
		this.personService = personService;
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
			@Valid @NotBlank @RequestParam("firstName") final String firstName,
			@Valid @NotBlank @RequestParam("lastName") final String lastName) {
        if (firstName == null
        		|| firstName.trim().length() == 0
        		|| lastName == null
                || lastName.trim().length() == 0) {
            throw new BadRequestException("Response Status: 400 Bad Request"
            		+ " -> The parameter entered has missing values"
            		+ " or invalid");
        }
		PersonDTO personDTO = personService
				.getPersonById(firstName, lastName);

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
    		@RequestBody final PersonDTO person) {

        if (person.getFirstName() == null
        		|| person.getFirstName().isEmpty()
        		|| person.getLastName() == null
                || person.getLastName().isEmpty()) {
            throw new BadRequestException("Response Status: 400 Bad request"
            		+ " The request body is incomplete"
            		+ " or missing required entries");
        }
        PersonDTO personAdded = personService
        		.addNewPerson(person);

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
    		@RequestBody final PersonDTO person) throws BadRequestException{

        if (person.getFirstName() == null
        		|| person.getFirstName().isEmpty()
        		|| person.getLastName() == null
                || person.getLastName().isEmpty()) {
            throw new BadRequestException("Response Status 400 Bad request"
            		+ " The request body is incomplete"
            		+ " or missing required entries");
        }
        PersonDTO personUpdated = personService
        		.updateExistingPerson(person);

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
    		@RequestParam("firstName") final String firstName,
    		@RequestParam("lastName") final String lastName) {

		if (firstName == null
				|| firstName.trim().length() == 0
				|| lastName == null
                || lastName.trim().length() == 0) {
            throw new BadRequestException("Response Status: 400 Bad request:"
            		+ " The request body is incomplete "
            		+ "or missing required parameters entry");
        }
        personService.deleteExistingPerson(firstName, lastName);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
