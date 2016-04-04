package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
		this.computerGateway.find(getComputerNum(), getLaboratory());
		this.computerGateway.getCurrent().setState(State.INCIDENCE);
		this.userGateway.find(currentUser.getName());
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		dateFormat.format(date);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		if (this.computerGateway.getCurrent().getIncidences() == null) {
			List<Incidence> incidences = new LinkedList<Incidence>();
			this.computerGateway.getCurrent().setIncidences(incidences);
		}
		List<IncidenceType> typeList = new LinkedList<IncidenceType>();
		for (String incidence : this.types) {
			typeList.add(new IncidenceType(incidence));
		}
		this.computerGateway.getCurrent().getIncidences().add(new Incidence(this.getDescription(), sqlDate));
		this.incidenceGateway.create(new Incidence(this.getDescription(), sqlDate));
		this.incidenceGateway.getCurrent().setTypes(typeList);
		this.incidenceGateway.getCurrent().setComputer(this.computerGateway.getCurrent());
		this.incidenceGateway.getCurrent().setUser(this.userGateway.getCurrent());
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

	public void redirectIfNotLaboratory() throws IOException {
		if (!this.laboratory.toLowerCase().equals("libre acceso")
				&& !this.laboratory.toLowerCase().equals("s01")
				&& !this.laboratory.toLowerCase().equals("s02")
				&& !this.laboratory.toLowerCase().equals("s03")
				&& !this.laboratory.toLowerCase().equals("s04")
				&& !this.laboratory.toLowerCase().equals("s05")
				&& !this.laboratory.toLowerCase().equals("s06")
				&& !this.laboratory.toLowerCase().equals("aula 2.1")
				&& !this.laboratory.toLowerCase().equals("aula 2.2")
				&& !this.laboratory.toLowerCase().equals("aula 3.1")
				&& !this.laboratory.toLowerCase().equals("aula 3.2")
				&& !this.laboratory.toLowerCase().equals("laboratorio 30a")
				&& !this.laboratory.toLowerCase().equals("laboratorio 30b")
				&& !this.laboratory.toLowerCase().equals("laboratorio 31a")
				&& !this.laboratory.toLowerCase().equals("laboratorio 37")
				&& !this.laboratory.toLowerCase().equals("laboratorio 38")
				&& !this.laboratory.toLowerCase().equals("laboratorio 39")
				) {
			this.redirectToIndex();
		}
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
}
