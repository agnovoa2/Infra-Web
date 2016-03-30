package es.uvigo.esei.infraestructura.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import es.uvigo.esei.infraestructura.ejb.UserAuthorizationEJB;
import es.uvigo.esei.infraestructura.entities.Printer;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class PrinterGatewayBean {
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@EJB
	private UserAuthorizationEJB auth;

	private Printer current;

	public Printer find(int id) {
		this.current = this.em.find(Printer.class, id);
		return this.current;
	}

	public Printer getCurrent() {
		return current;
	}

	public void create(Printer printer) {
		this.em.persist(printer);
		this.current = printer;
	}

	public void remove(int id) {
		Printer ref = this.em.getReference(Printer.class, id);
		this.em.remove(ref);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() {
		// nothing to do
	}
	
	public List<Printer> getAll(){
		return em.createNamedQuery("findAllPrinters", Printer.class).getResultList();
	}
}
