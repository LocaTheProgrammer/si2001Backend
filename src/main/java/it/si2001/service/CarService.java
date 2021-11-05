package it.si2001.service;

import org.springframework.stereotype.Service;

import it.si2001.dao.CarRepository;
import it.si2001.dto.CarDTO;
import it.si2001.dto.Response;
import it.si2001.entity.Car;

@Service
public class CarService {
	
	
	private CarRepository carRepository;
	
	public CarService(CarRepository carRepository) {
		this.carRepository=carRepository;
	}
	
	
	public Response<CarDTO> createCar(CarDTO carDTO) {
		
		
		Response<CarDTO> res= new Response<CarDTO>();
		
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
		}catch(Exception e) {
			res.setError("car not saved");
		}
		
		return res;
		
	}
	
	
	public Response<CarDTO> findCarById(int id){
		
		Response<CarDTO> res=new Response<CarDTO>();
		
		try {
			Car c=this.carRepository.findById(id).get();
			res.setResult(CarDTO.build(c));
			res.setResultTest(true);
			
		}catch(Exception e) {
			res.setError("no car found for id: "+id);
		}
		
		
		return res;
	}
	
	
	public boolean deleteCarById(int id) {
		
		try {
			this.carRepository.delete(this.carRepository.findById(id).get());
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}

}
