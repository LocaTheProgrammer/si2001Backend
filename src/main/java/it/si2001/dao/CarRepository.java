package it.si2001.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.si2001.entity.Car;

import java.util.Date;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    @Query(value ="SELECT * FROM Car c WHERE c.id NOT IN"+
            "(SELECT r.id FROM Reservation r " +
            "WHERE (?1 < r.to_date AND ?2 >= r.from_date " +
                    "OR ?2 > r.from_date AND ?2 <= r.to_date " +
                    "OR ?1 < r.from_date AND ?2 > r.to_date) AND is_approved=1)",nativeQuery = true)
    List<Car> findReservationBusyInPeriod(String fromDate, String ToDate);


}
