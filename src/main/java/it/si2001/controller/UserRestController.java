package it.si2001.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.si2001.dto.Response;
import it.si2001.dto.UserDTO;
import it.si2001.service.UserService;


@RestController
@RequestMapping("/rest/User")
public class UserRestController {
	
	private UserService userService;
	
	public UserRestController(UserService userService) {
		this.userService=userService;
	}
	
	private static Logger log = LoggerFactory.getLogger(UserRestController.class);
	
	@PostMapping(path="/create")
	public Response<?> createUser(@RequestBody UserDTO u){
		log.info("Ricevuta richiesta della creazion User");
		return userService.createUser(u);
	}
	
	
	@DeleteMapping(path="/delete/{id}")
	public Response<?> deleteUser(@PathVariable int id){
		log.info("Ricevuta richiesta della delete User");
		return userService.deleteUserById(id);
	}
	
	@GetMapping(path="/findById/{id}")
	public Response<?> findUserById(@PathVariable int id){
		log.info("Ricevuta richiesta della delete User");
		return userService.findUserById(id);
	}
	
	@PutMapping(path="/update")
	public Response<?> updateUser(@RequestBody UserDTO u){
		log.info("Ricevuta richiesta della update User");
		return userService.updateUser(u);
	}
	
	@GetMapping(path = "/findAll")
	public Response<?> findAllArticoli() {
		log.info("Ricevuta richiesta della lista di tutti gli user");
		return userService.findAllUsers();	
	}
	
	@PostMapping(path="/logIn/{mail}/{password}")
	public Response<?> signIn(@PathVariable String mail, @PathVariable String password){
		
		return userService.loginUser(mail, password);

	}

}
