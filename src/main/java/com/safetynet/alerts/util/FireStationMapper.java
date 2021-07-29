package com.safetynet.alerts.util;

import org.springframework.stereotype.Component;

import com.safetynet.alerts.dto.FireStationDTO;
import com.safetynet.alerts.model.FireStation;

/**
 * Mapper for Fire station Data resource.
 * @author Senthil
 *
 */
@Component
public class FireStationMapper {

    /**
     * To fire station DTO.
     *
     * @param fireStation the fire station
     * @return the fire station DTO
     */
    public FireStationDTO toFireStationDTO(
    		final FireStation fireStation) {

        return new FireStationDTO(
        		fireStation.getStationId(),
        		fireStation.getAddress());
    }

    /**
     * To fire station.
     *
     * @param fireStationDTO the fire station DTO
     * @return the fire station
     */
    public FireStation toFireStation(
    		final FireStationDTO fireStationDTO) {

        return new FireStation(
        		fireStationDTO.getStationId(),
        		fireStationDTO.getAddress());
    }
}
