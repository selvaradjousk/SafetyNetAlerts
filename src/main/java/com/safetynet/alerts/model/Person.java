package com.safetynet.alerts.model;

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
public class Person {

	private String firstName;
	private String lastName;
	private String address;
	private String city;
    private int zip;
	private String phone;
	private String email;
}
