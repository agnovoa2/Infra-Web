package es.uvigo.esei.infraestructura.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import es.uvigo.esei.infraestructura.authorization.UserAuthorizationEJB;
import es.uvigo.esei.infraestructura.entities.User;
import es.uvigo.esei.infraestructura.exception.UserAlreadyExistsException;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class UserGatewayBean {
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@EJB
	private UserAuthorizationEJB auth;

	private User current;

	public User find(String id) {
		this.current = this.em.find(User.class, id);
		return this.current;
	}

	public User getCurrent() {
		return current;
	}

	public void create(User user) throws UserAlreadyExistsException {
		if (this.find(user.getLogin()) == null) {
			this.em.persist(user);
			this.current = user;
		}
		else{
			throw new UserAlreadyExistsException("Ya existe este login en la base de datos.");
		}
	}

	public void remove(String id) {
		User ref = this.em.getReference(User.class, id);
		this.em.remove(ref);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() {
		// nothing to do
	}
	
	public List<User> getAllStudents(){
		return em.createNamedQuery("findAllStudents", User.class).getResultList();
	}
	
	public List<User> getAllProfessors(){
		return em.createNamedQuery("findAllProfessors", User.class).getResultList();
	}
	
	public List<User> getAllInterns(){
		return em.createNamedQuery("findAllInterns", User.class).getResultList();
	}
}
