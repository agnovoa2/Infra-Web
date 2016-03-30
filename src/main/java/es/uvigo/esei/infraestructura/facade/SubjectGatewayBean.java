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
import es.uvigo.esei.infraestructura.entities.Subject;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SubjectGatewayBean {
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@EJB
	private UserAuthorizationEJB auth;

	private Subject current;

	public Subject find(String id) {
		this.current = this.em.find(Subject.class, id);
		return this.current;
	}
	
	public Subject findByCode(String code){
    	Query query = em.createQuery("SELECT s FROM Subject s WHERE s.code =:code",Subject.class);
    	query.setParameter("code", code);
    	List<Subject> subjects = query.getResultList();
    	if(subjects.isEmpty())
    		return null;
    	this.current = subjects.get(0);
    	return this.current;
    }

	public Subject getCurrent() {
		return current;
	}

	public void create(Subject subject) {
		this.em.persist(subject);
		this.current = subject;
	}

	public void remove(String id) {
		Subject ref = this.em.getReference(Subject.class, id);
		this.em.remove(ref);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() {
		// nothing to do
	}
	
	public List<Subject> getAll(){
		return em.createNamedQuery("findAllSubjects", Subject.class).getResultList();
	}
}
