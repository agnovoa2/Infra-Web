package es.uvigo.esei.infraestructura.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Computer;
import es.uvigo.esei.infraestructura.entities.Incidence;
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

	private int computerNum = 0;
	private int labelNum;
	private Integer[] auxArray;
	private String laboratory;
	private String description;

	public void initLists() {
		switch (laboratory) {
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
		this.computerGateway.find(getComputerNum(), getLaboratory());
	}

	public void doAddComputer() {
		this.computerGateway.create(new Computer(getLaboratory(), getComputerNum(), getLabelNum()));
		this.computerGateway.save();
	}

	public void doRemoveComputer() {
		this.computerGateway.find(getComputerNum(), getLaboratory());
		this.computerGateway.remove(this.computerGateway.getCurrent().getId());
		this.computerGateway.save();
	}

	public void doAddIncidence() {
		this.computerGateway.find(getComputerNum(), getLaboratory());
		this.computerGateway.getCurrent().setState(State.INCIDENCE);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		dateFormat.format(date);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		if (this.computerGateway.getCurrent().getIncidences() == null) {
			List<Incidence> incidences = new LinkedList<Incidence>();
			this.computerGateway.getCurrent().setIncidences(incidences);
		}
		this.computerGateway.getCurrent().getIncidences().add(new Incidence(this.getDescription(), sqlDate));
		this.incidenceGateway.create(new Incidence(this.getDescription(), sqlDate));
		this.incidenceGateway.getCurrent().setComputer(this.computerGateway.getCurrent());
		this.incidenceGateway.save();
		this.computerGateway.save();
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

	public boolean isHasIncidence(int num) {
		this.computerGateway.find(num, getLaboratory());
		if (this.computerGateway.getCurrent() != null
				&& this.computerGateway.getCurrent().getState() == State.INCIDENCE)
			return true;
		return false;
	}

	public boolean hasFocus(int num) {
		if (this.getComputerNum() == num)
			return true;
		return false;
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

}
