package com.safetynet.alerts.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Community email DTO.
 * @author Senthil
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityEmailDTO {

    /**
     * emails list.
     */
    private List<String> emails;
}
