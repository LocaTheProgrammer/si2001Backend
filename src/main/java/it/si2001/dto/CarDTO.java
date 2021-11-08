package it.si2001.dto;

import org.springframework.beans.BeanUtils;

import it.si2001.entity.Car;

public class CarDTO {

	private int id;

	private String name;
	private String milesPerGallon;
	private String cylinders;
	private String displacement;
	private String horsePower;
	private String weightInLbs;

	
	public static CarDTO build(Car c) {

		CarDTO result = new CarDTO();
		BeanUtils.copyProperties(c, result);

		return result;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMilesPerGallon() {
		return milesPerGallon;
	}

	public void setMilesPerGallon(String milesPerGallon) {
		this.milesPerGallon = milesPerGallon;
	}

	public String getCylinders() {
		return cylinders;
	}

	public void setCylinders(String cylinders) {
		this.cylinders = cylinders;
	}

	public String getDisplacement() {
		return displacement;
	}

	public void setDisplacement(String displacement) {
		this.displacement = displacement;
	}

	public String getHorsePower() {
		return horsePower;
	}

	public void setHorsePower(String horsePower) {
		this.horsePower = horsePower;
	}

	public String getWeightInLbs() {
		return weightInLbs;
	}

	public void setWeightInLbs(String weightInLbs) {
		this.weightInLbs = weightInLbs;
	}


}
