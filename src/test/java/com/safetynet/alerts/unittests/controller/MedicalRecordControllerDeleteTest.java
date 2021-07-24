package com.safetynet.alerts.unittests.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.safetynet.alerts.controller.MedicalRecordController;
import com.safetynet.alerts.service.MedicalRecordService;

@DisplayName("MEDICAL RECORD DELETE Endpoint Controller - Unit Tests")
@ExtendWith(SpringExtension.class)
@WebMvcTest(MedicalRecordController.class)
public class MedicalRecordControllerDeleteTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

  
    // ***************************************************************************************************

    
    @Test
    @DisplayName("Check for (VALID Medical Record ids)"
    		+ " - Given VALID PERSON-ID,"
    		+ " when DELETE request (/medicalRecord?firstName={firstName}&lastName={lastName}),"
    		+ " then return - Status: 200 OK")
    public void testDeleteMedicalRecordRequestWithValidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.delete("/medicalRecord?firstName=Test FirstName&lastName=Test Last Name"))
                .andExpect(status()
                		.isOk());

        verify(medicalRecordService)
        .deleteMedicalRecord(anyString(), anyString());
        verify(medicalRecordService, times(1))
        .deleteMedicalRecord(anyString(), anyString());
    }
    
    @Test
    @DisplayName("Check for (INVALID PERSON-ID no firstname)"
    		+ " - Given INVALID PERSON-ID without firstname,"
    		+ " when DELETE request (/medicalRecord?firstName={}&lastName={lastName}),"
    		+ " then return - Status: 400 Bad Request")
    public void testDeleteMedicalRecordRequestWithIdWithoutFirstname() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.delete("/medicalRecord?firstName=&lastName=Test LastName"))
                .andExpect(status()
                		.isBadRequest());
    }
    
    @Test
    @DisplayName("Check for (INVALID PERSON-ID no lastname)"
    		+ " - Given INVALID PERSON-ID without lastname,"
    		+ " when DELETE request (/medicalRecord?firstName={firstName}&lastName={}),"
    		+ " then return - Status: 400 Bad Request")
    public void testDeleteMedicalRecordRequestWithIdWithoutLastname() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.delete("/medicalRecord?firstName=Test FirstName&lastName="))
                .andExpect(status()
                		.isBadRequest());
    }
    
    @Test
    @DisplayName("check for (without any input)"
    		+ " - Given INVALID PERSON-ID without input,"
    		+ " when DELETE request (/medicalRecord?firstName={}&lastName={}),"
    		+ " then return - Status: 400 Bad Request")
    public void testDeleteMedicalRecordRequestWithIdNoIdInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.delete("/medicalRecord?firstName=&lastName="))
                .andExpect(status()
                		.isBadRequest());
    }
}
