package it.si2001.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.si2001.entity.Car;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer>{

}