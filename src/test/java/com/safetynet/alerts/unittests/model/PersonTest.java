package com.safetynet.alerts.unittests.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.safetynet.alerts.model.Person;

/**
 * @author Senthil
 *
 */
class PersonTest {
	
	Person toTest1 = new Person("firstname", "lastname", "address", "city", 123456789, "12345678", "email");
	Person toTest2 = new Person("firstname", "lastname", "address", "city", 123456789, "12345678", "email");

	@BeforeEach
	public void init() {
	}

	/**
	 * Test method for {@link com.safetynet.Alerts.model.mainCRUDendpoint.mainCRUDendpoint.Person#hashCode()}.
	 */
	@Test
	void testHashCode() {
		assertEquals((toTest1.toString()).hashCode(), (toTest2.toString()).hashCode());
	}

	/**
	 * Test method for
	 * {@link com.safetynet.Alerts.model.mainCRUDendpoint.mainCRUDendpoint.Person#equals(java.lang.Object)}.
	 */
	@Test
	final void testEqualsObject() {
		assertEquals(toTest1.toString(), toTest2.toString());
	}

	/**
	 * Test method for
	 * {@link com.safetynet.Alerts.model.mainCRUDendpoint.mainCRUDendpoint.Person#canEqual(java.lang.Object)}.
	 */
	@Test
	final void testCanEqual() {
		assertEquals(toTest1.toString(), toTest2.toString());
	}

	/**
	 * Test method for
	 * {@link com.safetynet.Alerts.model.mainCRUDendpoint.mainCRUDendpoint.Person#Person(String, String, String, String, int, String, String)}.
	 */
	@Test
	final void testPersonStringStringStringStringIntStringString() {
		assertEquals(toTest1.toString(), toTest2.toString());
	}

	/**
	 * Test method for {@link com.safetynet.Alerts.model.mainCRUDendpoint.mainCRUDendpoint.Person#Person()}.
	 */
	@Test
	final void testPerson() {
		assertEquals(toTest1.getFirstName(), toTest2.getFirstName());
	}

	/**
	 * Test method for {@link com.safetynet.Alerts.model.mainCRUDendpoint.mainCRUDendpoint.Person#getFirstName()}.
	 */
	@Test
	final void testGetFirstName() {
		assertNotNull(toTest1.getFirstName());
	}

	/**
	 * Test method for {@link com.safetynet.Alerts.model.mainCRUDendpoint.mainCRUDendpoint.Person#getLastName()}.
	 */
	@Test
	final void testGetLastName() {
		assertNotNull(toTest1.getLastName()); 
	}

	/**
	 * Test method for {@link com.safetynet.Alerts.model.mainCRUDendpoint.mainCRUDendpoint.Person#getAddress()}.
	 */
	@Test
	final void testGetAddress() {
		assertNotNull(toTest1.getAddress()); 
	}

	/**
	 * Test method for {@link com.safetynet.Alerts.model.mainCRUDendpoint.mainCRUDendpoint.Person#getCity()}.
	 */
	@Test
	final void testGetCity() {
		assertNotNull(toTest1.getCity()); 
	}

	/**
	 * Test method for {@link com.safetynet.Alerts.model.mainCRUDendpoint.mainCRUDendpoint.Person#getZip()}.
	 */
	@Test
	final void testGetZip() {
		assertNotNull(toTest1.getZip()); // TODO
	}

	/**
	 * Test method for {@link com.safetynet.Alerts.model.mainCRUDendpoint.urlReadEndpoint.Person#getPhone()}.
	 */
	@Test
	final void testGetPhone() {
		assertNotNull(toTest1.getPhone()); // TODO
	}

	/**
	 * Test method for {@link com.safetynet.Alerts.model.mainCRUDendpoint.mainCRUDendpoint.Person#getEmail()}.
	 */
	@Test
	final void testGetEmail() {
		assertNotNull(toTest1.getEmail()); // TODO
	}

	/**
	 * Test method for
	 * {@link com.safetynet.Alerts.model.mainCRUDendpoint.mainCRUDendpoint.Person#setFirstName(String)}.
	 */
	@Test
	final void testSetFirstName() {
		Person person = new Person();
		person.setFirstName("firstname"); // TODO
		assertEquals(person.getFirstName(), toTest1.getFirstName());
	}

	/**
	 * Test method for
	 * {@link com.safetynet.Alerts.model.mainCRUDendpoint.mainCRUDendpoint.Person#setLastName(String)}.
	 */
	@Test
	final void testSetLastName() {
		Person person = new Person();
		person.setLastName("lastname"); // TODO
		assertEquals(person.getLastName(), toTest1.getLastName());
	}

	/**
	 * Test method for {@link com.safetynet.Alerts.model.mainCRUDendpoint.mainCRUDendpoint.Person#setAddress(String)}.
	 */
	@Test
	final void testSetAddress() {
		Person person = new Person();
		person.setAddress("address");
		assertEquals(person.getAddress(), toTest1.getAddress());
	}

	/**
	 * Test method for {@link com.safetynet.Alerts.model.mainCRUDendpoint.mainCRUDendpoint.Person#setCity(String)}.
	 */
	@Test
	final void testSetCity() {
		Person person = new Person();
		person.setCity("city");
		assertEquals(person.getCity(), toTest1.getCity());
	}

	/**
	 * Test method for {@link com.safetynet.Alerts.model.mainCRUDendpoint.mainCRUDendpoint.Person#setZip(int)}.
	 */
	@Test
	final void testSetZip() {
		Person person = new Person();
		person.setZip(123456789);
		assertEquals(person.getZip(), toTest1.getZip());
	}

	/**
	 * Test method for {@link com.safetynet.Alerts.model.mainCRUDendpoint.mainCRUDendpoint.Person#setPhone(String)}.
	 */
	@Test
	final void testSetPhone() {
		Person person = new Person();
		person.setPhone("12345678");
		assertEquals(person.getPhone(), toTest1.getPhone());
	}

	/**
	 * Test method for {@link com.safetynet.Alerts.model.mainCRUDendpoint.mainCRUDendpoint.Person#setEmail(String)}.
	 */
	@Test
	final void testSetEmail() {
		Person person = new Person();
		person.setEmail("email");
		assertEquals(person.getEmail(), toTest1.getEmail());
	}

	/**
	 * Test method for {@link com.safetynet.Alerts.model.mainCRUDendpoint.mainCRUDendpoint.Person#toString()}.
	 */
	@Test
	final void testToString() {
		Person toTest1 = new Person("firstname", "lastname", "address", "city", 123456789, "12345678", "email");
		Person toTest2 = new Person("firstname", "lastname", "address", "city", 123456789, "12345678", "email");
		assertEquals(toTest1.toString(), (toTest2).toString());
	}

}

