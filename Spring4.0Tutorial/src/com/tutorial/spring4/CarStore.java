package com.tutorial.spring4;

import java.util.List;

public class CarStore {

	private String name;
	private List<Car> cars;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Car> getCars() {
		return cars;
	}
	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
	
	@Override
	public String toString() {
		return "CarStore [cars=" + cars + ", name=" + name + "]";
	}
	
}
