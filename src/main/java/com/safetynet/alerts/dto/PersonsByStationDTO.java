package com.safetynet.alerts.dto;

import java.util.List;

import com.safetynet.alerts.model.PersonStation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Person by station DTO.
 * @author Senthil
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonsByStationDTO {

    /**
     * person by station list.
     */
    private List<PersonStation> personsByStation;

    /**
     * adult number.
     */
    private int adultNumber;

    /**
     * child number.
     */
    private int childNumber;
}
