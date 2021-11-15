package it.si2001.controller;

import it.si2001.dto.NgbDateDTO;
import it.si2001.dto.NgbDateRangeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import it.si2001.dto.CarDTO;
import it.si2001.dto.Response;
import it.si2001.service.CarService;

import java.text.ParseException;
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
	public Response<CarDTO> createCar(@RequestBody CarDTO car) throws ParseException {
		log.info(car.toString());
		return this.carService.createCar(car);
	}

	@GetMapping(path="/findAll")
	public Response<List<CarDTO>> createCar(){
		log.info("received find all request");
		Response<List<CarDTO>> response =new Response<>();
		response.setResult(this.carService.findAllCars());
		return response;}

	@GetMapping(path = "/findCarById/{id}")
	public Response<CarDTO> findCarById(@PathVariable int id){
		return this.carService.findCarById(id);
	}

	@PutMapping(path="/updateCar")
	public Response<CarDTO> updateCar(@RequestBody CarDTO carDTO) throws ParseException {
		log.info("car dto: "+carDTO);
		return this.carService.createCar(carDTO);
	}

	@DeleteMapping(path="/deleteCarById/{id}")
	public Response<Boolean> deleteCar(@PathVariable int id){
		return this.carService.deleteCarById(id);
	}

	@PostMapping(path="/getFreeCarByReservationDate")
	public Response<List<CarDTO>> getFreeCarByReservationDate(@RequestBody NgbDateRangeDTO ngbDateRange) throws ParseException {
		log.info(ngbDateRange.toString());
		Response<List<CarDTO>> response = new Response<>();
		response.setResult(this.carService.getFreeCarByReservationDate(ngbDateRange.getStart(), ngbDateRange.getEnd()));
		return response;
	}

}
