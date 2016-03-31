package es.uvigo.esei.infraestructura.facade;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import es.uvigo.esei.infraestructura.ejb.UserAuthorizationEJB;
import es.uvigo.esei.infraestructura.entities.Configuration;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ConfigurationGatewayBean {
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@EJB
	private UserAuthorizationEJB auth;

	private Configuration current;

	public Configuration find() {
		this.current = this.em.find(Configuration.class, 1);
		return this.current;
	}

	public Configuration getCurrent() {
		return current;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() {
		// nothing to do
	}
}
