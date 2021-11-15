package it.si2001.service;

import it.si2001.controller.CarRestController;
import it.si2001.converter.EntityDTOConverter;
import it.si2001.dao.CarRepository;
import it.si2001.dao.ReservationRepository;
import it.si2001.dto.CarDTO;
import it.si2001.dto.NgbDateDTO;
import it.si2001.dto.Response;
import it.si2001.entity.Car;
import it.si2001.entity.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;
    private final EntityDTOConverter entityDTOConverter;

    public CarService(CarRepository carRepository, ReservationRepository reservationRepository, EntityDTOConverter entityDTOConverter) {
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
        this.entityDTOConverter=entityDTOConverter;
    }

    private static final Logger log = LoggerFactory.getLogger(CarRestController.class);

    @Transactional
    public Response<CarDTO> createCar(CarDTO carDTO) throws ParseException {

        Response<CarDTO> res = new Response<>();

        Car car = this.entityDTOConverter.carDtoToCarEntity(carDTO);

        Optional<Car> carToSaveOPT= this.carRepository.findById(carDTO.getId());

        if(carToSaveOPT.isPresent()){
            this.carRepository.save(car);
            res.setResult(carDTO);
            res.setResultTest(true);
        }else{
            this.carRepository.save(car);
            res.setResult(carDTO);
            res.setResultTest(true);
        }



        return res;

    }

    public Response<CarDTO> findCarById(int id) {

        Response<CarDTO> res = new Response<>();

        try {
            Optional<Car> c = this.carRepository.findById(id);
            if(c.isPresent()){
                res.setResult(CarDTO.build(c.get()));
                res.setResultTest(true);
            }

        } catch (Exception e) {
            res.setError("no car found for id: " + id);
        }

        return res;
    }

    @Transactional
    public Response<Boolean> deleteCarById(int id) {
        Response<Boolean> res =new Response<>();
        try {
            Optional<Car> c = this.carRepository.findById(id);
            if(c.isPresent()){
                this.carRepository.delete(c.get());
                res.setResult(true);
                return res;
            }

        } catch (Exception e) {
            res.setResult(false);
            return res;
        }
        return res;
    }

    @Transactional
    public Response<CarDTO> updateCar(CarDTO carDTO) throws ParseException {

        Response<CarDTO> res = new Response<>();

        Optional<Car> car =  this.carRepository.findById(carDTO.getId());



        if (car.isPresent()){
            if (carDTO.getCylinders() != null) {
                car.get().setCylinders(carDTO.getCylinders());
            }

            if (carDTO.getDisplacement() != null) {
                car.get().setDisplacement(carDTO.getDisplacement());
            }

            if (carDTO.getHorsePower() != null) {
                car.get().setHorsePower(carDTO.getHorsePower());
            }
            if (carDTO.getMilesPerGallon() != null) {
                car.get().setMilesPerGallon(carDTO.getMilesPerGallon());
            }
            if (carDTO.getName() != null) {
                car.get().setName(carDTO.getName());
            }
            if (carDTO.getWeightInLbs() != null) {
                car.get().setWeightInLbs(carDTO.getWeightInLbs());
            }

            if (carDTO.getAcceleration() != null) {
                car.get().setAcceleration(carDTO.getAcceleration());
            }
            if (carDTO.getYear() != null) {
                car.get().setYear(this.entityDTOConverter.simpleDateFormat.parse(carDTO.getYear()));
            }
            if (carDTO.getOrigin() != null) {
                car.get().setOrigin(carDTO.getOrigin());
            }

            try {
                this.carRepository.save(car.get());

                res.setResult(carDTO);
                res.setResultTest(true);

            } catch (Exception e) {
                res.setResult(null);
                res.setError("car not updated");
                res.setResultTest(false);
            }
        }


        return res;
    }

    public List<CarDTO> findAllCars() {


        List<CarDTO> result = new ArrayList<>();

        for (Car car : this.carRepository.findAll()) {
            result.add(CarDTO.build(car));
        }


        if (result != null) {
            return result;
        } else {
            return null;
        }


    }


    public List<CarDTO> getFreeCarByReservationDate(NgbDateDTO fromDate, NgbDateDTO toDate) {

        List<Reservation> reservation;
        reservation=this.reservationRepository.findAll();
        List<CarDTO> carDTOList = this.findAllCars();
        if (reservation.size() == 0) {
            return carDTOList;
        } else {
            List<CarDTO> availableCarList = new ArrayList<>();
            int fromMonth = fromDate.getMonth();
            int toMonth = toDate.getMonth();
            String dateFromS = fromDate.getYear() + "-" + fromMonth + "-" + fromDate.getDay();
            String dateToS = toDate.getYear() + "-" + toMonth + "-" + toDate.getDay();

            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date from = new Date();
            Date to = new Date();


            try {
                from = simpleDateFormat.parse(dateFromS);
                log.info("from: " + from);
            } catch (ParseException e) {
                log.error("error in parsing date From");
                e.printStackTrace();
            }


            try {
                to = simpleDateFormat.parse(dateToS);
                log.info("to: " + to);
            } catch (ParseException e) {
                log.error("error in parsing date To");
                e.printStackTrace();
            }

            String fromStringed=simpleDateFormat.format(from);
            String toStringed=simpleDateFormat.format(to);
           List<Car> freeCarList=this.carRepository.findReservationBusyInPeriod(fromStringed,toStringed);
            log.info("freeCarList: "+Arrays.toString(freeCarList.toArray()));
            for (Car car : freeCarList) {
                CarDTO cDTO = this.entityDTOConverter.carEntityToCarDTO(car);
                availableCarList.add(cDTO);
            }
            return availableCarList;
        }
    }


}
