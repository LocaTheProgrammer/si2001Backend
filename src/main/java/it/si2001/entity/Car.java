package it.si2001.entity;

import javax.persistence.*;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "milesPerGallon")
    private String milesPerGallon;

    @Column(name = "cylinders")
    private String cylinders;

    @Column(name = "displacement")
    private String displacement;

    @Column(name = "horsePower")
    private String horsePower;

    @Column(name = "weightInLbs")
    private String weightInLbs;

    @Column(name = "acceleration")
    private String acceleration;

    @Column(name = "year")
    private Date year;

    @Column(name = "origin")
    private String origin;



    @OneToMany(mappedBy = "car",  cascade= CascadeType.REMOVE , fetch = FetchType.LAZY) //cascade = CascadeType.REMOVE v
    List<Reservation> reservationList;

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    public String getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(String acceleration) {
        this.acceleration = acceleration;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMilesPerGallon() {
        return milesPerGallon;
    }

    public void setMilesPerGallon(String milesPerGallon) {
        this.milesPerGallon = milesPerGallon;
    }

    public String getCylinders() {
        return cylinders;
    }

    public void setCylinders(String cylinders) {
        this.cylinders = cylinders;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public String getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(String horsePower) {
        this.horsePower = horsePower;
    }

    public String getWeightInLbs() {
        return weightInLbs;
    }

    public void setWeightInLbs(String weightInLbs) {
        this.weightInLbs = weightInLbs;
    }


    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", milesPerGallon='" + milesPerGallon + '\'' +
                ", cylinders='" + cylinders + '\'' +
                ", displacement='" + displacement + '\'' +
                ", horsePower='" + horsePower + '\'' +
                ", weightInLbs='" + weightInLbs + '\'' +
                ", acceleration='" + acceleration + '\'' +
                ", year='" + year + '\'' +
                ", origin='" + origin + '\'' +
                '}';
    }
}
