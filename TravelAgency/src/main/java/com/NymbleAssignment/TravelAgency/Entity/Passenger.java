package com.NymbleAssignment.TravelAgency.Entity;

import java.util.ArrayList;
import java.util.List;

public class Passenger {

	private String name;
	private int passengerNumber;
	private PassengerType passengerType;
	private double balance;
	private List<Activity> activitiesSignedUp;
	
	public void printPassengerDetails() {
		System.out.println("Name: " + this.getName());
		System.out.println("Number: " + this.getPassengerNumber());
		if(!this.getPassengerType().equals(PassengerType.PREMIUM)) {
			System.out.println("Balance: " + this.getBalance());
		}
		System.out.println("Signed up activities - ");
		this.getActivitiesSignedUp().forEach(activity -> {
			System.out.println("Activity name: " + activity.getName());
			System.out.println("Destination: " + activity.getDestination().getName());
			System.out.println("Price paid: " + this.getDiscountedPriceForActivity(activity.getCost()));
		});
	}
	
	public double getDiscountedPriceForActivity(double activityCost) {
		double discount = 0;
		
		switch(this.getPassengerType()) {
			case STANDARD:
				discount = 0;
				break;
			
			case GOLD:
				discount = 0.1;
				break;
				
			case PREMIUM:
				discount = 1;
				break;
		}
		
		double discountedPrice = activityCost - (discount * activityCost);
		
		return discountedPrice;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPassengerNumber() {
		return passengerNumber;
	}
	public void setPassengerNumber(int passengerNumber) {
		this.passengerNumber = passengerNumber;
	}
	public PassengerType getPassengerType() {
		return passengerType;
	}
	public void setPassengerType(PassengerType passengerType) {
		this.passengerType = passengerType;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public List<Activity> getActivitiesSignedUp() {
		return activitiesSignedUp;
	}
	public void setActivitiesSignedUp(List<Activity> activitiesSignedUp) {
		this.activitiesSignedUp = activitiesSignedUp;
	}
}
