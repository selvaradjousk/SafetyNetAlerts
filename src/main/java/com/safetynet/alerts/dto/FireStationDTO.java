package com.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * FireStation DTO.
 *
 * @author Senthil
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FireStationDTO {

    /**
     * FireStation Id.
     */
    private int stationId;

    /**
     * FireStation Address.
     */
    private String address;
}
