package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.ejb.UserEJB;
import es.uvigo.esei.infraestructura.entities.User;
import es.uvigo.esei.infraestructura.exception.RegisterException;

@RequestScoped
@ManagedBean(name = "signUpController")
public class SignUpController {

	@Inject
	private UserEJB userEJB;

	private ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	
	private String name;
	private String firstSurname;
	private String secondSurname;
	private String password;
	private String login;
	private String error;

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

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	public void doRegister() {
		try {
			userEJB.registerUser(new User(this.getPassword(), this.getName(), this.getFirstSurname(), this.getSecondSurname()));
			context.redirect("index.xhtml");
		} catch (RegisterException e) {
			this.setError(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
