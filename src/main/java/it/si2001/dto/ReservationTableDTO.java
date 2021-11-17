package it.si2001.dto;

import it.si2001.entity.Reservation;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class ReservationTableDTO {

    private int id;
    private int userId;
    private String name;
    private String fromDate;
    private String toDate;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
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
                ", name='" + name + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", isApproved=" + isApproved +
                '}';
    }
}
