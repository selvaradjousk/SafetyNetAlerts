package com.safetynet.alerts.unittests.controller;

import static com.safetynet.alerts.testingtoolsconfig.DataPreparation.displayAsJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

@ExtendWith(SpringExtension.class)
@WebMvcTest(MedicalRecordController.class)
public class MedicalRecordControllerTest {

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
    @DisplayName("Test GET MEDICAL RECORD")
    @Nested
    class TestGetMedicalRecord { 
    @Test
    @DisplayName("Check (Valid person ids input)"
    		+ " - Given VALID PERSON-ID,"
    		+ " when GET request (/medicalRecord?firstName=Test FirstName&lastName=Test Last Name),"
    		+ " then return OK status (an HTTP 200 response)")
    public void testGetMedicalRecordRequestWithValidId() throws Exception {
        when(medicalRecordService
        		.getMedicalRecordById(anyString(), anyString()))
        .thenReturn(medicalDTO);

        mockMvc.perform(MockMvcRequestBuilders
        		.get("/medicalRecord?firstName=Test FirstName&lastName=Test Last Name"))
                .andExpect(status()
                		.isOk());

        verify(medicalRecordService)
        .getMedicalRecordById(anyString(), anyString());
        verify(medicalRecordService, times(1))
        .getMedicalRecordById(anyString(), anyString());
    }
    
    
    
    @Test
    @DisplayName("Check (input no firstname)"
    		+ " - Given INVALID PERSON-ID - without firstname,"
    		+ " when GET request (/medicalRecord?firstName=&lastName=Test LastName),"
    		+ " then return BadRequest status (an HTTP 400 response)")
    public void testGetMedicalRecordRequestWithIdWithoutFirstname() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.get("/medicalRecord?firstName=&lastName=LastName"))
                .andExpect(status()
                		.isBadRequest());

    }
    
    
    @Test
    @DisplayName("Check (input no lastname)"
    		+ " - Given INVALID PERSON-ID - without lastname,"
    		+ " when GET request (/medicalRecord?firstName=Test FirstName&lastName=),"
    		+ " then return BadRequest status (an HTTP 400 response)")
    public void testGetMedicalRecordRequestWithIdWithoutLastname() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.get("/medicalRecord?firstName=Test FirstName&lastName="))
                .andExpect(status()
                		.isBadRequest());

    }
    
    @Test
    @DisplayName("Check (without any input )"
    		+ " - Given INVALID PERSON-ID - without any input,"
    		+ " when GET request (/medicalRecord?firstName=&lastName=),"
    		+ " then return BadRequest status (an HTTP 400 response)")
    public void testGetMedicalRecordRequestWithIdWithoutAnyInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.get("/medicalRecord?firstName=&lastName="))
                .andExpect(status()
                		.isBadRequest());

    }
    
    @Test
    @DisplayName("Check (without both invalid input )"
    		+ " - Given INVALID ID - both invalid,"
    		+ " when GET request (/medicalRecord?firstName=&lastName=),"
    		+ " then return BadRequest status (an HTTP 400 response)")
    public void testGetMedicalRecordRequestWithIdBothInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.get("/medicalRecord?firstNameXXXXXXXXXX=&lastName=YYYYYYYYYY"))
                .andExpect(status()
                		.isBadRequest());

    }
    
    }

    // ***************************************************************************************************
    @DisplayName("Test ADD MEDICAL RECORD")
    @Nested
    class TestAddMedicalRecord { 
    
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
    
    // ***************************************************************************************************
    @DisplayName("Test UPDATE MEDICAL RECORD")
    @Nested
    class TestUpdateMedicalRecord { 

    @Test
    @DisplayName("Check (Valid MedicalRecord input)"
    		+ " - Given a valid MedicalRecord to update,"
    		+ " when PUT request,"
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
    
    }
    
    

}
