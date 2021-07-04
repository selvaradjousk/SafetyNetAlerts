/**
 * 
 */
package com.safetynet.alerts.unittests.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.safetynet.alerts.model.FireStation;

/**
 * @author Senthil
 *
 */
class FireStationTest {
	
	FireStation toTest1 = new FireStation("1509 Culver St", 3);
	FireStation toTest2 = new FireStation("1509 Culver St", 3);
	/**
	 * Test method for {@link com.safetynet.alerts.model.FireStation#FireStation(int, String)}.
	 */
	@Test
	final void testFireStationIntString() {
		assertEquals(toTest1.getAddress(), toTest2.getAddress());
	}

	/**
	 * Test method for {@link com.safetynet.alerts.model.FireStation#FireStation()}.
	 */
	@Test
	final void testFireStation() {
		assertEquals(toTest1.getAddress(), toTest2.getAddress());
	}

	/**
	 * Test method for {@link com.safetynet.alerts.model.FireStation#getStationId()}.
	 */
	@Test
	final void testGetStationId() {
		assertEquals(toTest1.getStationId(), toTest2.getStationId());
	}

	/**
	 * Test method for {@link com.safetynet.alerts.model.FireStation#getAddress()}.
	 */
	@Test
	final void testGetAddress() {
		assertEquals(toTest1.getAddress(), toTest2.getAddress());
	}

	/**
	 * Test method for {@link com.safetynet.alerts.model.FireStation#setStationId(int)}.
	 */
	@Test
	final void testSetStationId() {
		FireStation fireStation = new FireStation();
		fireStation.setStationId(3); 
		assertEquals(fireStation.getStationId(), toTest1.getStationId());
	}

	/**
	 * Test method for {@link com.safetynet.alerts.model.FireStation#setAddress(String)}.
	 */
	@Test
	final void testSetAddress() {
		FireStation fireStation = new FireStation();
		fireStation.setAddress("1509 Culver St"); 
		assertEquals(fireStation.getAddress(), toTest1.getAddress());
	}

	/**
	 * Test method for {@link com.safetynet.alerts.model.FireStation#toString()}.
	 */
	@Test
	final void testToString() {
		assertEquals(toTest1.toString(), (toTest2).toString());
	}

}
