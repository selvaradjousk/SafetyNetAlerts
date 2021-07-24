package com.safetynet.alerts.dto;

import java.util.List;

import com.safetynet.alerts.model.PersonStation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonsByStationDTO {

    private List<PersonStation> personsByStation;

    private int adultNumber;

    private int childNumber;
}
