package com.safetynet.alerts.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Phone alerts DTO.
 * @author Senthil
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneAlertDTO {

    /**
     * phones list.
     */
    private List<String> phones;
}
