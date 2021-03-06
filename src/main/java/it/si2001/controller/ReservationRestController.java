package it.si2001.controller;

import it.si2001.dto.ReservationDTO;
import it.si2001.dto.ReservationTableDTO;
import it.si2001.dto.Response;
import it.si2001.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/rest/reservation")
public class ReservationRestController {

    private ReservationService reservationService;

    public ReservationRestController(ReservationService reservationService){
        this.reservationService=reservationService;
    }

    private static Logger log = LoggerFactory.getLogger(ReservationRestController.class);

    @PostMapping(path = "/create")
    public Response<ReservationDTO> createReservation(@RequestBody ReservationDTO res) throws ParseException {
        log.info("ricevuta richiesta creazione prenotazione");
        return this.reservationService.createReservation(res);
    }

    @GetMapping(path = "/findAllReservations")
    public Response<List<ReservationDTO>> findAllReservations(){

        List<ReservationDTO> list=this.reservationService.findAllReservations();
        Response<List<ReservationDTO>> response=new Response<>();
        response.setResult(list);
        return response;
    }


    @DeleteMapping(path = "/deleteReservationById/{id}")
    public boolean deleteReservationById(@PathVariable int id){
       return this.reservationService.deleteReservationById(id);
    }

    @GetMapping(path = "/getReservationById/{id}")
    public Response<ReservationDTO> getReservationById(@PathVariable int id){
        return this.reservationService.getReservationById(id);
    }

    @GetMapping(path="/getReservationTableByUserId/{id}")
    public Response<List<ReservationTableDTO>> geReservationTableByUserId(@PathVariable int id){
        Response<List<ReservationTableDTO>> response = new Response<>();
        log.info("getReservationTableByUserId request");
        response.setResult(this.reservationService.findReservationByUserId(id));
        return response;
    }

    @GetMapping(path="/getReservationDetailsByReservationId/{id}")
    public  Response<ReservationTableDTO> getReservationDetailsByReservationId(@PathVariable int id){
        Response<ReservationTableDTO> response = new Response<>();

        response.setResult(this.reservationService.findCarByReservationId(id));
        return response;

    }

    @GetMapping(path="/findAllReservationAdmin")
    public Response<List<ReservationTableDTO>> findReservationTable(){
        Response<List<ReservationTableDTO>> response = new Response<>();

        response.setResult(this.reservationService.findReservationTable());
        return response;
    }

    @GetMapping(path="/approveReservation/{id}")
    public Response<ReservationDTO> approveReservation(@PathVariable int id){
        log.info("received request approve reservation");
        return this.reservationService.approveReservation(id);
    }


}
