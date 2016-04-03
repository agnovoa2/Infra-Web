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
import es.uvigo.esei.infraestructura.entities.Computer;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ComputerGatewayBean {
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@EJB
	private UserAuthorizationEJB auth;

	private Computer current;

	public Computer find(int num, String laboratory) {
		
		Query query = em.createQuery("Select c From Computer c Where c.laboratory = :laboratory AND c.num = :num", Computer.class);
		query.setParameter("laboratory", laboratory);
		query.setParameter("num", num);
		if(query.getResultList().size() == 0){
			this.current = null;
			return null;
		}
		this.current = (Computer) query.getResultList().get(0);
		return current;
	}

	public Computer getCurrent() {
		return current;
	}

	public void create(Computer computer) {
		this.em.persist(computer);
		this.current = computer;
	}

	public void remove(int id) {
		Computer ref = this.em.getReference(Computer.class, id);
		this.em.remove(ref);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() {
		// nothing to do
	}
	
	public List<Computer> getAll(){
		return em.createNamedQuery("findAllComputers", Computer.class).getResultList();
	}
	
	public List<Computer> getAllLaboratoryComputers(String laboratory){
		Query query = em.createQuery("Select c From Computer c Where c.laboratory = :laboratory AND c.num = 0", Computer.class);
		query.setParameter("laboratory", laboratory);
		return query.getResultList();
	}
}
