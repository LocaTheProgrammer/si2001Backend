package it.si2001.dto;


import it.si2001.entity.Reservation;
import org.springframework.beans.BeanUtils;

public class ReservationDTO {

    private int id;
    private int userId;
    private CarDTO car;
    private String reservationDate;


    public static ReservationDTO build(Reservation r){
        ReservationDTO result = new ReservationDTO();
        BeanUtils.copyProperties(r, result);

        return result;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public CarDTO getCarDTO() {
        return this.car;
    }

    public void setCarDTO(CarDTO car) {
        this.car = car;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }
}
