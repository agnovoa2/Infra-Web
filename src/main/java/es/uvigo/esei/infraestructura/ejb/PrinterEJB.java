package es.uvigo.esei.infraestructura.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.uvigo.esei.infraestructura.entities.Model;
import es.uvigo.esei.infraestructura.entities.Printer;
import es.uvigo.esei.infraestructura.entities.User;

@Stateless
public class PrinterEJB {
	
	@PersistenceContext
	private EntityManager em;
	
	@EJB
	private UserAuthorizationEJB auth;
	
	@RolesAllowed({"INTERN","PROFESSOR"})
	public List<Printer> getAllProfessorPrinters(User user){
		Query query = em.createQuery("SELECT p FROM Printer p JOIN p.users u WHERE u.login = :login",Printer.class);
		query.setParameter("login", user.getLogin());
		return query.getResultList();
	}
	
	@RolesAllowed({"INTERN","PROFESSOR"})
	public void addPrinter(Printer printer, Model model){
		printer.setModel(model);
		em.persist(printer);
	}
	
	@RolesAllowed({"INTERN","PROFESSOR"})
	public Printer findPrinter(int inventoryNumber){
		return em.find(Printer.class, inventoryNumber);
	}
}