package it.si2001.dto;

import it.si2001.entity.Reservation;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class ReservationTableDTO {

    private int id;
    private int userId;
    private CarDTO car;
    private Date fromDate;
    private Date toDate;
    private int isApproved;




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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }

    public int getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(int isApproved) {
        this.isApproved = isApproved;
    }

    @Override
    public String toString() {
        return "ReservationTableDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", car=" + car +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}
