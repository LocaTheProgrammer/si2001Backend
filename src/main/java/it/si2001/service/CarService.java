package it.si2001.service;

import it.si2001.controller.CarRestController;
import it.si2001.converter.EntityDTOConverter;
import it.si2001.dao.CarRepository;
import it.si2001.dao.ReservationRepository;
import it.si2001.dto.CarDTO;
import it.si2001.dto.NgbDateDTO;
import it.si2001.dto.ReservationDTO;
import it.si2001.dto.Response;
import it.si2001.entity.Car;
import it.si2001.entity.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;
    private EntityDTOConverter entityDTOConverter;

    public CarService(CarRepository carRepository, ReservationRepository reservationRepository, EntityDTOConverter entityDTOConverter) {
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
        this.entityDTOConverter=entityDTOConverter;
    }

    private static final Logger log = LoggerFactory.getLogger(CarRestController.class);


    public Response<CarDTO> createCar(CarDTO carDTO) throws ParseException {

        Response<CarDTO> res = new Response<>();

        Car car = this.entityDTOConverter.carDtoToCarEntity(carDTO);

        this.carRepository.save(car);
        res.setResult(carDTO);
        res.setResultTest(true);

        return res;

    }

    public Response<CarDTO> findCarById(int id) {

        Response<CarDTO> res = new Response<>();

        try {
            Car c = this.carRepository.findById(id).get();
            res.setResult(CarDTO.build(c));
            res.setResultTest(true);

        } catch (Exception e) {
            res.setError("no car found for id: " + id);
        }

        return res;
    }

    public Response<Boolean> deleteCarById(int id) {
        Response<Boolean> res =new Response<>();
        try {
            this.carRepository.delete(this.carRepository.findById(id).get());
            res.setResult(true);
            return res;
        } catch (Exception e) {
            res.setResult(false);
            return res;
        }

    }

    public Response<CarDTO> updateCar(CarDTO carDTO) throws ParseException {

        Response<CarDTO> res = new Response<>();

        Car car =  this.carRepository.findById(carDTO.getId()).get();




        if (carDTO.getCylinders() != null) {
            car.setCylinders(carDTO.getCylinders());
        }

        if (carDTO.getDisplacement() != null) {
            car.setDisplacement(carDTO.getDisplacement());
        }

        if (carDTO.getHorsePower() != null) {
            car.setHorsePower(carDTO.getHorsePower());
        }
        if (carDTO.getMilesPerGallon() != null) {
            car.setMilesPerGallon(carDTO.getMilesPerGallon());
        }
        if (carDTO.getName() != null) {
            car.setName(carDTO.getName());
        }
        if (carDTO.getWeightInLbs() != null) {
            car.setWeightInLbs(carDTO.getWeightInLbs());
        }

        if (carDTO.getAcceleration() != null) {
            car.setAcceleration(carDTO.getAcceleration());
        }
        if (carDTO.getYear() != null) {
            car.setYear(this.entityDTOConverter.simpleDateFormat.parse(carDTO.getYear()));
        }
        if (carDTO.getOrigin() != null) {
            car.setOrigin(carDTO.getOrigin());
        }


        log.info("updating car: "+car.toString());
        try {
            this.carRepository.save(car);

            res.setResult(carDTO);
            res.setResultTest(true);

        } catch (Exception e) {
            res.setResult(null);
            res.setError("car not updated");
            res.setResultTest(false);
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


    public List<CarDTO> getFreeCarByReservationDate(NgbDateDTO fromDate, NgbDateDTO toDate) throws ParseException {

        List<ReservationDTO> reservationDTOList = new ArrayList<>();

        List<CarDTO> carDTOList = this.findAllCars();
        if (reservationDTOList.size() == 0) {
            return carDTOList;
        } else {
            List<CarDTO> availableCarList = new ArrayList<>();
            int fromMonth = fromDate.getMonth() - 1;
            int toMonth = toDate.getMonth() - 1;
            String dateFromS = fromDate.getYear() + "/" + fromMonth + "/" + fromDate.getDay();
            String dateToS = toDate.getYear() + "/" + toMonth + "/" + toDate.getDay();

            String pattern = "yyyy/MM/dd";
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

//            List<Reservation>reservationList=this.reservationRepository.findByToDateBeforeAndFromDateAfter(to,from);
            List<Reservation> reservationList = this.reservationRepository.findAll();
            log.info("sasd");
            return availableCarList;
        }
    }


    private Date getDateToCheck(String[] date, SimpleDateFormat simpleDateFormat) throws ParseException {
        int month = Integer.parseInt(date[1]);
        String formattedDateFromString = date[0] + "/" + month + "/" + date[2];
        return simpleDateFormat.parse(formattedDateFromString);
    }


}
