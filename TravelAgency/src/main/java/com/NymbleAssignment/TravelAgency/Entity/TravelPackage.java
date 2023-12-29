package com.NymbleAssignment.TravelAgency.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TravelPackage {

	private String name;
	private int passengerCapacity;
	private List<Destination> itinerary;
	private List<Passenger> passengers;
	
	public TravelPackage() {
		this.itinerary = new ArrayList<>();
		this.passengers = new ArrayList<>();
	}
	
	public TravelPackage(String name, int passengerCapacity) {
		this.name = name;
		this.passengerCapacity = passengerCapacity;
		this.itinerary = new ArrayList<>();
		this.passengers = new ArrayList<>();
	}

	public void printItinerary() {
		System.out.println("Package name: " + this.getName());
		System.out.println("Destinations - ");
		itinerary.forEach(d -> {
			System.out.println("Destination: " + d.getName());
			System.out.println("Available activities - ");
			List<Activity> activities = d.getActivities();
			for(Activity a : activities) {
				
				System.out.println("Activity: " + a.getName());
				System.out.println("Description: " + a.getDescription());
				System.out.println("Cost: " + a.getCost());
				System.out.println("Total Capacity: " + a.getMaxCapacity());
				System.out.println("Available Capacity: " + a.getAvailableCapacity());
			}
		});
	}
	
	public void printPassengerList() {
		System.out.println("Package name: " + this.getName());
		System.out.println("Passenger capacity: " + this.getPassengerCapacity());
		System.out.println("Number for passengers enrolled: " + this.getPassengers().size());
		System.out.println("Passengers - ");
		this.getPassengers().forEach(p -> {
			System.out.println("Name: " + p.getName());
			System.out.println("Number: " + p.getPassengerNumber());
		});
	}
	
	public void printAvailableActivities() {
		itinerary.forEach(d -> {
			System.out.println("Destination: " + d.getName());
			System.out.println("Available activities - ");
			List<Activity> activities = d.getActivities().stream()
														.filter(a -> a.getAvailableCapacity() > 0)
														.collect(Collectors.toList());
			for(Activity a : activities) {
				
				System.out.println("Activity: " + a.getName());
				System.out.println("Description: " + a.getDescription());
				System.out.println("Cost: " + a.getCost());
				System.out.println("Total Capacity: " + a.getMaxCapacity());
				System.out.println("Available Capacity: " + a.getAvailableCapacity());
			}
		});
	}
	
	public boolean addPassenger(Passenger passenger) {
		boolean wasAddedSuccessfuly = false;
		int availablePassengerCapacity = this.getPassengerCapacity() - this.getPassengers().size();
		if(availablePassengerCapacity > 0) {
			this.getPassengers().add(passenger);
			wasAddedSuccessfuly = true;
		}
		
		return wasAddedSuccessfuly;
	}
	
	public boolean signPassengerUpForActivity(Activity activity, Passenger passenger) {
		boolean wasSignupSuccessful = false;
		int availableCapacity = activity.getAvailableCapacity();
		
		// Create a new list if it hasn't been created yet 
		if(passenger.getActivitiesSignedUp() == null) {
			passenger.setActivitiesSignedUp( new ArrayList<>());
		}
		
		double discountedPriceForActivity = passenger.getDiscountedPriceForActivity(activity.getCost());
		boolean hasRequiredBalance = passenger.getBalance() >= discountedPriceForActivity;
		boolean alreadySignedUp = passenger.getActivitiesSignedUp().contains(activity);
		boolean purchasedTravelPackage = this.getPassengers().contains(passenger);
		
		if(availableCapacity > 0 && hasRequiredBalance && !alreadySignedUp && purchasedTravelPackage) {
			// reduce the available capacity of the activity
			activity.setAvailableCapacity(availableCapacity - 1);
			
			// reduce passenger's balance
			passenger.setBalance(passenger.getBalance() - discountedPriceForActivity);
			
			// add the activity to passenger's activity list
			passenger.getActivitiesSignedUp().add(activity);
			
			wasSignupSuccessful = true;
		}
		
		return wasSignupSuccessful;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPassengerCapacity() {
		return passengerCapacity;
	}
	public void setPassengerCapacity(int passengerCapacity) {
		this.passengerCapacity = passengerCapacity;
	}
	public List<Destination> getItinerary() {
		return itinerary;
	}
	public void setItinerary(List<Destination> itinerary) {
		this.itinerary = itinerary;
	}
	public List<Passenger> getPassengers() {
		return passengers;
	}
}
