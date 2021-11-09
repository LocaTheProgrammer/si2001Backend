package it.si2001.dao;

import it.si2001.dto.ReservationDTO;
import it.si2001.entity.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {


    List<Reservation> findByUserId(int userId);
}
