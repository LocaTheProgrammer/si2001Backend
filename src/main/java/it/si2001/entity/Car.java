package it.si2001.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "car")
public class Car {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "milesPerGallon")
	private String milesPerGallon;
	
	@Column(name = "cylinders")
	private String cylinders;
	
	@Column(name = "displacement")
	private String displacement;
	
	@Column(name = "horsePower")
	private String horsePower;

	@Column(name = "weightInLbs")
	private String weightInLbs;



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
