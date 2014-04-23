package com.tutorial.spring4;

import java.util.Map;

public class CarStore2 {

	private String name;
	private Map<String,Car> cars;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Map<String, Car> getCars() {
		return cars;
	}
	public void setCars(Map<String, Car> cars) {
		this.cars = cars;
	}
	@Override
	public String toString() {
		return "CarStore [cars=" + cars + ", name=" + name + "]";
	}
	
}
