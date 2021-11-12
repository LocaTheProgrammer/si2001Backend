package it.si2001.service;

import it.si2001.controller.UserRestController;
import it.si2001.dao.ReservationRepository;
import it.si2001.dao.UserRepository;
import it.si2001.dto.Response;
import it.si2001.dto.UserDTO;
import it.si2001.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

	private final UserRepository userRepository;
	private final ReservationRepository reservationRepository;

	public UserService(UserRepository userRepository, ReservationRepository reservationRepository) {
		this.userRepository=userRepository;
		this.reservationRepository=reservationRepository;
	}

	final static String error = "Nessun User trovato.";


	public Response<UserDTO> createUser(UserDTO User) {

		Response<UserDTO> response = new Response<>();
		User u = new User();
		u.setEmail(User.getEmail());
		u.setFirstName(User.getFirstName());
		u.setLastName(User.getLastName());
		u.setPassword(BCrypt.hashpw(User.getPassword(), BCrypt.gensalt())); // per cifrare
		u.setRole(User.getRole());

		try {
			if (!this.checkEmail(User.getEmail())) {

				this.userRepository.save(u);
				response.setResult(User);
				response.setResultTest(true);
			} else {
				response.setError("User non creato");

			}

		} catch (Exception e) {
			e.getStackTrace();
			response.setError("User non creato");

		}

		return response;

	}

	// delete
	public Response<String> deleteUserById(int id) {

		Response<String> response = new Response<>();

		try {
			this.userRepository.deleteById(id);
			if(this.reservationRepository.findByUserId(id).size()>0){
				this.reservationRepository.deleteAllByUserId(id);
			}
			response.setResult("User e prenotazioni eliminate.");
			response.setResultTest(true);

		} catch (Exception e) {
			response.setError("User e prenotazioni non eliminate correttamente.");
		}
		return response;
	}

	// findAll
	public Response<List<UserDTO>> findAllUsers() {

		Response<List<UserDTO>> response = new Response<>();

		List<UserDTO> result = new ArrayList<>();

		try {

			for (User user : this.userRepository.findAll()) {

				result.add(UserDTO.build(user));

			}

			response.setResult(result);
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;

	}

	// find User by id
	public Response<UserDTO> findUserById(int id) {

		Response<UserDTO> response = new Response<>();

		try {

			User User = this.userRepository.findById(id).get();

			response.setResult(UserDTO.build(User));
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;

	}



	public boolean checkEmail(String email) {

		boolean isEmailFound = false;
		Response<List<UserDTO>> response = new Response<>();

		List<UserDTO> result = new ArrayList<>();

		try {

			for (it.si2001.entity.User User : this.userRepository.findAll()) {

				if (User.getEmail().equals(email)) {
					isEmailFound = true;
				}

			}

			response.setResult(result);
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return isEmailFound;

	}

	public UserDTO loginUser(String email, String password) {

		UserDTO response = new UserDTO();

		try {

			User user = this.userRepository.findByEmail(email);


			if (BCrypt.checkpw(password, user.getPassword())) { //non va
				response= UserDTO.build(user);
				log.info(response.getFirstName());
			}

		} catch (Exception e) {

			response=null;

		}

		return response;

	}

	// update User
	public Response<UserDTO> updateUser(UserDTO u) {

		Response<UserDTO> response = new Response<>();
		try {
			User user = this.userRepository.findByEmail(u.getEmail());

			if (u.getFirstName() != null)
				user.setFirstName(u.getFirstName());

			if (u.getLastName() != null)
				user.setLastName(u.getLastName());

			if (u.getEmail() != null)
				user.setEmail(u.getEmail());

			if (u.getPassword() != null)
				user.setPassword(u.getPassword());

			this.userRepository.save(user);

			response.setResult(UserDTO.build(user));
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;
	}
}