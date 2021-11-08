package it.si2001.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.si2001.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
	User findByEmail(String email);
}
