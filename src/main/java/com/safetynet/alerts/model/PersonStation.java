package com.safetynet.alerts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Person station DO.
 * @author Senthil
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class PersonStation {

    /**
     * first name.
     */
    private String firstName;

    /**
     * last name.
     */
    private String lastName;

    /**
     * address.
     */
    private String address;

    /**
     * phone.
     */
    private String phone;

}
