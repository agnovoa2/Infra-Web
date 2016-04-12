package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import es.uvigo.esei.infraestructura.ejb.UserAuthorizationEJB;
import es.uvigo.esei.infraestructura.entities.Role;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;

@RequestScoped
@ManagedBean(name = "loginController")
public class LoginController {

	@Inject
	private Principal currentUser;

	@Inject
	private UserAuthorizationEJB auth;

	@Inject
	private UserGatewayBean userGateway;

	private ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

	private String login;
	private String password;
	private boolean error;
	private String errorMessage;

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

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public void doLogin() throws IOException, ServletException {
		try {
			HttpServletRequest request = (HttpServletRequest) context.getRequest();
			diggestPassword(password);
			request.login(this.getLogin(), this.getPassword());
			this.error = false;
			this.userGateway.find(this.getLogin());
			if (auth.getCurrentUser().getRole().equals(Role.INTERN))
				FacesContext.getCurrentInstance().getExternalContext().redirect("zonaBecario.xhtml");
			else
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml?login=true");
		} catch (ServletException e) {
			this.error = true;
			this.errorMessage = "Login or password don't match";
			context.redirect("index.xhtml?login=error" + e.getMessage());
		}
	}

	public void doLogout() throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		request.logout();
		FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
	}

	public Principal getCurrentUser() {
		return this.currentUser;
	}

	public boolean isIntern() {
		return auth.getCurrentUser().getRole().equals(Role.INTERN);
	}

	public boolean isProfessor() {
		return auth.getCurrentUser().getRole().equals(Role.PROFESSOR);
	}

	public boolean isStudent() {
		return auth.getCurrentUser().getRole().equals(Role.STUDENT);
	}

	public boolean isAnonymous() {
		return "anonymous".equals(this.getCurrentUser().getName());
	}

	public void redirectIfAnonymous() throws IOException {
		if (this.isAnonymous())
			redirectToIndex();
	}

	public void redirectIfStudent() throws IOException {
		if (this.isStudent())
			redirectToIndex();
	}

	public void redirectIfNotIntern() throws IOException {
		if (!this.isIntern())
			redirectToIndex();
	}

	private void redirectToIndex() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
	}
	
	private void diggestPassword(String password){
		MessageDigest passwordDigester;
		HexBinaryAdapter adapter = new HexBinaryAdapter();
		try {
			passwordDigester = MessageDigest.getInstance("MD5");
			this.password = adapter.marshal(passwordDigester.digest(password.getBytes())).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
