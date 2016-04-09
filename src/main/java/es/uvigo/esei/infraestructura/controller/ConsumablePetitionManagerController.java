package es.uvigo.esei.infraestructura.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.ConsumablePetition;
import es.uvigo.esei.infraestructura.facade.PetitionGatewayBean;

@ViewScoped
@ManagedBean(name = "consumablePetitionManagement")
public class ConsumablePetitionManagerController {
	
	@Inject
	PetitionGatewayBean petitionGateway;
	
	public List<ConsumablePetition> getAllPetitions(){
		return petitionGateway.getAllPetitions();
	}
}
