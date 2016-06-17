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
import es.uvigo.esei.infraestructura.entities.Material;

@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class MaterialGatewayBean {
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@EJB
	private UserAuthorizationEJB auth;

	private Material current;

	public Material find(int id) {
		this.current = this.em.find(Material.class, id);
		return this.current;
	}

	public Material getCurrent() {
		return current;
	}

	public void create(Material material) {
		this.em.persist(material);
		this.current = material;
	}

	public void remove(int id) {
		Material ref = this.em.getReference(Material.class, id);
		this.em.remove(ref);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void save() {
		// nothing to do
	}

	public List<Material> getAllMonitors() {
		return em.createNamedQuery("findAllMonitors", Material.class).getResultList();
	}

	public List<Material> getAllHardDrives() {
		return em.createNamedQuery("findAllHardDrive", Material.class).getResultList();
	}

	public List<Material> getAllRamMemories() {
		return em.createNamedQuery("findAllRamMemory", Material.class).getResultList();
	}

	public List<Material> getAllOthers() {
		return em.createNamedQuery("findAllOthers", Material.class).getResultList();
	}
	
	public List<Material> getAllLendableMonitors() {
		return em.createNamedQuery("findAllLendableMonitors", Material.class).getResultList();
	}

	public List<Material> getAllLendableHardDrives() {
		return em.createNamedQuery("findAllLendableHardDrive", Material.class).getResultList();
	}

	public List<Material> getAllLendableRamMemories() {
		return em.createNamedQuery("findAllLendableRamMemory", Material.class).getResultList();
	}

	public List<Material> getAllLendableOthers() {
		return em.createNamedQuery("findAllLendableOthers", Material.class).getResultList();
	}
}
