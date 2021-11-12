package it.si2001.dto;

import it.si2001.entity.Reservation;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class ReservationDTO {

    private int id;
    private int userId;
    private int carId;
    private Date fromDate;
    private Date toDate;
    private boolean isApproved;




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
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
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

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", carId=" + carId +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}
