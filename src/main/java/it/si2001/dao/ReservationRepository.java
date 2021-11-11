package it.si2001.dao;

import it.si2001.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByToDateAfterAndFromDateBefore(Date toDate, Date fromDate); //fine prima inizio inizio dopo fine
    List<Reservation> findByToDateBeforeAndFromDateAfter(Date toDate, Date fromDate);
    List<Reservation> findByUserId(int userId);
}
