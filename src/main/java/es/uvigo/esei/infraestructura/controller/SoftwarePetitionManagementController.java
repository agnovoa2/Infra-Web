package es.uvigo.esei.infraestructura.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Subject;
import es.uvigo.esei.infraestructura.entities.User;
import es.uvigo.esei.infraestructura.facade.SubjectGatewayBean;
import es.uvigo.esei.infraestructura.util.Email;

@ViewScoped
@ManagedBean(name = "softwarePetitionManagement")
public class SoftwarePetitionManagementController {
	
	@Inject
	Email mail;
	
	@Inject
	SubjectGatewayBean subjectGateway;
	
	private String textMessage;
	
	public List<Subject> getAllPetitions(){
		return subjectGateway.getAllPetitions();
	}
	
	public List<Subject> getNoPetitions(){
		List<Subject> subjects = subjectGateway.getAll();
		List<Subject> subjectWithPetition = getAllPetitions();
		subjects.removeAll(subjectWithPetition);
		return subjects;
	}
	
	public void doConfirmPetition(String code){
		subjectGateway.findByCode(code);
		subjectGateway.getCurrent().setPetitionState(2);
		subjectGateway.save();
		setText(subjectGateway.getCurrent());
		for(User user: subjectGateway.getCurrent().getUsers()){
			mail.sendEmail(getText(), "[Infraestructura] Confirmada la petición de software para la asignatura" + subjectGateway.getCurrent().getSubjectName(), user.getEmail());
		}
	}

	public String getText() {
		return textMessage;
	}

	public void setText(Subject subject) {
		textMessage = ("Este es un mensaje autogenerado de la aplicación [Futuro nombre aqui]\n" 
				+ "\n"
				+ "Se ha realizado la instalación del software necesario para la asignatura " + subject.getSubjectName() + ".\n"
				+ "Si lo considera oportuno, póngase en contacto con el equipo de infraestructura para comprobar que el software se ha instalado de acuerdo a las necesidades de dicha asignatura.\n" 
				+ "Un saludo.");
	}
}
