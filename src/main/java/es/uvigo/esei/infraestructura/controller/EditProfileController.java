package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.security.Principal;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.ejb.UserAuthorizationEJB;
import es.uvigo.esei.infraestructura.entities.Role;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;

@ViewScoped
@ManagedBean(name = "editProfile")
public class EditProfileController {

	@EJB
	private UserAuthorizationEJB auth;

	@Inject
	private Principal currentUser;

	@Inject
	private UserGatewayBean userGateway;

	private String login;
	private String name;
	private String mail;
	private String firstSurname;
	private String secondSurname;
	private String oldPassword;
	private String newPassword;

	public void init() {
		userGateway.find(getLogin());
		name = userGateway.getCurrent().getName();
		firstSurname = userGateway.getCurrent().getFirstSurname();
		secondSurname = userGateway.getCurrent().getSecondSurname();
		mail = userGateway.getCurrent().getEmail();
	}

	public void doEdit() throws IOException {
		userGateway.find(getLogin());
		userGateway.getCurrent().setName(getName());
		userGateway.getCurrent().setFirstSurname(getFirstSurname());
		userGateway.getCurrent().setSecondSurname(getSecondSurname());
		userGateway.getCurrent().setEmail(getMail());
		if (!auth.getCurrentUser().getRole().equals(Role.INTERN)) {
			if (getOldPassword() != null && !getOldPassword().equals("")) {
				if (getOldPassword().equals(userGateway.getCurrent().getPassword())) {
					userGateway.getCurrent().setPassword(getNewPassword());
				}
			}
		}
		else{
			userGateway.getCurrent().setPassword(getNewPassword());
		}
		userGateway.save();
		FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
	}

	public void redirectIfCantEdit(String login) throws IOException {
		userGateway.find(currentUser.getName());
		if (login != null) {
			if (!(userGateway.getCurrent().getRole().equals(Role.INTERN)
					|| userGateway.getCurrent().getLogin().equals(login)))
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		}
	}

	public boolean isIntern() {
		userGateway.find(currentUser.getName());
		if (userGateway.getCurrent().getRole().equals(Role.INTERN))
			return true;
		return false;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
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

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getFullName() {
		return getName() + " " + getFirstSurname() + " " + getSecondSurname();
	}
}
