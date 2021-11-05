package it.si2001.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.si2001.dto.Response;
import it.si2001.dto.UserDTO;
import it.si2001.service.UserService;


@RestController
@RequestMapping("/rest/User")
public class UserRestController {
	
	@Autowired
	private UserService userService;
	
	private static Logger log = LoggerFactory.getLogger(UserRestController.class);
	
	@PostMapping(path="/create")
	public Response<?> createUser(@RequestBody UserDTO u){
		log.info("Ricevuta richiesta della creazion User");
		return userService.createUser(u);
	}
	
	
	@PostMapping(path="/delete")
	public Response<?> deleteUser(@RequestBody int id){
		log.info("Ricevuta richiesta della delete User");
		return userService.deleteUserById(id);
	}
	
	@PostMapping(path="/findById")
	public Response<?> findUserById(@RequestBody int id){
		log.info("Ricevuta richiesta della delete User");
		return userService.findUserById(id);
	}
	
	@PostMapping(path="/update")
	public Response<?> updateUser(@RequestBody UserDTO u){
		log.info("Ricevuta richiesta della update User");
		return userService.updateUser(u);
	}
	
	@GetMapping(path = "/findAll")
	public Response<?> findAllArticoli() {
		log.info("Ricevuta richiesta della lista di tutti gli articoli");
		return userService.findAllUsers();	
	}
	
	@PostMapping(path="/logIn/{mail}/{password}")
	public Response<?> signIn(@PathVariable String mail, @PathVariable String password){
		
		return userService.loginUser(mail, password);

	}

}
