package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.security.Principal;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.authorization.UserAuthorizationEJB;
import es.uvigo.esei.infraestructura.entities.Role;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;
import es.uvigo.esei.infraestructura.util.PasswordUtil;

@ViewScoped
@ManagedBean(name = "editProfile")
public class EditProfileController {

	@EJB
	private UserAuthorizationEJB auth;

	@Inject
	private Principal currentUser;

	@Inject
	private UserGatewayBean userGateway;

	private ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	
	private String login;
	private String name;
	private String mail;
	private String firstSurname;
	private String secondSurname;
	private String oldPassword;
	private String newPassword;
	private String ldap;

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
				if (PasswordUtil.diggestPassword(getOldPassword()).equals(userGateway.getCurrent().getPassword())) {
					userGateway.getCurrent().setPassword(getNewPassword());
				}
				else{
					context.redirect("editProfile.xhtml?login=" + login + "&error=true");
				}
			}
		} else {
			if (newPassword != null && !newPassword.equals("")) {
				userGateway.getCurrent().setPassword(getNewPassword());
			}
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

	public boolean isMe(){
		return login.equals(currentUser.getName());
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

	public String getLdap() {
		return ldap;
	}

	public void setLdap(String ldap) {
		this.ldap = ldap;
	}
}
