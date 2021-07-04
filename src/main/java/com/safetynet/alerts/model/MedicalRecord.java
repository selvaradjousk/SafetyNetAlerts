package com.safetynet.alerts.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MedicalRecord {

    private String firstName;
    private String lastName;
    private String birthDate;
    private List<String> medications;
    private List<String> allergies;
}