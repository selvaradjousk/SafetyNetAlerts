package com.safetynet.alerts.dto;

import java.util.List;

import com.safetynet.alerts.model.House;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Houses covered by station DTO.
 * @author Senthil
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HousesCoveredByStationDTO {

    /**
     * station number.
     */
    private int station;

    /**
     * households list.
     */
    private List<House> households;
}
