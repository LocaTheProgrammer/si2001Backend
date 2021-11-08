package it.si2001.controller;

import it.si2001.entity.Car;
import org.springframework.web.bind.annotation.*;

import it.si2001.dto.CarDTO;
import it.si2001.dto.Response;
import it.si2001.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/rest/car")
public class CarRestController {
	
	private CarService carService;
	
	public CarRestController (CarService carService) {
		this.carService=carService;
	}
	
	@PostMapping(path="/create")
	public Response<CarDTO> createCar(@RequestBody CarDTO car){
		return this.carService.createCar(car);
	}

	@GetMapping(path="/findAll")
	public Response<List<CarDTO>> createCar(){return this.carService.findAllCars();}

	@GetMapping(path = "/findCarById/{id}")
	public Response<CarDTO> findCarById(@PathVariable int id){
		return this.carService.findCarById(id);
	}

	@PutMapping(path="/updateCar")
	public Response<CarDTO> updateCar(@RequestBody CarDTO carDTO){
		return this.carService.updateCar(carDTO);
	}

	@DeleteMapping(path="/deleteCar/{id}")
	public boolean deleteCar(@PathVariable int id){
		return this.carService.deleteCarById(id);
	}

}
