package it.si2001.dto;


import it.si2001.entity.Reservation;
import org.springframework.beans.BeanUtils;

public class ReservationDTO {

    private int id;
    private int userId;
    private int carId;
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

    public int getCarId() {
        return this.carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }


    @Override
    public String toString(){
        return "id: "+getId()+" user id: "+getUserId()+" carDto: "+getCarId()+" res date: "+getReservationDate();
    }
}
