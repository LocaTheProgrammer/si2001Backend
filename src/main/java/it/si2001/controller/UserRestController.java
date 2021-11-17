package it.si2001.controller;

import it.si2001.dto.JwtRequest;
import it.si2001.dto.Response;
import it.si2001.dto.UserDTO;
import it.si2001.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/rest/user")
public class UserRestController {
	
	private UserService userService;
	
	public UserRestController(UserService userService) {
		this.userService=userService;
	}
	
	private static Logger log = LoggerFactory.getLogger(UserRestController.class);
	
	@PostMapping(path="/create")
	public Response<?> createUser(@RequestBody UserDTO u){
		log.info("Ricevuta richiesta della creazion User");
		return userService.createUser(u,0);
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
	
	@PutMapping(path="/update/{id}")
	public Response<?> updateUser(@RequestBody UserDTO u, @PathVariable int id){
		log.info("Ricevuta richiesta della update User");
		return userService.createUser(u, id);
	}
	
	@GetMapping(path = "/findAll")
	public Response<?> findAllArticoli() {
		log.info("Ricevuta richiesta della lista di tutti gli user");
		return userService.findAllUsers();	
	}
	
	@PostMapping(path="/logIn")
	public Response<?> signIn(@RequestBody JwtRequest credentials){
		log.info("richiesta di logIn");
		UserDTO userDTO=userService.loginUser(credentials.getUsername(), credentials.getPassword());
		Response<UserDTO> response=new Response<>();
		response.setResult(userDTO);
		return response;

	}

}
