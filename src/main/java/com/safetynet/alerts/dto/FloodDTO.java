package com.safetynet.alerts.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FloodDTO {

    private List<HousesCoveredByStationDTO> householdsByStation;
}