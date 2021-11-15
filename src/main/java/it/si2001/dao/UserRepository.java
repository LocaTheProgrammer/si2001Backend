package it.si2001.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.si2001.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
	Optional<User> findByEmail(String email);
}
