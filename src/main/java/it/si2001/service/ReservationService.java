package it.si2001.service;

import it.si2001.dao.ReservationRepository;
import it.si2001.dto.ReservationDTO;
import it.si2001.dto.Response;
import it.si2001.entity.Reservation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;

    public ReservationService (ReservationRepository reservationRepository){
        this.reservationRepository=reservationRepository;
    }


    //todo testare bene che quel set car id mmmmboh
    public Response<ReservationDTO> createReservation(ReservationDTO reservationDTO){

        Reservation reservation = new Reservation();
        Response<ReservationDTO> response = new Response<>();


        reservation.setReservationDate(reservationDTO.getReservationDate());
        reservation.setId(reservationDTO.getId());
        reservation.setCarId(reservationDTO.getCarDTO().getId());
        reservation.setUserId(reservationDTO.getUserId());

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

    public Response<List<ReservationDTO>> findAllReservations(){

        Response<List<ReservationDTO>> response=new Response<>();
        List<ReservationDTO> result = new ArrayList<>();

        Iterator<Reservation> iterator =this.reservationRepository.findAll().iterator();

        while (iterator.hasNext()){
            Reservation r=iterator.next();

            result.add(ReservationDTO.build(r));
        }
        response.setResult(result);
        if(result!=null){
            response.setResultTest(true);
        }else{
            response.setResultTest(false);
        }
        return response;
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

        Response<ReservationDTO> res = new Response<ReservationDTO>();

        try{
            Reservation r=this.reservationRepository.findById(id).get();
            res.setResult(ReservationDTO.build(r));
            res.setResultTest(true);
        }catch (Exception e){
            res.setError("no reservation found for id: " + id);
        }

        return res;
    }


}
