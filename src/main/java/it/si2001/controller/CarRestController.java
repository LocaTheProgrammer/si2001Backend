package it.si2001.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static Logger log = LoggerFactory.getLogger(CarRestController.class);

	@PostMapping(path="/create")
	public Response<CarDTO> createCar(@RequestBody CarDTO car){
		log.info(car.toString());
		return this.carService.createCar(car);
	}

	@GetMapping(path="/findAll")
	public Response<List<CarDTO>> createCar(){
		log.info("received find all request");
		return this.carService.findAllCars();}

	@GetMapping(path = "/findCarById/{id}")
	public Response<CarDTO> findCarById(@PathVariable int id){
		return this.carService.findCarById(id);
	}

	@PutMapping(path="/updateCar/{id}")
	public Response<CarDTO> updateCar(@RequestBody CarDTO carDTO, @PathVariable int id){
		carDTO.setId(id);
		return this.carService.updateCar(carDTO);
	}

	@DeleteMapping(path="/deleteCarById/{id}")
	public boolean deleteCar(@PathVariable int id){
		return this.carService.deleteCarById(id);
	}

}
