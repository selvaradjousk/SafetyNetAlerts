package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.dto.ChildAlertDTO;
import com.safetynet.alerts.dto.CommunityEmailDTO;
import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.dto.FloodDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.dto.PersonsByStationDTO;
import com.safetynet.alerts.dto.PhoneAlertDTO;

/**
 * Alerts Service interface.
 * @author Senthil
 *
 */
public interface IAlertsUrlsService {

    /**
     * Gets Persons By Station.
     *
     * @param station the station
     * @return PersonsByStationDTO(list, adultCount, childCount)
     */
    PersonsByStationDTO getPersonsByStation(int station);

    /**
     * Get child by address.
     *
     * @param address the address
     * @return ChildAlertDTO(childList, adultList)
     */
    ChildAlertDTO getChildByAddress(String address);

    /**
     * Gets phone by station.
     *
     * @param station the station
     * @return PhoneAlertDTO(phones)
     */
    PhoneAlertDTO getPhonesByStation(int station);

    /**
     * Gets persons by address.
     *
     * @param address the address
     * @return FireDTO(station, persons)
     */
    FireDTO getPersonsByAddress(String address);

    /**
     * Gets houses covered by station.
     *
     * @param stations the stations
     * @return FloodDTO(housesCoveredByStationDTO)
     */
    FloodDTO getHousesCoveredByStation(List<Integer> stations);

    /**
     * Gets the info person by identity.
     *
     * @param firstName the first name
     * @param lastName the last name
     * @return the info person by identity
     */
    PersonInfoDTO getInfoPersonByIdentity(String firstName, String lastName);

	// http://localhost:8080/communityEmail?city=<city>
	// i.e. communityEmail(city)
    /**
	 * Gets the emails by city.
	 *
	 * @param city the city
	 * @return the emails by city
	 */
	CommunityEmailDTO getEmailsByCity(
    		String city);
}
