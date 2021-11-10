package it.si2001.service;

import it.si2001.controller.CarRestController;
import it.si2001.dao.CarRepository;
import it.si2001.dto.CarDTO;
import it.si2001.dto.NgbDateDTO;
import it.si2001.dto.ReservationDTO;
import it.si2001.dto.Response;
import it.si2001.entity.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final ReservationService reservationService;

    public CarService(CarRepository carRepository, ReservationService reservationService) {
        this.carRepository = carRepository;
        this.reservationService = reservationService;
    }

    private static final Logger log = LoggerFactory.getLogger(CarRestController.class);


    public Response<CarDTO> createCar(CarDTO carDTO) {

        Response<CarDTO> res = new Response<>();

        Car car = new Car();
        car.setCylinders(carDTO.getCylinders());
        car.setDisplacement(carDTO.getDisplacement());
        car.setHorsePower(carDTO.getHorsePower());
        car.setMilesPerGallon(carDTO.getMilesPerGallon());
        car.setName(carDTO.getName());
        car.setWeightInLbs(carDTO.getWeightInLbs());
        car.setAcceleration(carDTO.getAcceleration());
        car.setYear(carDTO.getYear());
        car.setOrigin(carDTO.getOrigin());
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

    public boolean deleteCarById(int id) {

        try {
            this.carRepository.delete(this.carRepository.findById(id).get());
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Response<CarDTO> updateCar(CarDTO carDTO) {

        Response<CarDTO> res = new Response<>();

        Car car = new Car();


        car.setId(carDTO.getId());


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
            car.setYear(carDTO.getYear());
        }
        if (carDTO.getOrigin() != null) {
            car.setOrigin(carDTO.getOrigin());
        }


        try {
            this.carRepository.save(car);

            res.setResult(carDTO);
            res.setResultTest(true);

        } catch (Exception e) {
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

        List<ReservationDTO> reservationDTOList = this.reservationService.findAllReservations();
        List<CarDTO> carDTOList = this.findAllCars();
        if (reservationDTOList.size() == 0) {
            return carDTOList;
        } else {
            List<CarDTO> availableCarList = new ArrayList<>();
            List<Integer> busyCarsId = new ArrayList<>();
            int fromMonth=fromDate.getMonth()-1;
            int toMonth=toDate.getMonth()-1;
            String dateFromS = fromDate.getYear() + "/" + fromMonth + "/" + fromDate.getDay();
            String dateToS = toDate.getYear() + "/" + toMonth + "/" + toDate.getDay();

            String pattern = "yyyy/MM/dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date from = new Date();
            Date to = new Date();


            try {
                from = simpleDateFormat.parse(dateFromS);
                log.info("from: "+from);
            } catch (ParseException e) {
                log.error("error in parsing date From");
                e.printStackTrace();
            }


            try {
                to = simpleDateFormat.parse(dateToS);
                log.info("to: "+to);
            } catch (ParseException e) {
                log.error("error in parsing date To");
                e.printStackTrace();
            }


            for (int i = 1; i < reservationDTOList.size(); i++) {
                String[] fromDateArray=reservationDTOList.get(i).getFromDate().toString().substring(0,10).split("-");
                String[] toDateArray=reservationDTOList.get(i).getToDate().toString().substring(0,10).split("-");

                Date reservationDateFromToCheck = getDateToCheck(fromDateArray,simpleDateFormat);
                Date reservationDateToToCheck = getDateToCheck(toDateArray, simpleDateFormat);

                log.info("reservationDateFromToCheck: "+reservationDateFromToCheck);
                log.info("reservationDateToToCheck: "+reservationDateToToCheck);

                log.info("from: "+from);
                log.info("to: "+to);
                boolean isDateAfterValid=from.after(reservationDateFromToCheck)||from.compareTo(reservationDateFromToCheck)==0;
                boolean isDateToVaild=to.before(reservationDateToToCheck)||reservationDateToToCheck.equals(to);


                boolean isDateValid=isDateAfterValid||isDateToVaild;

                log.info("isDateAfterValid? "+isDateAfterValid);
                log.info("isDateToVaild? "+isDateToVaild);
                log.info("isDateValid? "+isDateValid);

                if (isDateValid) {
                    log.info("car id reserved for id: "+reservationDTOList.get(i).getCarId());
                    busyCarsId.add(reservationDTOList.get(i).getCarId());
                }
            }


            for (int i = 1; i < carDTOList.size(); i++) {
                for (int j = 0; j < busyCarsId.size(); j++) {
                    if (carDTOList.get(i).getId() != busyCarsId.get(j)) {
                        availableCarList.add(carDTOList.get(i));
                        break;
                    }
                }
            }

            return availableCarList;
        }
    }


    private Date getDateToCheck(String[] date, SimpleDateFormat simpleDateFormat) throws ParseException {
        int month = Integer.parseInt(date[1]);
        String formattedDateFromString = date[0] + "/" + month + "/" + date[2];
        return simpleDateFormat.parse(formattedDateFromString);
    }


}
