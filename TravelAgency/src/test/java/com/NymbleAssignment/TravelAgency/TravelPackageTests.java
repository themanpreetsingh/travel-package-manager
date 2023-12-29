package com.NymbleAssignment.TravelAgency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.NymbleAssignment.TravelAgency.Entity.Activity;
import com.NymbleAssignment.TravelAgency.Entity.Destination;
import com.NymbleAssignment.TravelAgency.Entity.Passenger;
import com.NymbleAssignment.TravelAgency.Entity.PassengerType;
import com.NymbleAssignment.TravelAgency.Entity.TravelPackage;

public class TravelPackageTests {
	
	private TravelPackage travelPackage;
	private static final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
	
    @BeforeEach
	void setUpTest() {
    	System.setOut(new PrintStream(outputStreamCaptor));
    	
		travelPackage = new TravelPackage("testPackage", 10);
		
		Passenger passenger = new Passenger();
		passenger.setName("passenger1");
		passenger.setPassengerNumber(1);
		passenger.setPassengerType(PassengerType.STANDARD);
		passenger.setBalance(20.0);
		travelPackage.addPassenger(passenger);
		
		Destination destination1 = new Destination("destination1");
		
		travelPackage.getItinerary().add(destination1);
		
		Activity activity1 = new Activity();
		
		activity1.setName("activity1");
		activity1.setDescription("activity1");
		activity1.setCost(10);
		activity1.setMaxCapacity(4);
		activity1.setAvailableCapacity(4);
		activity1.setDestination(destination1);
		
		destination1.getActivities().add(activity1);
	}
    
    @AfterAll
    static void cleanUp() {
    	System.setOut(originalOut);
    }

	@Test
	void testPrintItinerary() {
		travelPackage.printItinerary();
		
		String outputString = outputStreamCaptor.toString();
		
		assertTrue(outputString.contains("Package name: testPackage"));
		assertTrue(outputString.contains("Destinations - "));
		assertTrue(outputString.contains("Destination: destination1"));
		assertTrue(outputString.contains("Available activities - "));
		assertTrue(outputString.contains("Activity: activity1"));
		assertTrue(outputString.contains("Description: activity1"));
		assertTrue(outputString.contains("Cost: 10.0"));
		assertTrue(outputString.contains("Total Capacity: 4"));
		assertTrue(outputString.contains("Available Capacity: 4"));
	}
	
	@Test
	void testPrintPassengerList() {
		travelPackage.printPassengerList();
		
		String outputString = outputStreamCaptor.toString();
		
		assertTrue(outputString.contains("Package name: testPackage"));
		assertTrue(outputString.contains("Passenger capacity: 10"));
		assertTrue(outputString.contains("Number for passengers enrolled: 1"));
		assertTrue(outputString.contains("Passengers - "));
		assertTrue(outputString.contains("Name: passenger1"));
		assertTrue(outputString.contains("Number: 1"));
	}
	
	@Test
	void testPrintAvailableActivities() {
		Passenger passenger = travelPackage.getPassengers().get(0);
		Activity activity = travelPackage.getItinerary().get(0).getActivities().get(0);
		
		travelPackage.signPassengerUpForActivity(activity, passenger);
		
		travelPackage.printAvailableActivities();
		
		String outputString = outputStreamCaptor.toString();
		
		assertTrue(outputString.contains("Destination: destination1"));
		assertTrue(outputString.contains("Available activities - "));
		assertTrue(outputString.contains("Activity: activity1"));
		assertTrue(outputString.contains("Description: activity1"));
		assertTrue(outputString.contains("Cost: 10.0"));
		assertTrue(outputString.contains("Total Capacity: 4"));
		assertTrue(outputString.contains("Available Capacity: 3"));
	}
	
	@Test
	void testAddPassenger() {
		
		// Scenario where max capacity has been reached
		travelPackage.setPassengerCapacity(1);
		Passenger passenger = new Passenger();
		boolean testOuput = travelPackage.addPassenger(passenger);
		assertFalse(testOuput);
		assertEquals(1, travelPackage.getPassengers().size());
		
		// Positive scenario
		travelPackage.setPassengerCapacity(2);
		boolean testOuput1 = travelPackage.addPassenger(passenger);
		assertTrue(testOuput1);
		assertEquals(2, travelPackage.getPassengers().size());
	}
	
	@Test
	void testSignPassengerUpForActivity() {
		// Standard passenger
		Passenger passenger = travelPackage.getPassengers().get(0);
		Activity activity = travelPackage.getItinerary().get(0).getActivities().get(0);
		
		double expectedBalance = passenger.getBalance() - activity.getCost();
		boolean output = travelPackage.signPassengerUpForActivity(activity, passenger);
		double actualBalance = passenger.getBalance();
		
		
		assertTrue(output);
		assertEquals(expectedBalance, actualBalance);
		assertEquals(1, passenger.getActivitiesSignedUp().size());
		
		// Gold passenger
		Passenger passenger2 = new Passenger();
    	passenger2.setName("passenger2");
    	passenger2.setPassengerNumber(2);
    	passenger2.setPassengerType(PassengerType.GOLD);
    	passenger2.setBalance(20.0);
    	travelPackage.addPassenger(passenger2);
    	
    	expectedBalance = 11.0;
    	output = travelPackage.signPassengerUpForActivity(activity, passenger2);
    	actualBalance = passenger2.getBalance();
    	
    	assertTrue(output);
		assertEquals(expectedBalance, actualBalance);
		assertEquals(1, passenger2.getActivitiesSignedUp().size());
		
		// Insufficient balance
		Passenger passenger3 = new Passenger();
    	passenger3.setName("passenger3");
    	passenger3.setPassengerNumber(3);
    	passenger3.setPassengerType(PassengerType.STANDARD);
    	passenger3.setBalance(1.0);
    	travelPackage.addPassenger(passenger3);
    	
    	expectedBalance = passenger3.getBalance();
    	output = travelPackage.signPassengerUpForActivity(activity, passenger3);
    	actualBalance = passenger3.getBalance();
    	
    	assertFalse(output);
		assertEquals(expectedBalance, actualBalance);
		assertEquals(0, passenger3.getActivitiesSignedUp().size());
		
	}
}
