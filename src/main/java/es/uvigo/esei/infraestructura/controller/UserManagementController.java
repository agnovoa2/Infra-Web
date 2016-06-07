package es.uvigo.esei.infraestructura.controller;

import java.security.Principal;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Role;
import es.uvigo.esei.infraestructura.entities.User;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;

@ViewScoped
@ManagedBean(name = "userManagement")
public class UserManagementController {
	
	@Inject
	private Principal currentUser;
	
	@Inject
	UserGatewayBean userGateway;
	
	public void doBanUnban(String login){
		userGateway.find(login);
		userGateway.getCurrent().setBanned(!userGateway.getCurrent().isBanned());
		userGateway.save();
	}
	
	public void doPromoteToProfessor(String login){
		userGateway.find(login);
		userGateway.getCurrent().setRole(Role.PROFESSOR);
		userGateway.save();
	}
	
	public void doPromoteToIntern(String login){
		userGateway.find(login);
		userGateway.getCurrent().setRole(Role.INTERN);
		userGateway.save();
	}
	
	public void doDegradeToStudent(String login){
		userGateway.find(login);
		userGateway.getCurrent().setRole(Role.STUDENT);
		userGateway.save();
	}
	
	public boolean isMe(String login){
		userGateway.find(login);
		return userGateway.getCurrent().getLogin().equals(currentUser.getName());
	}
	
	public boolean isUserBanned(String login){
		userGateway.find(login);
		return userGateway.getCurrent().isBanned();
	}
	
	public List<User> getAllStudents(){
		return userGateway.getAllStudents();
	}
	
	public List<User> getAllProfessors(){
		return userGateway.getAllProfessors();
	}
	
	public List<User> getAllInterns(){
		return userGateway.getAllInterns();
	}
}
