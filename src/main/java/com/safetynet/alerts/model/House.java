package com.safetynet.alerts.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * House DO.
 * @author Senthil
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class House {

    /**
     * address.
     */
    private String address;

    /**
     * person address list.
     */
    private List<PersonAddress> personsList;
}
