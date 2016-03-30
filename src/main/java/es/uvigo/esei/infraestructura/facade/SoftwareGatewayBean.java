package es.uvigo.esei.infraestructura.facade;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import es.uvigo.esei.infraestructura.ejb.UserAuthorizationEJB;
import es.uvigo.esei.infraestructura.entities.Software;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SoftwareGatewayBean {
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@EJB
    private UserAuthorizationEJB auth;
	
	private Software current;

	public Software find(String id) {
		this.current = this.em.find(Software.class, id);
		return this.current;
	}
	
	public Software getCurrent() {
        return current;
    }
	
	public void create(Software software){
        this.em.persist(software);
        this.current = software;
    }
	
	public void remove(String id){
		Software ref = this.em.getReference(Software.class, id);
        this.em.remove(ref);
    }
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void save(){
        //nothing to do
    }
}
