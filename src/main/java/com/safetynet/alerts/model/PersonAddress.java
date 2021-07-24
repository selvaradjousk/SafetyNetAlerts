package com.safetynet.alerts.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PersonAddress {

    private String lastName;

    private String phone;

    private int age;

    List<String> medications;

    List<String> allergies;

}
