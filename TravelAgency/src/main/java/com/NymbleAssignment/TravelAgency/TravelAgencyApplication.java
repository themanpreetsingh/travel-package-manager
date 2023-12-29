package com.NymbleAssignment.TravelAgency;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.NymbleAssignment.TravelAgency.Entity.Activity;
import com.NymbleAssignment.TravelAgency.Entity.Destination;
import com.NymbleAssignment.TravelAgency.Entity.Passenger;
import com.NymbleAssignment.TravelAgency.Entity.PassengerType;
import com.NymbleAssignment.TravelAgency.Entity.TravelPackage;

@SpringBootApplication
public class TravelAgencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelAgencyApplication.class, args);
		
		TravelPackage tPackage = getMockTravelpackage();
		tPackage.printItinerary();
		tPackage.printPassengerList();
	}
	
	private static TravelPackage getMockTravelpackage() {
		TravelPackage testPackage = new TravelPackage();
		testPackage.setName("Test-Travel-Package");
		testPackage.setPassengerCapacity(10);
		
		// Creating passengers
		for(int i = 0; i < 10; ++i) {
			Passenger passenger = new Passenger();
			passenger.setName("passenger" + i);
			passenger.setPassengerNumber(i);
			
			if(i % 2 == 0) {
				passenger.setPassengerType(PassengerType.STANDARD);
				passenger.setBalance(i * 5);
			} else if(i % 3 == 0) {
				passenger.setPassengerType(PassengerType.GOLD);
				passenger.setBalance(i * 2);
			} else {
				passenger.setPassengerType(PassengerType.PREMIUM);
			}
			
			testPackage.addPassenger(passenger);
		}
		
		// Creating destinations
		List<Destination> itinerary = new ArrayList<>();
		for(int i = 0; i < 10; ++i) {
			Destination destination = new Destination("destination" + i);
			List<Activity> activities = new ArrayList<>();
			
			// Creating activities
			for(int j = 0; j < 10; ++j) {
				Activity activity = new Activity();
				activity.setName("activity" + i + j);
				activity.setCost((i+1) + (j+1));
				activity.setDescription("activity description" + i + j);
				activity.setMaxCapacity(j+1);
				activity.setAvailableCapacity(j+1);
				activity.setDestination(destination);
				
				activities.add(activity);
			}
			
			destination.setActivities(activities);
			itinerary.add(destination);
		}
		
		testPackage.setItinerary(itinerary);
		
		return testPackage;
	}

}
