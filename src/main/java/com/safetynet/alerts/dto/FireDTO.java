package com.safetynet.alerts.dto;

import java.util.List;

import com.safetynet.alerts.model.PersonAddress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Fire DTO.
 * @author Senthil
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FireDTO {

    /**
     * station number.
     */
    private int station;

    /**
     * persons by address list.
     */
    private List<PersonAddress> persons;
}
