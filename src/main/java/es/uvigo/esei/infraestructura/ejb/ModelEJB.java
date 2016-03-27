package es.uvigo.esei.infraestructura.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uvigo.esei.infraestructura.entities.Model;

@Stateless
public class ModelEJB {

	@PersistenceContext
	EntityManager em;
	
	@EJB
	private UserAuthorizationEJB auth;
	
	@RolesAllowed({"INTERN","PROFESSOR"})
	public List<Model> getAllPrinterModels(){
		return em.createNamedQuery("findAllModels",Model.class).getResultList();
	}
	
	@RolesAllowed({"INTERN","PROFESSOR"})
	public Model findModel(String modelName){
		return em.find(Model.class, modelName);
	}
	
	@RolesAllowed({"INTERN","PROFESSOR"})
	public void addModel(Model model){
		em.persist(model);
	}
}
