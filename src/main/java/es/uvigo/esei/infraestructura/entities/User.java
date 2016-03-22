package es.uvigo.esei.infraestructura.entities;

import javax.faces.bean.SessionScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@SessionScoped
public class User {
	
	//Regex for the validation of md5 passwords
	
	
	@Id
	@Column(length = 45)
	private String login;

	@Column(length = 32, nullable = false)
	private String password;
	
	@Column(length = 45, nullable = false)
	private String name;
	
	@Column(length = 45, nullable = false)
	private String firstSurname;
	
	@Column(length = 45, nullable = true)
	private String secondSurname;
	
	@Column(length = 8, nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Column(length = 60, nullable = false, unique=true)
	private String email;
	
	@Column
	private boolean authorized;
	
	//Constructor required for JPA framework
	User() {}
	
	//Constructor required for user registration
	public User(String password, String name, String firstSurname, String secondSurname) {
		this.password = password;
		this.name = name;
		this.firstSurname = firstSurname;
		this.secondSurname = secondSurname;
		this.role = Role.ALUMNO;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstSurname() {
		return firstSurname;
	}

	public void setFirstSurname(String firstSurname) {
		this.firstSurname = firstSurname;
	}

	public String getSecondSurname() {
		return secondSurname;
	}

	public void setSecondSurname(String secondSurname) {
		this.secondSurname = secondSurname;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(String role) {
		switch (role){
			case "Becario": 
				this.role = Role.BECARIO;
				break;
			case "Profesor": 
				this.role = Role.PROFESOR;
				break;
			case "Alumno": 
				this.role = Role.ALUMNO;
				break;
			default : break;
		}
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}
}
