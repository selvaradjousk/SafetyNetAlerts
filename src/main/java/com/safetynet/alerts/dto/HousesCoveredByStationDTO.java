package com.safetynet.alerts.dto;

import java.util.List;

import com.safetynet.alerts.model.House;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HousesCoveredByStationDTO {

    private int station;

    private List<House> households;
}