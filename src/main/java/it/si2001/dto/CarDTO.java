package it.si2001.dto;

import org.springframework.beans.BeanUtils;

import it.si2001.entity.Car;
import lombok.Data;

@Data
public class CarDTO {
	
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
}
