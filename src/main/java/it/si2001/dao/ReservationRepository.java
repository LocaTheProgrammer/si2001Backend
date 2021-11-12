package it.si2001.dao;

import it.si2001.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {



    List<Reservation> findByUserId(int id);
    void deleteAllByUserId(int id);
    void deleteAllByCarId(int id);

    List<Reservation> findAllByCarId(int id);
}
