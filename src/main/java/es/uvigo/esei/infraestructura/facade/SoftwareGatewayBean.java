package es.uvigo.esei.infraestructura.facade;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import es.uvigo.esei.infraestructura.authorization.UserAuthorizationEJB;
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
		Query query = em.createQuery("SELECT s FROM Software s WHERE s.softwareName=:softwareName",Software.class);
		query.setParameter("softwareName", id);
		if(query.getResultList().size() == 0){
			this.current = null;
			return null;
		}
		this.current = (Software) query.getResultList().get(0);
		return current;
	}
	
	public Software getCurrent() {
        return current;
    }
	
	public void create(Software software) throws SQLException{
		if (this.find(software.getSoftwareName()) == null) {
			this.em.persist(software);
			this.current = software;
		}
		else{
			throw new SQLException("Ya existe ese software en la base de datos.");
		}
    }
	
	public void remove(int id){
		Software ref = this.em.getReference(Software.class, id);
        this.em.remove(ref);
    }
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void save(){
        //nothing to do
    }
	
	public List<Software> getAll(){
		return em.createNamedQuery("findAllSoftware", Software.class).getResultList();
	}
	
	public List<Software> getAllProgram(){
		return em.createNamedQuery("findAllSoftwareProgram", Software.class).getResultList();
	}
	
	public List<Software> getAllOperativeSystem(){
		return em.createNamedQuery("findAllSoftwareOperativeSystem", Software.class).getResultList();
	}
}
