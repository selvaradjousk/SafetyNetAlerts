package com.safetynet.alerts.unittests.controller;

import static com.safetynet.alerts.testingtoolsconfig.DataPreparation.displayAsJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.MedicalRecordController;
import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.service.MedicalRecordService;

@DisplayName("MEDICAL RECORD UPDATE Endpoint Controller - Unit Tests")
@ExtendWith(SpringExtension.class)
@WebMvcTest(MedicalRecordController.class)
public class MedicalRecordControllerUpdateTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    private ObjectMapper objectMapper;

    private MedicalRecordDTO medicalDTO;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        medicalDTO = MedicalRecordDTO.builder()
        		.firstName("Test FirstName")
        		.lastName("Test Last Name")
        		.birthDate("01/01/1960")
        		.medications(Arrays.asList("First symptom",
        				"Second medication",
        				"Third medication"))
                .allergies(Arrays.asList("First allergy",
                		"Second allergy",
                		"Third allergy"))
                .build();
    }

    // ***************************************************************************************************

    @Test
    @DisplayName("Check (Valid MedicalRecord input)"
    		+ " - Given a valid MedicalRecord to update,"
    		+ " when update request,"
    		+ " then return - Status 200 OK")
    public void testUpdateMedicalRecordRequestWithValidRecordInfo() throws Exception {

    	when(medicalRecordService
        		.updateMedicalRecord(any(MedicalRecordDTO.class)))
        .thenReturn(any(MedicalRecordDTO.class));

        mockMvc.perform(MockMvcRequestBuilders
        		.put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(medicalDTO)))
                .andExpect(status()
                		.isOk());

        verify(medicalRecordService)
        .updateMedicalRecord(any(MedicalRecordDTO.class));
        verify(medicalRecordService, times(1))
        .updateMedicalRecord(any(MedicalRecordDTO.class));
    }
    
    
    @Test
    @DisplayName("Check (incomplete id input firstname)"
    		+ " - Given an incomplete ID firstname,"
    		+ " when update request,"
    		+ " then return - Status: 400 Bad Request")
    public void testUpdateMedicalRecordRequestWithIncompleteIdNoFirstName() throws Exception {
        medicalDTO = MedicalRecordDTO.builder()
        		.firstName("")
        		.lastName("Test Last Name")
        		.birthDate("01/01/1960")
        		.medications(Arrays.asList("First symptom",
        				"Second medication",
        				"Third medication",
        				"terazine:500mg"))
                .allergies(Arrays.asList("First allergy",
                		"Second allergy",
                		"Third allergy"))
                .build();

        mockMvc.perform(MockMvcRequestBuilders
        		.put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(medicalDTO)))
                .andExpect(status()
                		.isBadRequest());

    }
    
    @Test
    @DisplayName("Check (incomplete id input lastname)"
    		+ " - Given an incomplete ID lastname,"
    		+ " when update request,"
    		+ " then return - Status: 400 Bad Request")
    public void testUpdateMedicalRecordRequestWithIncompleteIdWithoutLastNameName() throws Exception {
        medicalDTO = MedicalRecordDTO.builder()
        		.firstName("Test First Name")
        		.lastName("")
        		.birthDate("01/01/1960")
        		.medications(Arrays.asList("First symptom",
        				"Second medication",
        				"Third medication",
        				"terazine:500mg"))
                .allergies(Arrays.asList("First allergy",
                		"Second allergy",
                		"Third allergy"))
                .build();

        mockMvc.perform(MockMvcRequestBuilders
        		.put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(medicalDTO)))
                .andExpect(status()
                		.isBadRequest());

    }
    
    @Test
    @DisplayName("Check without any inputs)"
    		+ " - Given an incomplete IDs,"
    		+ " when update request,"
    		+ " then return - Status: 400 Bad Request")
    public void testUpdateMedicalRecordRequestWithIncompleteIdWithoutAnyNameInputs() throws Exception {
        medicalDTO = MedicalRecordDTO.builder()
        		.firstName("")
        		.lastName("")
        		.birthDate("01/01/1960")
        		.medications(Arrays.asList("First symptom",
        				"Second medication",
        				"Third medication",
        				"terazine:500mg"))
                .allergies(Arrays.asList("First allergy",
                		"Second allergy",
                		"Third allergy"))
                .build();

        mockMvc.perform(MockMvcRequestBuilders
        		.put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(medicalDTO)))
                .andExpect(status()
                		.isBadRequest());

    }
    
    @Test
    @DisplayName("Check (No request body input)"
    		+ " - Given no request body content,"
    		+ " when PUT request,"
    		+ " then return - Status: 400 Bad Request")
    public void testUpdateMedicalRecordRequestWithNoRequestBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString("")))
                .andExpect(status()
                		.isBadRequest());

    }
}
