package com.NymbleAssignment.TravelAgency.Entity;

public class Activity {

	private String name;
	private String description;
	private double cost;
	private int maxCapacity;
	private int availableCapacity;
	private Destination destination;

	public Activity() {}

	public Activity(String name, String description, double cost, int maxCapacity, int availableCapacity, Destination destination) {
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.maxCapacity = maxCapacity;
		this.availableCapacity = availableCapacity;
		this.destination = destination;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public int getMaxCapacity() {
		return maxCapacity;
	}
	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	public int getAvailableCapacity() {
		return availableCapacity;
	}
	public void setAvailableCapacity(int availableCapacity) {
		this.availableCapacity = availableCapacity;
	}
	public Destination getDestination() {
		return destination;
	}
	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	
	
}
