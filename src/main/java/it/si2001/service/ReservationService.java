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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private EntityDTOConverter entityDTOConverter;


    public ReservationService (ReservationRepository reservationRepository, CarRepository carRepository, UserRepository userRepository, EntityDTOConverter entityDTOConverter){
        this.reservationRepository=reservationRepository;
        this.carRepository=carRepository;
        this.userRepository=userRepository;
        this.entityDTOConverter = entityDTOConverter;
    }

    private static final Logger log = LoggerFactory.getLogger(ReservationService.class);



    public Response<ReservationDTO> createReservation(ReservationDTO reservationDTO){

        Reservation reservation = new Reservation();
        Response<ReservationDTO> response = new Response<>();

        reservation=this.entityDTOConverter.reservationDtoToReservationEntity(reservationDTO);

        try{
            this.reservationRepository.save(reservation);
            response.setResultTest(true);
            response.setResult(reservationDTO);
        }catch (Exception e){
            response.setResultTest(false);
            response.setError("reservation not created");
        }
        return response;
    }

    public List<ReservationDTO> findAllReservations(){

        Response<List<ReservationDTO>> response;
        response = new Response<>();
        List<ReservationDTO> result = new ArrayList<>();

        for (Reservation r : this.reservationRepository.findAll()) {
            result.add(ReservationDTO.build(r));
        }
        response.setResult(result);
        if(result!=null){
            response.setResultTest(true);
        }else{
            response.setResultTest(false);
        }
        return result;
    }

    public boolean deleteReservationById(int id){
        try{
            this.reservationRepository.delete(this.reservationRepository.findById(id).get());
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public Response<ReservationDTO> getReservationById(int id){

        Response<ReservationDTO> res = new Response<>();

        try{
            Reservation r;
            r = this.reservationRepository.findById(id).get();
            res.setResult(ReservationDTO.build(r));
            res.setResultTest(true);
        }catch (Exception e){
            res.setError("no reservation found for id: " + id);
        }

        return res;
    }


    //todo da riscrivere tutto
    public List<ReservationTableDTO> findReservationByUserId(int userId){

        List<ReservationDTO>reservationDTO=new ArrayList<>();
        List<Reservation> reservationList =this.reservationRepository.findByUserId(userId);
        User u = this.userRepository.findById(userId).get();
        UserDTO userDTO=UserDTO.build(u);

       for (int i=0; i<reservationList.size();i++){
           reservationList.get(i).setUser(u);

       }



        return null;//todo da cambiare
    }


    public ReservationTableDTO findCarByReservationId(int id){
        Reservation r=this.reservationRepository.findById(id).get();
        Car c=this.carRepository.findById(r.getCar().getId()).get();
        CarDTO carDTO=CarDTO.build(c);
        ReservationTableDTO ret= new ReservationTableDTO();

        ret.setId(r.getId());
        ret.setCar(carDTO);
        ret.setFromDate(r.getFromDate());
        ret.setToDate(r.getToDate());
        ret.setUserId(1);

        return ret;
    }




    public List<ReservationTableDTO> findReservationTable(){

        List<ReservationDTO>reservationDTO=new ArrayList<>();
        Iterator<Reservation> iterator =this.reservationRepository.findAll().iterator();
        List<CarDTO> carDTOListReserved=new ArrayList<>();

        while (iterator.hasNext()){
            Reservation r=iterator.next();
            reservationDTO.add(ReservationDTO.build(r));
        }
        log.info("reservationDTO: "+ Arrays.toString(reservationDTO.toArray()));


        for (ReservationDTO dto : reservationDTO) {
            Car c;
            c = this.carRepository.findById(dto.getCarId()).get();
            carDTOListReserved.add(CarDTO.build(c));
        }


        return getReservationTableDTOList(reservationDTO, carDTOListReserved);
    }

    private List<ReservationTableDTO> getReservationTableDTOList(List<ReservationDTO>reservationDTO, List<CarDTO> carDTOListReserved){
        List<ReservationTableDTO> ret = new ArrayList<>();
        ReservationTableDTO reservationTableDTO= new ReservationTableDTO();
        log.info(Arrays.toString(carDTOListReserved.toArray()));
        for (int j=0; j<reservationDTO.size(); j++){
            reservationTableDTO.setFromDate(reservationDTO.get(j).getFromDate());
            reservationTableDTO.setToDate(reservationDTO.get(j).getToDate());
            reservationTableDTO.setUserId(reservationDTO.get(j).getUserId());
            reservationTableDTO.setId(reservationDTO.get(j).getId());
            reservationTableDTO.setCar(carDTOListReserved.get(j));
            ret.add(reservationTableDTO);
        }
        return ret;
    }

}
