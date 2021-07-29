package com.safetynet.alerts.util;

import org.springframework.stereotype.Component;

import com.safetynet.alerts.dto.MedicalRecordDTO;
import com.safetynet.alerts.model.MedicalRecord;

/**
 * Mapper for Medical Record Data resource.
 * @author Senthil
 *
 */
@Component
public class MedicalRecordMapper {

    /**
     * To medical record DTO.
     *
     * @param med the med
     * @return the medical record DTO
     */
    public MedicalRecordDTO toMedicalRecordDTO(
    		final MedicalRecord med) {

        return new MedicalRecordDTO(med.getFirstName(),
        		med.getLastName(),
        		med.getBirthDate(),
                med.getMedications(),
                med.getAllergies());
    }

    /**
     * To medical record.
     *
     * @param medDTO the med DTO
     * @return the medical record
     */
    public MedicalRecord toMedicalRecord(
    		final MedicalRecordDTO medDTO) {

        return new MedicalRecord(medDTO.getFirstName(),
        		medDTO.getLastName(),
        		medDTO.getBirthDate(),
                medDTO.getMedications(),
                medDTO.getAllergies());
    }
}
