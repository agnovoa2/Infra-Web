package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Computer;
import es.uvigo.esei.infraestructura.entities.Incidence;
import es.uvigo.esei.infraestructura.entities.IncidenceType;
import es.uvigo.esei.infraestructura.entities.State;
import es.uvigo.esei.infraestructura.facade.ComputerGatewayBean;
import es.uvigo.esei.infraestructura.facade.IncidenceGatewayBean;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;
import es.uvigo.esei.infraestructura.util.Mail;

@ViewScoped
@ManagedBean(name = "incidences")
public class IncidencesController {

	@Inject
	private Principal currentUser;

	@Inject
	private ComputerGatewayBean computerGateway;

	@Inject
	private IncidenceGatewayBean incidenceGateway;

	@Inject
	private UserGatewayBean userGateway;

	@Inject
	private Mail mail;

	private int computerNum;
	private int labelNum;
	private Integer[] auxArray;
	private String[] types;
	private String laboratory;
	private String description;
	private String textMessage;

	public void initLists() {
		switch (laboratory.toLowerCase()) {
		case "libre acceso":
			fillArray(48);
			break;
		case "s01":
			fillArray(29);
			break;
		case "s02":
			fillArray(29);
			break;
		case "s03":
			fillArray(29);
			break;
		case "s04":
			fillArray(29);
			break;
		case "s05":
			fillArray(29);
			break;
		case "s06":
			fillArray(29);
			break;
		case "aula 2.1":
			fillArray(1);
			break;
		case "aula 2.2":
			fillArray(1);
			break;
		case "aula 3.1":
			fillArray(1);
			break;
		case "aula 3.2":
			fillArray(1);
			break;
		// TODO faltan mirar los labs 37 38 39 30A 30B 31A
		}

	}

	public void doSelectComputer(int num) {
		this.computerNum = num;
		System.out.println(this.computerNum);
		this.computerGateway.find(getComputerNum(), getLaboratory());
	}

	public void doAddComputer() {
		System.out.println(this.labelNum);
		this.computerGateway.create(new Computer(getLaboratory(), getComputerNum(), getLabelNum()));
		this.computerGateway.save();
	}

	public void doRemoveComputer() {
		this.computerGateway.find(getComputerNum(), getLaboratory());
		this.computerGateway.remove(this.computerGateway.getCurrent().getId());
		this.computerGateway.save();
	}

	public void doAddIncidence() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		dateFormat.format(date);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		Incidence incidence = new Incidence(this.getDescription(), sqlDate);
		this.userGateway.find(currentUser.getName());
		this.computerGateway.find(getComputerNum(), getLaboratory());
		incidence.setUser(this.userGateway.getCurrent());
		incidence.setComputer(this.computerGateway.getCurrent());
		List<IncidenceType> typeList = new LinkedList<IncidenceType>();
		for (String incidenceType : this.types) {
			typeList.add(new IncidenceType(incidenceType));
		}
		incidence.setTypes(typeList);
		this.incidenceGateway.create(incidence);
		if (this.userGateway.getCurrent().getIncidences() == null) {
			this.userGateway.getCurrent().setIncidences(new LinkedList<Incidence>());
		}
		this.userGateway.getCurrent().getIncidences().add(incidence);
		if (this.computerGateway.getCurrent().getIncidences() == null) {
			this.computerGateway.getCurrent().setIncidences(new LinkedList<Incidence>());
		}
		this.computerGateway.getCurrent().getIncidences().add(incidence);
		this.computerGateway.getCurrent().setState(State.INCIDENCE);
		this.incidenceGateway.save();
		this.userGateway.save();
		this.computerGateway.save();
		this.setTextMessage();
		this.mail.sendMail(this.getTextMessage(), "[Infraestructura] Nueva incidencia en " + getLaboratory());
	}

	private void fillArray(int c) {
		this.auxArray = new Integer[c];
		for (int i = 0; i < c; i++) {
			auxArray[i] = i;
		}
	}

	public boolean isNoPc(int num) {
		this.computerGateway.find(num, getLaboratory());
		if (this.computerGateway.getCurrent() == null) {
			return true;
		}
		return false;
	}

	public boolean isNoPc() {
		this.computerGateway.find(getComputerNum(), getLaboratory());
		if (this.computerGateway.getCurrent() == null) {
			return true;
		}
		return false;
	}

	public boolean isPcOk(int num) {
		this.computerGateway.find(num, getLaboratory());
		if (this.computerGateway.getCurrent() != null && this.computerGateway.getCurrent().getState() == State.OK)
			return true;
		return false;
	}

	public boolean isPcRepair(int num) {
		this.computerGateway.find(num, getLaboratory());
		if (this.computerGateway.getCurrent() != null
				&& this.computerGateway.getCurrent().getState() == State.UNDER_REPAIR)
			return true;
		return false;
	}

	public boolean isPcRepair() {
		this.computerGateway.find(getComputerNum(), getLaboratory());
		if (this.computerGateway.getCurrent() != null
				&& this.computerGateway.getCurrent().getState() == State.UNDER_REPAIR)
			return true;
		return false;
	}

	public boolean isHasIncidence(int num) {
		this.computerGateway.find(num, getLaboratory());
		if (this.computerGateway.getCurrent() != null
				&& this.computerGateway.getCurrent().getState() == State.INCIDENCE)
			return true;
		return false;
	}

	public boolean isHasIncidence() {
		this.computerGateway.find(getComputerNum(), getLaboratory());
		if (this.computerGateway.getCurrent() != null
				&& this.computerGateway.getCurrent().getState() == State.INCIDENCE)
			return true;
		return false;
	}

	public void redirectIfNotLaboratory() throws IOException {
		if (!this.laboratory.toLowerCase().equals("libre acceso") && !this.laboratory.toLowerCase().equals("s01")
				&& !this.laboratory.toLowerCase().equals("s02") && !this.laboratory.toLowerCase().equals("s03")
				&& !this.laboratory.toLowerCase().equals("s04") && !this.laboratory.toLowerCase().equals("s05")
				&& !this.laboratory.toLowerCase().equals("s06") && !this.laboratory.toLowerCase().equals("aula 2.1")
				&& !this.laboratory.toLowerCase().equals("aula 2.2")
				&& !this.laboratory.toLowerCase().equals("aula 3.1")
				&& !this.laboratory.toLowerCase().equals("aula 3.2")
				&& !this.laboratory.toLowerCase().equals("laboratorio 30a")
				&& !this.laboratory.toLowerCase().equals("laboratorio 30b")
				&& !this.laboratory.toLowerCase().equals("laboratorio 31a")
				&& !this.laboratory.toLowerCase().equals("laboratorio 37")
				&& !this.laboratory.toLowerCase().equals("laboratorio 38")
				&& !this.laboratory.toLowerCase().equals("laboratorio 39")) {
			this.redirectToIndex();
		}
	}

	public void setTextMessage() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		dateFormat.format(date);

		this.textMessage = ("Este es un mensaje autogenerado de la aplicación [Futuro nombre aqui]\n" + "\n"
				+ "El usuario " + userGateway.getCurrent().getName() + " " + userGateway.getCurrent().getFirstSurname()
				+ " " + userGateway.getCurrent().getSecondSurname() + " ha realizado a fecha de "
				+ new java.sql.Date(date.getTime()) + " la siguiente incidencia en el ordenador número " + this.labelNum
				+ " de " + this.getLaboratory() + "\n" + "Categorías: ");
		for (IncidenceType type : this.incidenceGateway.getCurrent().getTypes()) {
			this.textMessage += (type.getType() + " ");
		}
		String description = this.incidenceGateway.getCurrent().getDescription();
		if (description != null && !description.equals("")) {
			this.textMessage += ("\n" + "Descripción: " + description);
		}
	}

	public void doSendComputerToRepair() {
		this.computerGateway.find(getComputerNum(), getLaboratory());
		List<Incidence> incidences = this.computerGateway.getCurrent().getIncidences();
		Incidence incidence = null;
		if (incidences != null) {
			for (int i = 0; i < incidences.size(); i++) {
				incidence = incidences.get(i);
				System.out.println("incidences id: " + incidence.getId());
				if (incidence.getState() == 0)
					break;
			}
		}
		if (incidence != null) {
			System.out.println("incidence id: " + incidence.getId());
			this.incidenceGateway.find(incidence.getId());
			this.incidenceGateway.getCurrent().setState(1);
			this.incidenceGateway.save();
		}
		this.computerGateway.getCurrent().setState(State.UNDER_REPAIR);
		this.computerGateway.save();
	}

	public void doFinishIncidence() {
		this.computerGateway.find(getComputerNum(), getLaboratory());
		List<Incidence> incidences = this.computerGateway.getCurrent().getIncidences();
		Incidence incidence = null;
		if (incidences != null) {
			for (int i = 0; i < incidences.size(); i++) {
				incidence = incidences.get(i);
				if (incidence.getState() == 1 || incidence.getState() == 0)
					break;
			}
		}
		if (incidence != null) {
			this.incidenceGateway.find(incidence.getId());
			this.incidenceGateway.getCurrent().setState(2);
			this.incidenceGateway.save();
		}
		this.computerGateway.getCurrent().setState(State.OK);
		this.computerGateway.save();
	}

	private void redirectToIndex() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
	}

	public int getComputerNum() {
		return computerNum;
	}

	public void setComputerNum(int computerNum) {
		this.computerNum = computerNum;
	}

	public Integer[] getAuxArray() {
		return auxArray;
	}

	public void setAuxArray(Integer[] auxArray) {
		this.auxArray = auxArray;
	}

	public String getLaboratory() {
		return laboratory;
	}

	public void setLaboratory(String laboratory) {
		this.laboratory = laboratory;
	}

	public int getLabelNum() {
		return labelNum;
	}

	public void setLabelNum(int labelNum) {
		this.labelNum = labelNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getTypes() {
		return types;
	}

	public void setTypes(String[] types) {
		this.types = types;
	}

	public String getTextMessage() {
		return textMessage;
	}
}
