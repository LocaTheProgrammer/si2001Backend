package it.si2001.service;

import it.si2001.converter.EntityDTOConverter;
import it.si2001.dao.CarRepository;
import it.si2001.dao.ReservationRepository;
import it.si2001.dao.UserRepository;
import it.si2001.dto.*;
import it.si2001.entity.Car;
import it.si2001.entity.Reservation;
import it.si2001.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final EntityDTOConverter entityDTOConverter;


    public ReservationService(ReservationRepository reservationRepository, CarRepository carRepository, UserRepository userRepository, EntityDTOConverter entityDTOConverter) {
        this.reservationRepository = reservationRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.entityDTOConverter = entityDTOConverter;
    }

    private static final Logger log = LoggerFactory.getLogger(ReservationService.class);

    @Transactional
    public Response<ReservationDTO> createReservation(ReservationDTO reservationDTO) {

        Reservation reservation = this.entityDTOConverter.reservationDtoToReservationEntity(reservationDTO);
        Response<ReservationDTO> response = new Response<>();
        reservation.setIsApproved(0);


        try {
            this.reservationRepository.save(reservation);
            response.setResultTest(true);
            response.setResult(reservationDTO);
        } catch (Exception e) {
            response.setResultTest(false);
            response.setError("reservation not created");
        }
        return response;
    }

    public List<ReservationDTO> findAllReservations() {

        List<ReservationDTO> result = new ArrayList<>();

        for (Reservation r : this.reservationRepository.findAll()) {
            result.add(ReservationDTO.build(r));
        }


        return result;
    }

    @Transactional
    public boolean deleteReservationById(int id) {
        try {
            Optional<Reservation> r = this.reservationRepository.findById(id);
            if(r.isPresent()){
                this.reservationRepository.delete(this.reservationRepository.findById(id).get());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public Response<ReservationDTO> getReservationById(int id) {

        Response<ReservationDTO> res = new Response<>();

        try {
            Optional<Reservation> r = this.reservationRepository.findById(id);

            if (r.isPresent()){
                res.setResult(ReservationDTO.build(r.get()));
                res.setResultTest(true);
            }

        } catch (Exception e) {
            res.setError("no reservation found for id: " + id);
        }

        return res;
    }


    public List<ReservationTableDTO> findReservationByUserId(int userId) {

        List<ReservationDTO> reservationDTO = new ArrayList<>();
        List<Reservation> reservationList = this.reservationRepository.findByUserId(userId);

        Optional<User> u = this.userRepository.findById(userId);
        List<CarDTO> carDTOListReserved=new ArrayList<>();

        if (u.isPresent()){
            for (Reservation reservation : reservationList) {
                reservation.setUser(u.get());
                reservationDTO.add(this.entityDTOConverter.reservationEntityToReservationDTO(reservation));
            }
            for (ReservationDTO dto : reservationDTO) {
                int id=dto.getCarId();
                log.info("car id: "+id);
                Optional<Car> c = this.carRepository.findById(id);

                if (c.isPresent()){
                    carDTOListReserved.add(CarDTO.build(c.get()));
                }

            }
        }


        return  getReservationTableDTOList(reservationDTO, carDTOListReserved);
    }


    public ReservationTableDTO findCarByReservationId(int id) {

        Optional<Reservation> r = this.reservationRepository.findById(id);
        if (r.isPresent()){
        Car c = r.get().getCar();
        CarDTO carDTO = CarDTO.build(c);
        ReservationTableDTO ret = new ReservationTableDTO();


            ret.setId(r.get().getId());
            ret.setCar(carDTO);
            ret.setFromDate(r.get().getFromDate());
            ret.setToDate(r.get().getToDate());
            ret.setUserId(1);
            return ret;
        }else{
            return null;
        }


    }


    public List<ReservationTableDTO> findReservationTable() {

        List<ReservationDTO> reservationDTO = new ArrayList<>();
        Iterator<Reservation> iterator = this.reservationRepository.findAll().iterator();
        List<CarDTO> carDTOListReserved = new ArrayList<>();

        while (iterator.hasNext()) {
            Reservation r = iterator.next();
            reservationDTO.add(ReservationDTO.build(r));
        }
        log.info("reservationDTO: " + Arrays.toString(reservationDTO.toArray()));


        for (ReservationDTO dto : reservationDTO) {
            Car c;
            c = this.carRepository.findById(dto.getCarId()).get();
            carDTOListReserved.add(CarDTO.build(c));
        }


        return getReservationTableDTOList(reservationDTO, carDTOListReserved);
    }

    private List<ReservationTableDTO> getReservationTableDTOList(List<ReservationDTO> reservationDTO, List<CarDTO> carDTOListReserved) {
        List<ReservationTableDTO> ret = new ArrayList<>();
        ReservationTableDTO reservationTableDTO = new ReservationTableDTO();
        log.info(Arrays.toString(carDTOListReserved.toArray()));
        for (int j = 0; j < reservationDTO.size(); j++) {
            reservationTableDTO.setFromDate(reservationDTO.get(j).getFromDate());
            reservationTableDTO.setToDate(reservationDTO.get(j).getToDate());
            reservationTableDTO.setUserId(reservationDTO.get(j).getUserId());
            reservationTableDTO.setId(reservationDTO.get(j).getId());
            reservationTableDTO.setCar(carDTOListReserved.get(j));
            ret.add(reservationTableDTO);
        }
        return ret;
    }

    @Transactional
    public Response<ReservationDTO> approveReservation(int id) {

        Reservation reservation=reservationRepository.findById(id).get();

        reservation.setIsApproved(1);

        this.reservationRepository.save(reservation);

        Response<ReservationDTO> res = new Response<>();
        res.setResult(this.entityDTOConverter.reservationEntityToReservationDTO(reservation));
        return res;
    }
}
