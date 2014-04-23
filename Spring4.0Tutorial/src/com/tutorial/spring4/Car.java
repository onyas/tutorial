package com.tutorial.spring4;

public class Car {

	private String brand;
	private String corp;
	private double price;
	private int speed;
	
	public Car(String brand, String corp, double price) {
		super();
		this.brand = brand;
		this.corp = corp;
		this.price = price;
	}

	public Car(String brand, String corp, int speed) {
		super();
		this.brand = brand;
		this.corp = corp;
		this.speed = speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	@Override
	public String toString() {
		return "Car [brand=" + brand + ", corp=" + corp + ", price=" + price
				+ ", speed=" + speed + "]";
	}
	
	
	
	
}
