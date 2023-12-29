package com.NymbleAssignment.TravelAgency;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

public class PassengerTests {

	private TravelPackage travelPackage;
	private Passenger passenger;
	private static final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    
    @BeforeEach
	void setUpTest() {
    	System.setOut(new PrintStream(outputStreamCaptor));
    	
    	travelPackage = new TravelPackage("testPackage", 10);
    	
    	passenger = new Passenger();
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
    void testPrintPassengerDetails() {
    	
    	// Testing standard passenger
    	Activity activity = travelPackage.getItinerary().get(0).getActivities().get(0);
    	travelPackage.signPassengerUpForActivity(activity, passenger);
    	passenger.printPassengerDetails();
    	
    	String outputString = outputStreamCaptor.toString();
		
		assertTrue(outputString.contains("Name: passenger1"));
		assertTrue(outputString.contains("Number: 1"));
		assertTrue(outputString.contains("Balance: 10.0"));
		assertTrue(outputString.contains("Signed up activities - "));
		assertTrue(outputString.contains("Activity name: activity1"));
		assertTrue(outputString.contains("Destination: destination1"));
		assertTrue(outputString.contains("Price paid: 10.0"));
    	
    	// Testing GOLD passenger
    	Passenger passenger2 = new Passenger();
    	passenger2.setName("passenger2");
    	passenger2.setPassengerNumber(2);
    	passenger2.setPassengerType(PassengerType.GOLD);
    	passenger2.setBalance(20.0);
    	travelPackage.addPassenger(passenger2);
    	travelPackage.signPassengerUpForActivity(activity, passenger2);
    	
    	passenger2.printPassengerDetails();

    	outputString = outputStreamCaptor.toString();
		
		assertTrue(outputString.contains("Name: passenger2"));
		assertTrue(outputString.contains("Number: 2"));
		assertTrue(outputString.contains("Balance: 11.0"));
		assertTrue(outputString.contains("Signed up activities - "));
		assertTrue(outputString.contains("Activity name: activity1"));
		assertTrue(outputString.contains("Destination: destination1"));
		assertTrue(outputString.contains("Price paid: 9.0"));
    }
}
