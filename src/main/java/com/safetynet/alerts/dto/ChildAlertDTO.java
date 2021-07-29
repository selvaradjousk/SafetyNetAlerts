package com.safetynet.alerts.dto;

import java.util.List;

import com.safetynet.alerts.model.Child;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Child Alerts DTO.
 * @author Senthil
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChildAlertDTO {

    /**
     * Child list.
     */
    private List<Child> child;

    /**
     * Home members list.
     */
    private List<String> homeMembers;
}
