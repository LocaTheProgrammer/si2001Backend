package it.si2001.controller;

import it.si2001.dto.ReservationDTO;
import it.si2001.dto.Response;
import it.si2001.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/reservation")
public class ReservationRestController {

    private ReservationService reservationService;

    public ReservationRestController(ReservationService reservationService){
        this.reservationService=reservationService;
    }


    @PostMapping(path = "/create")
    public Response<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO){
        return this.reservationService.createReservation(reservationDTO);
    }

    @GetMapping(path = "/findAllReservations")
    public Response<List<ReservationDTO>> findAllReservations(){
        return this.reservationService.findAllReservations();
    }


    @DeleteMapping(path = "/deleteReservationById/{id}")
    public boolean deleteReservationById(@PathVariable int id){
       return this.reservationService.deleteReservationById(id);
    }

    @GetMapping(path = "/getReservationById/{id}")
    public Response<ReservationDTO> getReservationById(int id){
        return this.reservationService.getReservationById(id);
    }
}
