package es.uvigo.esei.infraestructura.ejb;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uvigo.esei.infraestructura.entities.Consumable;

@Stateless
public class ConsumableEJB {

	@PersistenceContext
	EntityManager em;

	@EJB
	private UserAuthorizationEJB auth;

	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public Consumable find(String consumable){
		return em.find(Consumable.class, consumable);
	}
	
	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public void addConsumable(Consumable consumable){
		em.persist(consumable);
	}
	
	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<Consumable> findAllConsumables(){
		return em.createNamedQuery("findAllConsumables",Consumable.class).getResultList();
	}
	
	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<Consumable> findAllBlackConsumables(){
		return em.createNamedQuery("findAllBlackConsumables",Consumable.class).getResultList();
	}
	
	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<Consumable> findAllPhotoBlackConsumables(){
		return em.createNamedQuery("findAllPhotoBlackConsumables",Consumable.class).getResultList();
	}
	
	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<Consumable> findAllCyanConsumables(){
		return em.createNamedQuery("findAllCyanConsumables",Consumable.class).getResultList();
	}
	
	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<Consumable> findAllLightCyanConsumables(){
		return em.createNamedQuery("findAllLightCyanConsumables",Consumable.class).getResultList();
	}
	
	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<Consumable> findAllMagentaConsumables(){
		return em.createNamedQuery("findAllMagentaConsumables",Consumable.class).getResultList();
	}
	
	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<Consumable> findAllLightMagentaConsumables(){
		return em.createNamedQuery("findAllLightMagentaConsumables",Consumable.class).getResultList();
	}
	
	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<Consumable> findAllYellowConsumables(){
		return em.createNamedQuery("findAllYellowConsumables",Consumable.class).getResultList();
	}
	
	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<Consumable> findAllTricolorConsumables(){
		return em.createNamedQuery("findAllTricolorConsumables",Consumable.class).getResultList();
	}
	
	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<Consumable> findAllDrumConsumables(){
		return em.createNamedQuery("findAllDrumConsumables",Consumable.class).getResultList();
	}
	
	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<Consumable> findAllFuserConsumables(){
		return em.createNamedQuery("findAllFuserConsumables",Consumable.class).getResultList();
	}
	
	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<Consumable> findAllBeltUnitConsumables(){
		return em.createNamedQuery("findAllBeltUnitConsumables",Consumable.class).getResultList();
	}
	
	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<Consumable> findAllGarbageUnitConsumables(){
		return em.createNamedQuery("findAllGarbageUnitConsumables",Consumable.class).getResultList();
	}
	
	@RolesAllowed({ "INTERN", "PROFESSOR" })
	public List<Consumable> findAllTransferKitConsumables(){
		return em.createNamedQuery("findAllTransferKitConsumables",Consumable.class).getResultList();
	}
}
