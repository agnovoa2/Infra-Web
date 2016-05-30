package es.uvigo.esei.infraestructura.facade;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import es.uvigo.esei.infraestructura.ejb.UserAuthorizationEJB;
import es.uvigo.esei.infraestructura.entities.MaterialPetitionRow;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class MaterialPetitionRowGatewayBean {
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@EJB
	private UserAuthorizationEJB auth;

	private MaterialPetitionRow current;

	public MaterialPetitionRow find(int id) {
		this.current = this.em.find(MaterialPetitionRow.class, id);
		return this.current;
	}

	public MaterialPetitionRow getCurrent() {
		return current;
	}

	public void create(MaterialPetitionRow materialPetitionRow) {
		this.em.persist(materialPetitionRow);
		this.current = materialPetitionRow;
	}

	public void remove(int id) {
		MaterialPetitionRow ref = this.em.getReference(MaterialPetitionRow.class, id);
		this.em.remove(ref);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() {
		// nothing to do
	}
}
