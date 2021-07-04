package com.safetynet.alerts.unittests.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.safetynet.alerts.model.MedicalRecord;

class MedicalRecordTest {

	MedicalRecord toTest1 = new MedicalRecord("John", "Boyd", "03/06/1984",
			Arrays.asList("aznol:350mg", "hydrapermazol:100mg"), Arrays.asList("nillacilan"));
	MedicalRecord toTest2 = new MedicalRecord("John", "Boyd", "03/06/1984",
			Arrays.asList("aznol:350mg", "hydrapermazol:100mg"), Arrays.asList("nillacilan"));

	@Test
	final void testMedicalRecordStringStringStringListOfStringListOfString() {
		assertEquals(toTest1.toString(), toTest2.toString());
	}

	@Test
	final void testMedicalRecord() {
		assertEquals(toTest1.getFirstName(), toTest2.getFirstName());
	}

	@Test
	final void testGetFirstName() {
		assertNotNull(toTest1.getFirstName());
	}

	@Test
	final void testGetLastName() {
		assertNotNull(toTest1.getLastName()); 
	}

	@Test
	final void testGetBirthDate() {
		assertNotNull(toTest1.getBirthDate()); 
	}

	@Test
	final void testGetMedications() {
		assertNotNull(toTest1.getMedications()); 
	}

	@Test
	final void testGetAllergies() {
		assertNotNull(toTest1.getAllergies()); 
	}

	@Test
	final void testSetFirstName() {
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setFirstName("John");
		assertEquals(medicalRecord.getFirstName(), toTest1.getFirstName());
	}

	@Test
	final void testSetLastName() {
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setLastName("Boyd");
		assertEquals(medicalRecord.getLastName(), toTest1.getLastName());
	}

	@Test
	final void testSetBirthDate() {
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setBirthDate("03/06/1984");
		assertEquals(medicalRecord.getBirthDate(), toTest1.getBirthDate());
	}

	@Test
	final void testSetMedications() {
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setMedications(Arrays.asList("aznol:350mg", "hydrapermazol:100mg"));
		assertEquals(medicalRecord.getMedications(), toTest1.getMedications());
	}

	@Test
	final void testSetAllergies() {
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setAllergies(Arrays.asList("nillacilan"));
		assertEquals(medicalRecord.getAllergies(), toTest1.getAllergies());
	}

	@Test
	final void testToString() {
		assertEquals(toTest1.toString(), (toTest2).toString());
	}

}
