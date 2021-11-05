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
@Data
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

}
