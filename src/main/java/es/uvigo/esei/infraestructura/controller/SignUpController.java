package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.User;
import es.uvigo.esei.infraestructura.exception.UserAlreadyExistsException;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;

@RequestScoped
@ManagedBean(name = "signUpController")
public class SignUpController {

	@Inject
	private UserGatewayBean userGateway;

	private ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	
	private String name;
	private String firstSurname;
	private String secondSurname;
	private String password;
	private String login;
	private String email;
	private boolean error;
	private String message;
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean getError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void doRegister() {
		try {
			userGateway.create(new User(login,email,this.getPassword(), this.getName(), this.getFirstSurname(), this.getSecondSurname()));
			userGateway.getCurrent().setBanned(true);
			userGateway.save();
			System.out.println("registrao");
			context.redirect("login.xhtml?register=true");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UserAlreadyExistsException e) {
			System.out.println("ya existe mandingo");
			message = "Ya existe ese usuario.";
			error = true;
		} catch (Exception e){
			System.out.println("ya existe mail");
			message = "Ya existe ese email.";
			error = true;
		}
	}
}
