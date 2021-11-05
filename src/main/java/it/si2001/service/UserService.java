package it.si2001.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import it.si2001.dao.UserRepository;
import it.si2001.dto.Response;
import it.si2001.dto.UserDTO;
import it.si2001.entity.User;

@Service
public class UserService {

	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	final static String error = "Nessun User trovato.";


	public Response<UserDTO> createUser(UserDTO User) {

		Response<UserDTO> response = new Response<UserDTO>();
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

		Response<String> response = new Response<String>();

		try {
			this.userRepository.deleteById(id);

			response.setResult("User eliminato.");
			response.setResultTest(true);

		} catch (Exception e) {
			response.setError("User non eliminato correttamente.");
		}
		return response;
	}

	// findAll
	public Response<List<UserDTO>> findAllUsers() {

		Response<List<UserDTO>> response = new Response<List<UserDTO>>();

		List<UserDTO> result = new ArrayList<>();

		try {

			Iterator<User> iterator = this.userRepository.findAll().iterator();

			while (iterator.hasNext()) {

				User User = iterator.next();
				result.add(UserDTO.build(User));

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

		Response<UserDTO> response = new Response<UserDTO>();

		try {

			User User = this.userRepository.findById(id).get();

			response.setResult(UserDTO.build(User));
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;

	}

	public Response<UserDTO> findUserByEmail(String email) {

		Response<UserDTO> response = new Response<UserDTO>();

		try {

			User User = this.userRepository.findByEmail(email);

			response.setResult(UserDTO.build(User));
			response.setResultTest(true);

		} catch (Exception e) {

			response.setError(error);

		}

		return response;

	}

	public boolean checkEmail(String email) {

		boolean isEmailFound = false;
		Response<List<UserDTO>> response = new Response<List<UserDTO>>();

		List<UserDTO> result = new ArrayList<>();

		try {

			Iterator<User> iterator = this.userRepository.findAll().iterator();

			while (iterator.hasNext()) {

				User User = iterator.next();
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

	public Response<UserDTO> loginUser(String email, String password) {

		Response<UserDTO> response = new Response<UserDTO>();

		try {

			User User = this.userRepository.findByEmail(email);

			if (User.getPassword().equals(password)) {
				response.setResult(UserDTO.build(User));
				response.setResultTest(true);
			}

		} catch (Exception e) {

			response.setError(error);

		}

		return response;

	}

	// update User
	public Response<UserDTO> updateUser(UserDTO u) {

		Response<UserDTO> response = new Response<UserDTO>();
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