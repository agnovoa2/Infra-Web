package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.User;
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
			String login = generateLogin(getName(),getFirstSurname(),getSecondSurname());
			userGateway.create(new User(login,generateEmail(login),this.getPassword(), this.getName(), this.getFirstSurname(), this.getSecondSurname()));
			userGateway.save();
			context.redirect("index.xhtml");
		//} catch (EntityExistsException e) {
			this.setError("Ya existe ese usuario en la base de datos");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String generateLogin(String name, String firstSurname, String secondSurname) {
		String toRet;
		toRet = name.substring(0, 1);
		toRet += firstSurname.substring(0, 1);
		toRet += secondSurname;

		int i = 2;
		User previous = userGateway.find(toRet);
		if(previous != null){
			toRet += i;
			i++;
		} else {
			return toRet;
		}
			
		previous = userGateway.find(toRet);
		while(previous != null){
			toRet = toRet.substring(0, toRet.length()-1);
			toRet += i;
			i++;
			previous = userGateway.find(toRet);
		}
		return toRet;
	}

	private String generateEmail(String login){
		return login + "@esei.uvigo.es";
	}
}
