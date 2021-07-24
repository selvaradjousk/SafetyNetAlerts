package com.safetynet.alerts.dto;

import java.util.List;

import com.safetynet.alerts.model.PersonAddress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FireDTO {

    private int station;

    private List<PersonAddress> persons;
}