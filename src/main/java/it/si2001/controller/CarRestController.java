package it.si2001.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.si2001.dto.CarDTO;
import it.si2001.dto.Response;
import it.si2001.service.CarService;

@RestController
@RequestMapping("/rest/car")
public class CarRestController {
	
	private CarService carService;
	
	public CarRestController (CarService carService) {
		this.carService=carService;
	}
	
	@PostMapping(path="/create")
	public Response<?> createCar(@RequestBody CarDTO car){
		return this.carService.createCar(car);
	}

}
