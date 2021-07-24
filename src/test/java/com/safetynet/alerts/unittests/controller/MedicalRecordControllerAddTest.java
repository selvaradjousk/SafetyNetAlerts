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

@DisplayName("MEDICAL RECORD ADD Endpoint Controller - Unit Tests")
@ExtendWith(SpringExtension.class)
@WebMvcTest(MedicalRecordController.class)
public class MedicalRecordControllerAddTest {

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
    @DisplayName("Check (for valid input ids)"
    		+ " - Given a Medical Record to add,"
    		+ " when POST request,"
    		+ " then return Status: 201 Created")
    public void testAddMedicalRecordRequestWithValidIdCheckCreationStatus() throws Exception {
        when(medicalRecordService
        		.addNewMedicalRecord(any(MedicalRecordDTO.class)))
        .thenReturn(any(MedicalRecordDTO.class));

        mockMvc.perform(MockMvcRequestBuilders
        		.post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(medicalDTO)))
                .andExpect(status()
                		.isCreated());

        verify(medicalRecordService)
        .addNewMedicalRecord(any(MedicalRecordDTO.class));
        verify(medicalRecordService, times(1))
        .addNewMedicalRecord(any(MedicalRecordDTO.class));
    }
    
    @Test
    @DisplayName("Check (no firstname input)"
    		+ " - Given without complete person Id - firstname,"
    		+ " when POST request,"
    		+ " then return - Status: 400 Bad Request")
    public void testAddMedicalRecordRequestWithInvalidIDNoFirstName() throws Exception {
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
        		.post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(medicalDTO)))
                .andExpect(status()
                		.isBadRequest());

        verify(medicalRecordService, times(0))
        .addNewMedicalRecord(any(MedicalRecordDTO.class));
    }
    
    
    @Test
    @DisplayName("Check (no lastname input)"
    		+ " - Given without complete person Id - firstname,"
    		+ " when POST request,"
    		+ " then return - Status: 400 Bad Request")
    public void testAddMedicalRecordRequestWithInvalidIDNoLastName() throws Exception {
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
        		.post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(medicalDTO)))
                .andExpect(status()
                		.isBadRequest());

        verify(medicalRecordService, times(0))
        .addNewMedicalRecord(any(MedicalRecordDTO.class));
    }
    
    @Test
    @DisplayName("Check (no valid input ids)"
    		+ " - Given without complete person Ids ,"
    		+ " when POST request,"
    		+ " then return - Status: 400 Bad Request")
    public void testAddMedicalRecordRequestWithInvalidIDInputs() throws Exception {
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
        		.post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(displayAsJsonString(medicalDTO)))
                .andExpect(status()
                		.isBadRequest());

        verify(medicalRecordService, times(0))
        .addNewMedicalRecord(any(MedicalRecordDTO.class));
    }
    
    

    @Test
    @DisplayName("Check (Empty body input)"
    		+ " - Given an empty body request,"
    		+ " when POST request,"
    		+ " then return - Status: 400 Bad Request")
    public void testAddMedicalRecordRequestWithoutRequestBody() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status()
                		.isBadRequest());

    }

}
