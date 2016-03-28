package es.uvigo.esei.infraestructura.ejb;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uvigo.esei.infraestructura.entities.Printer;
import es.uvigo.esei.infraestructura.entities.Subject;
import es.uvigo.esei.infraestructura.entities.User;
import es.uvigo.esei.infraestructura.exception.RegisterException;
import es.uvigo.esei.infraestructura.exception.RemoveUserException;

@Stateless
public class UserEJB {
	 
	@PersistenceContext
	private EntityManager em;
	
	@PermitAll
	public void registerUser(User user) throws RegisterException{
		//search users in db for avoid duplicates
		String login = generateLogin(user.getName(), user.getFirstSurname(), user.getSecondSurname());
		user.setLogin(login);
		if(findUserByLogin(login) != null){
			throw new RegisterException("Duplicated login");
		}
		user.setLogin(login);
		user.setEmail(generateEmail(login));
		
		em.persist(user);
	}
	
	@RolesAllowed("INTERN")
	public void removeUser(User user) throws RemoveUserException{
		//search users in db for avoid
		if(findUserByLogin(user.getLogin()) == null){
			throw new RemoveUserException("User doesn't exist in the database");
		}
		em.remove(user);
	}
	
	@PermitAll
	public User findUserByLogin(String login) {
		return em.find(User.class, login);
	}
	
	//Generates the login for the user automatically
	@PermitAll
	public String generateLogin(String name, String firstSurname, String secondSurname) {
		String toRet;
		toRet = name.substring(0, 1);
		toRet += firstSurname.substring(0, 1);
		toRet += secondSurname;

		int i = 2;
		User previous = findUserByLogin(toRet);
		if(previous != null){
			toRet += i;
			i++;
		} else {
			return toRet;
		}
			
		previous = findUserByLogin(toRet);
		while(previous != null){
			toRet = toRet.substring(0, toRet.length()-1);
			toRet += i;
			i++;
			previous = findUserByLogin(toRet);
		}
		return toRet;
	}
	
	@PermitAll
	public String generateEmail(String login){
		return login + "@esei.uvigo.es";
	}
	

    @RolesAllowed({ "INTERN", "PROFESSOR"})
	public void assignSubjectToProfessor(String login, String subjectName)  {
    	Subject subject = em.find(Subject.class, subjectName);
    	User user = em.find(User.class, login);
    	List<Subject> subjectList = new LinkedList<Subject>();
    	subjectList.add(subject);
    	if(user.getSubjects().isEmpty())
    		user.setSubjects(subjectList);
    	else
    		user.getSubjects().add(subject);
    	em.merge(user);
	}
    
    @RolesAllowed({ "INTERN", "PROFESSOR"})
	public void removeSubjectFromProfessor(String login, String subjectName)  {
    	Subject subject = em.find(Subject.class, subjectName);
    	User user = em.find(User.class, login);
    	if(!user.getSubjects().isEmpty())
    		user.getSubjects().remove(subject);
       	em.merge(user);
	}
    
    @RolesAllowed({ "INTERN", "PROFESSOR"})
	public void assignPrinterToProfessor(String login, Printer printer)  {
    	User user = findUserByLogin(login);
    	user.getPrinters().add(printer);
    	em.merge(user);
    }
    
    @RolesAllowed({ "INTERN", "PROFESSOR"})
	public void removePrinterFromProfessor(String login, Printer printer)  {
    	User user = findUserByLogin(login);
    	user.getPrinters().remove(printer);
    	em.merge(user);
    }
}
