package es.uvigo.esei.infraestructura.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import es.uvigo.esei.infraestructura.ejb.UserAuthorizationEJB;
import es.uvigo.esei.infraestructura.entities.ConsumablePetition;
import es.uvigo.esei.infraestructura.entities.MaterialPetition;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class MaterialPetitionGatewayBean {
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@EJB
	private UserAuthorizationEJB auth;

	private MaterialPetition current;

	public MaterialPetition find(int id) {
		this.current = this.em.find(MaterialPetition.class, id);
		return this.current;
	}

	public MaterialPetition getCurrent() {
		return current;
	}

	public void create(MaterialPetition petition) {
		this.em.persist(petition);
		this.current = petition;
	}

	public void remove(int id) {
		MaterialPetition ref = this.em.getReference(MaterialPetition.class, id);
		this.em.remove(ref);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() {
		// nothing to do
	}
	
	public List<MaterialPetition> getAllPetitions(){
		return em.createNamedQuery("findAllMaterialPetitions", MaterialPetition.class).getResultList();
	}
	
	public List<MaterialPetition> getAllDonePetitions(){
		return em.createNamedQuery("findAllDoneMaterialPetitions", MaterialPetition.class).getResultList();
	}
	
	public List<MaterialPetition> getAllRetirevedPetitions(){
		return em.createNamedQuery("findAllRetrievedMaterialPetitions", MaterialPetition.class).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<MaterialPetition> getAllUserMaterialPetitions(String login){
		Query query = em.createQuery("Select m From MaterialPetition m Where m.user.login = :login and m.petitionState = 0", MaterialPetition.class);
		query.setParameter("login", login);
		return query.getResultList();
	}
}
