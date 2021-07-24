package com.safetynet.alerts.dto;

import java.util.List;

import com.safetynet.alerts.model.Child;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChildAlertDTO {

    private List<Child> child;

    private List<String> homeMembers;
}