package com.safetynet.alerts.dto;

import java.util.List;

import com.safetynet.alerts.model.PersonInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfoDTO {

    private List<PersonInfo> personsInfo;
}