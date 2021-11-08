package it.si2001.service;

import org.springframework.stereotype.Service;

import it.si2001.dao.CarRepository;
import it.si2001.dto.CarDTO;
import it.si2001.dto.Response;
import it.si2001.entity.Car;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CarService {

	private CarRepository carRepository;

	public CarService(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	public Response<CarDTO> createCar(CarDTO carDTO) {

		Response<CarDTO> res = new Response<CarDTO>();

		Car car = new Car();
		car.setCylinders(carDTO.getCylinders());
		car.setDisplacement(carDTO.getDisplacement());
		car.setHorsePower(carDTO.getHorsePower());
		car.setMilesPerGallon(carDTO.getMilesPerGallon());
		car.setName(carDTO.getName());
		car.setWeightInLbs(carDTO.getWeightInLbs());

		try {
			this.carRepository.save(car);
			res.setResult(carDTO);
			res.setResultTest(true);
		} catch (Exception e) {
			res.setError("car not saved");
		}

		return res;

	}

	public Response<CarDTO> findCarById(int id) {

		Response<CarDTO> res = new Response<CarDTO>();

		try {
			Car c = this.carRepository.findById(id).get();
			res.setResult(CarDTO.build(c));
			res.setResultTest(true);

		} catch (Exception e) {
			res.setError("no car found for id: " + id);
		}

		return res;
	}

	public boolean deleteCarById(int id) {

		try {
			this.carRepository.delete(this.carRepository.findById(id).get());
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public Response<CarDTO> updateCar(CarDTO carDTO) {
		
		Response<CarDTO> res = new Response<CarDTO>();
		
		Car car = new Car();


		car.setId(carDTO.getId());

		
		if(carDTO.getCylinders()!=null) {
			car.setCylinders(carDTO.getCylinders());
		}
		
		if(carDTO.getDisplacement()!=null) {
			car.setDisplacement(carDTO.getDisplacement());
		}
		
		if(carDTO.getHorsePower()!=null) {
			car.setHorsePower(carDTO.getHorsePower());
		}
		if(carDTO.getMilesPerGallon()!=null) {
			car.setMilesPerGallon(carDTO.getMilesPerGallon());
		}
		if(carDTO.getName()!=null) {
			car.setName(carDTO.getName());
		}
		if(carDTO.getWeightInLbs()!=null) {
			car.setWeightInLbs(carDTO.getWeightInLbs());
		}
		
		
		try {
			this.carRepository.save(car);
			
			res.setResult(carDTO);
			res.setResultTest(true);
			
		}catch(Exception e) {
			res.setError("car not updated");
			res.setResultTest(false);
		}
		
		return res;
	}

	public Response<List<CarDTO>> findAllCars() {

		Response<List<CarDTO>> response = new Response<List<CarDTO>>();
		List<CarDTO> result = new ArrayList<>();

		Iterator<Car> iterator = this.carRepository.findAll().iterator();

		while (iterator.hasNext()){
			Car car=iterator.next();

			result.add(CarDTO.build(car));
		}

		response.setResult(result);
		if(result!=null){
			response.setResultTest(true);
		}else{
			response.setResultTest(false);
		}

		return response;
	}
}
