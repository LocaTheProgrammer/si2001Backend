package it.si2001.dto;

import org.springframework.beans.BeanUtils;

import it.si2001.entity.User;
import lombok.Data;

@Data
public class UserDTO {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String role;
	
	public static UserDTO build(User u) {

		UserDTO result = new UserDTO();
		BeanUtils.copyProperties(u, result);

		return result;
	}
	
}
