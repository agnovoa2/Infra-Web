package es.uvigo.esei.infraestructura.controller;

import java.security.Principal;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.ConsumablePetition;
import es.uvigo.esei.infraestructura.entities.ConsumablePetitionRow;
import es.uvigo.esei.infraestructura.entities.Incidence;
import es.uvigo.esei.infraestructura.entities.MaterialPetition;
import es.uvigo.esei.infraestructura.entities.MaterialPetitionRow;
import es.uvigo.esei.infraestructura.entities.Subject;
import es.uvigo.esei.infraestructura.facade.ConsumablePetitionGatewayBean;
import es.uvigo.esei.infraestructura.facade.ConsumablePetitionRowGatewayBean;
import es.uvigo.esei.infraestructura.facade.IncidenceGatewayBean;
import es.uvigo.esei.infraestructura.facade.MaterialGatewayBean;
import es.uvigo.esei.infraestructura.facade.MaterialPetitionGatewayBean;
import es.uvigo.esei.infraestructura.facade.MaterialPetitionRowGatewayBean;
import es.uvigo.esei.infraestructura.facade.SubjectGatewayBean;

@ViewScoped
@ManagedBean(name = "index")
public class IndexController {
	
	@Inject
	private Principal currentUser;
	
	@Inject
	private SubjectGatewayBean subjectGateway;

	@Inject
	private IncidenceGatewayBean incidenceGateway;

	@Inject
	private ConsumablePetitionGatewayBean consumablePetitionGateway;
	
	@Inject
	private ConsumablePetitionRowGatewayBean consumablePetitionRowGateway;
	
	@Inject
	private MaterialPetitionGatewayBean materialPetitionGateway;
	
	@Inject
	private MaterialPetitionRowGatewayBean materialPetitionRowGateway;
	
	@Inject
	private MaterialGatewayBean materialGateway;
	
	public void doRemoveConsumablePetition(int id){
		consumablePetitionGateway.find(id);
		for (ConsumablePetitionRow petitionRow: consumablePetitionGateway.getCurrent().getPetitionRows()) {
			consumablePetitionRowGateway.remove(petitionRow.getId());
			consumablePetitionRowGateway.save();
		}
		consumablePetitionGateway.getCurrent().setPetitionRows(null);
		consumablePetitionGateway.remove(id);
		consumablePetitionGateway.save();
	}
	
	public void doRemovematerialPetition(int id){
		materialPetitionGateway.find(id);
		for (MaterialPetitionRow petitionRow: materialPetitionGateway.getCurrent().getPetitionRows()) {
			materialGateway.find(petitionRow.getMaterial().getId());
			materialGateway.getCurrent().setQuantity(materialGateway.getCurrent().getQuantity() + petitionRow.getQuantity());
			materialGateway.save();
			materialPetitionRowGateway.remove(petitionRow.getId());
			materialPetitionRowGateway.save();
		}
		materialPetitionGateway.getCurrent().setPetitionRows(null);
		materialPetitionGateway.remove(id);
		materialPetitionGateway.save();
	}
	
	public List<Incidence> getAllIncidences(){
		return incidenceGateway.getAllUnsolvedIncidences();
	}
	
	public List<Subject> getAllSoftwarePetitions(){
		return subjectGateway.getAllUnsolvedPetitions();
	}
	
	public List<MaterialPetition> getAllMaterialPetitions(){
		return materialPetitionGateway.getAllPetitions();
	}
	
	public List<ConsumablePetition> getAllConsumablePetitions(){
		return consumablePetitionGateway.getAllPetitions();
	}
	
	public List<ConsumablePetition> getMyConsumablePetitions(){
		return consumablePetitionGateway.getAllUserConsumablePetitions(currentUser.getName());
	}
	
	public List<MaterialPetition> getMyMaterialPetitions(){
		return materialPetitionGateway.getAllUserMaterialPetitions(currentUser.getName());
	}
}
