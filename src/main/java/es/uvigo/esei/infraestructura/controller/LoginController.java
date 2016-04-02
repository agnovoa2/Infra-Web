package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.itextpdf.text.DocumentException;

import es.uvigo.esei.infraestructura.ejb.UserAuthorizationEJB;
import es.uvigo.esei.infraestructura.entities.Consumable;
import es.uvigo.esei.infraestructura.entities.Model;
import es.uvigo.esei.infraestructura.entities.Petition;
import es.uvigo.esei.infraestructura.entities.PetitionRow;
import es.uvigo.esei.infraestructura.entities.Printer;
import es.uvigo.esei.infraestructura.entities.Role;
import es.uvigo.esei.infraestructura.entities.User;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;
import es.uvigo.esei.infraestructura.util.Report;

@RequestScoped
@ManagedBean(name = "loginController")
public class LoginController {
	
	@Inject
	private Principal currentUser;
	
	@Inject
	private UserAuthorizationEJB auth;
	
	@Inject
	private UserGatewayBean userGateway;
	
	@Inject
	private Report report;
	
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
			HttpServletRequest request = (HttpServletRequest) context
					.getRequest();
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
			context.redirect("index.xhtml?login=error"+e.getMessage());
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
	//TO ERASE
	public void doPdf() throws DocumentException, MalformedURLException, IOException{
		User user = new User("asd","Alejandro","Gutiérrez","Novoa");
		Model model = new Model("Oki model", "Oki");
		Printer printer = new Printer(12345,"Despacho 401");
		Petition petition = new Petition(1, printer, null, user);
		Consumable consumable = new Consumable("Toner Negro","Toner","Negro","Toner negro para oki");
		Consumable consumable2 = new Consumable("Cartucho Negro","Toner","Negro","Toner negro para oki");
		PetitionRow petitionRow = new PetitionRow(petition, consumable, 2);
		PetitionRow petitionRow2 = new PetitionRow(petition, consumable2, 1);
		printer.setModel(model);
		petition.setUser(user);
		petitionRow.setConsumable(consumable);
		List<PetitionRow> petitionRows = new LinkedList<PetitionRow>();
		petitionRows.add(petitionRow);
		petitionRows.add(petitionRow2);
		petition.setPetitionRows(petitionRows);
		report.doRetrievePDF(petition);
	}
	
	private void redirectToIndex() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
	}
}
