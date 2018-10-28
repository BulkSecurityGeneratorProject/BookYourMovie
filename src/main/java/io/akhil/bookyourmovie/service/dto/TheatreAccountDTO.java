package io.akhil.bookyourmovie.service.dto;

import java.io.Serializable;

/**
 * A DTO for the Theatre entity.
 */
public class TheatreAccountDTO implements Serializable {

	TheatreDTO theatre;
	
	UserDTO user;
	
	String password;

	public TheatreDTO getTheatre() {
		return theatre;
	}

	public void setTheatre(TheatreDTO theatre) {
		this.theatre = theatre;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
