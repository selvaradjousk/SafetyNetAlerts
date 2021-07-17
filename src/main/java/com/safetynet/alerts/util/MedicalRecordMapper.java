package com.safetynet.alerts.util;

import org.springframework.stereotype.Component;

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecord;

@Component
public class MedicalRecordMapper {

    public MedicalRecordDTO toMedicalRecordDTO(final MedicalRecord med) {

        return new MedicalRecordDTO(med.getFirstName(),
        		med.getLastName(),
        		med.getBirthDate(),
                med.getMedications(),
                med.getAllergies());
    }
    
    public MedicalRecord toMedicalRecord(final MedicalRecordDTO medDTO) {

        return new MedicalRecord(medDTO.getFirstName(),
        		medDTO.getLastName(),
        		medDTO.getBirthDate(),
                medDTO.getMedications(),
                medDTO.getAllergies());
    }
	
}
