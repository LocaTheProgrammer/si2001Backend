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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public Response<ReservationDTO> createReservation(ReservationDTO reservationDTO) throws ParseException {
        String[] fromDateString=EntityDTOConverter.simpleDateFormat.format(reservationDTO.getFromDate()).split("-");
        String[] toDateString=EntityDTOConverter.simpleDateFormat.format(reservationDTO.getToDate()).split("-");

        int fromMonth=Integer.parseInt(fromDateString[1])+1;
        int toMonth=Integer.parseInt(toDateString[1])+1;

        String fromDateCorrect=fromDateString[0]+"-"+fromMonth+"-"+fromDateString[2];
        String toDateCorrect=toDateString[0]+"-"+toMonth+"-"+toDateString[2];

        Date fromDateCorrectDate = EntityDTOConverter.simpleDateFormat.parse(fromDateCorrect);
        Date toDateCorrectDate = EntityDTOConverter.simpleDateFormat.parse(toDateCorrect);
        reservationDTO.setFromDate(fromDateCorrectDate);
        reservationDTO.setToDate(toDateCorrectDate);
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
                    carDTOListReserved.add(this.entityDTOConverter.carEntityToCarDTO(c.get()));
                }

            }
        }

        log.info("carDtoReserved: "+Arrays.toString(carDTOListReserved.toArray()));


        return  getReservationTableDTOList(reservationDTO, carDTOListReserved);
    }


    public ReservationTableDTO findCarByReservationId(int id) {

        Optional<Reservation> r = this.reservationRepository.findById(id);
        if (r.isPresent()){
        Car c = r.get().getCar();
        CarDTO carDTO = CarDTO.build(c);
        ReservationTableDTO ret = new ReservationTableDTO();


            ret.setId(r.get().getId());
            ret.setName(carDTO.getName());
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
        List<Reservation> reservationList = this.reservationRepository.findAll();
        List<CarDTO> carDTOListReserved = new ArrayList<>();

       ReservationDTO singleReservationDTO;
        for (Reservation reservation : reservationList) {

            singleReservationDTO = this.entityDTOConverter.reservationEntityToReservationDTO(reservation);
            reservationDTO.add(singleReservationDTO);
        }

        log.info("reservationDTO: " + Arrays.toString(reservationDTO.toArray()));


        for (ReservationDTO dto : reservationDTO) {
            Car c;
            c = this.carRepository.findById(dto.getCarId()).get();
            carDTOListReserved.add(this.entityDTOConverter.carEntityToCarDTO(c));
        }


        return getReservationTableDTOList(reservationDTO, carDTOListReserved);
    }

    private List<ReservationTableDTO> getReservationTableDTOList(List<ReservationDTO> reservationDTO, List<CarDTO> carDTOListReserved) {
        List<ReservationTableDTO> ret = new ArrayList<>();

        for (int j = 0; j < reservationDTO.size(); j++) {
            ReservationTableDTO reservationTableDTO = new ReservationTableDTO();
            reservationTableDTO.setFromDate(reservationDTO.get(j).getFromDate());
            reservationTableDTO.setToDate(reservationDTO.get(j).getToDate());
            reservationTableDTO.setUserId(reservationDTO.get(j).getUserId());
            reservationTableDTO.setId(reservationDTO.get(j).getId());
            reservationTableDTO.setName(this.carRepository.findById(carDTOListReserved.get(j).getId()).get().getName());
            reservationTableDTO.setIsApproved(reservationDTO.get(j).getIsApproved());
            ret.add(reservationTableDTO);
        }
        log.info("ret: "+ret);
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
