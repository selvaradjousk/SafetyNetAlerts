package com.safetynet.alerts.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordDTO {


    private String firstName;


    private String lastName;


    private String birthDate;


    private List<String> medications;


    private List<String> allergies;
}
