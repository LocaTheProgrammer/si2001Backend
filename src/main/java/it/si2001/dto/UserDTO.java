package it.si2001.dto;

import org.springframework.beans.BeanUtils;

import it.si2001.entity.User;



public class UserDTO {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String role;
	private int id;

//	private int idReservationList;
	
	public static UserDTO build(User u) {

		UserDTO result = new UserDTO();
		BeanUtils.copyProperties(u, result);

		return result;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

//	public int getIdReservationList() {
//		return idReservationList;
//	}
//
//	public void setIdReservationList(int idReservationList) {
//		this.idReservationList = idReservationList;
//	}

	@Override
	public String toString() {
		return "UserDTO{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", role='" + role + '\'' +
				", id=" + id +
//				", idReservationList=" + idReservationList +
				'}';
	}
}
