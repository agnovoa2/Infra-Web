package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Computer;
import es.uvigo.esei.infraestructura.entities.Incidence;
import es.uvigo.esei.infraestructura.entities.IncidenceType;
import es.uvigo.esei.infraestructura.entities.State;
import es.uvigo.esei.infraestructura.exception.EmptyIncidenceException;
import es.uvigo.esei.infraestructura.facade.ComputerGatewayBean;
import es.uvigo.esei.infraestructura.facade.IncidenceGatewayBean;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;
import es.uvigo.esei.infraestructura.util.Email;

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
	private Email mail;

	private ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	
	private int computerNum;
	private int labelNum;
	private int selectedLabelNum;
	private Integer[] auxArray;
	private String[] types;
	private String laboratory;
	private String description;
	private String textMessage;
	private boolean selected = false;
	private String message;
	private boolean error = false;
	private boolean success;

	public void initLists() {
		if (laboratory != null) {
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
			case "laboratorio 37":
				fillArray(26);
				break;
			case "laboratorio 38":
				fillArray(33);
				break;
			case "laboratorio 39":
				fillArray(25);
				break;
			case "laboratorio 30a":
				fillArray(29);
				break;
			case "laboratorio 31a":
				fillArray(29);
				break;
			case "laboratorio 31b":
				fillArray(25);
				break;
			}
		}

	}

	public void doSelectComputer(int num) {
		computerNum = num;
		computerGateway.find(getComputerNum(), getLaboratory());
		if (computerGateway.getCurrent() != null) {
			selectedLabelNum = computerGateway.getCurrent().getLabelNum();
		}
		selected = true;
	}

	public void doAddComputer() throws IOException {
		try {
			computerGateway.create(new Computer(getLaboratory(), getComputerNum(), getLabelNum()));
			computerGateway.save();
			error = false;
		} catch (SQLException e) {
			message = e.getMessage();
			error = true;
		}
	}

	public void doRemoveComputer() {
		computerGateway.find(getComputerNum(), getLaboratory());
		computerGateway.remove(computerGateway.getCurrent().getId());
		computerGateway.save();
	}

	public void doAddIncidence() throws IOException {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			dateFormat.format(date);
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			Incidence incidence = new Incidence(description, sqlDate);
			userGateway.find(currentUser.getName());
			computerGateway.find(computerNum, getLaboratory());
			incidence.setUser(userGateway.getCurrent());
			incidence.setComputer(computerGateway.getCurrent());
			List<IncidenceType> typeList = new LinkedList<IncidenceType>();
			if (types.length > 0) {
				for (String incidenceType : types) {
					typeList.add(new IncidenceType(incidenceType));
				}
			} else {
				throw new EmptyIncidenceException("Debes marcar por lo menos un tipo de incidencia");
			}
			incidence.setTypes(typeList);
			incidenceGateway.create(incidence);
			if (userGateway.getCurrent().getIncidences() == null) {
				userGateway.getCurrent().setIncidences(new LinkedList<Incidence>());
			}
			userGateway.getCurrent().getIncidences().add(incidence);
			if (computerGateway.getCurrent().getIncidences() == null) {
				computerGateway.getCurrent().setIncidences(new LinkedList<Incidence>());
			}
			computerGateway.getCurrent().getIncidences().add(incidence);
			computerGateway.getCurrent().setState(State.INCIDENCE);
			incidenceGateway.save();
			userGateway.save();
			computerGateway.save();
			setTextMessage();
			mail.sendEmail(textMessage, "[Infraestructura] Nueva incidencia en " + getLaboratory());
			context.redirect("incidences.xhtml?lab="+laboratory+"&success=true");
		} catch (EmptyIncidenceException e) {
			error = true;
			success = false;
			message = e.getMessage();
		}
	}

	private void fillArray(int c) {
		auxArray = new Integer[c];
		for (int i = 0; i < c; i++) {
			auxArray[i] = i;
		}
	}

	public boolean isNoPc(int num) {
		computerGateway.find(num, getLaboratory());
		if (computerGateway.getCurrent() == null) {
			return true;
		}
		return false;
	}

	public boolean isNoPc() {
		computerGateway.find(getComputerNum(), getLaboratory());
		if (computerGateway.getCurrent() == null) {
			return true;
		}
		return false;
	}

	public boolean isPcOk(int num) {
		computerGateway.find(num, getLaboratory());
		if (computerGateway.getCurrent() != null && computerGateway.getCurrent().getState() == State.OK)
			return true;
		return false;
	}

	public boolean isPcRepair(int num) {
		computerGateway.find(num, getLaboratory());
		if (computerGateway.getCurrent() != null
				&& computerGateway.getCurrent().getState() == State.UNDER_REPAIR)
			return true;
		return false;
	}

	public boolean isPcRepair() {
		computerGateway.find(getComputerNum(), getLaboratory());
		if (computerGateway.getCurrent() != null
				&& computerGateway.getCurrent().getState() == State.UNDER_REPAIR)
			return true;
		return false;
	}

	public boolean isHasIncidence(int num) {
		computerGateway.find(num, getLaboratory());
		if (computerGateway.getCurrent() != null
				&& computerGateway.getCurrent().getState() == State.INCIDENCE)
			return true;
		return false;
	}

	public boolean isHasIncidence() {
		computerGateway.find(getComputerNum(), getLaboratory());
		if (computerGateway.getCurrent() != null
				&& computerGateway.getCurrent().getState() == State.INCIDENCE) {
			return true;
		}
		return false;
	}

	public void redirectIfNotLaboratory() throws IOException {
		if (laboratory == null || laboratory.equals("")) {
			redirectToIndex();
		} else if (laboratory != null && !laboratory.toLowerCase().equals("libre acceso")
				&& !laboratory.toLowerCase().equals("s01") && !laboratory.toLowerCase().equals("s02")
				&& !laboratory.toLowerCase().equals("s03") && !laboratory.toLowerCase().equals("s04")
				&& !laboratory.toLowerCase().equals("s05") && !laboratory.toLowerCase().equals("s06")
				&& !laboratory.toLowerCase().equals("aula 2.1")
				&& !laboratory.toLowerCase().equals("aula 2.2")
				&& !laboratory.toLowerCase().equals("aula 3.1")
				&& !laboratory.toLowerCase().equals("aula 3.2")
				&& !laboratory.toLowerCase().equals("laboratorio 30a")
				&& !laboratory.toLowerCase().equals("laboratorio 31b")
				&& !laboratory.toLowerCase().equals("laboratorio 31a")
				&& !laboratory.toLowerCase().equals("laboratorio 37")
				&& !laboratory.toLowerCase().equals("laboratorio 38")
				&& !laboratory.toLowerCase().equals("laboratorio 39")) {
			redirectToIndex();
		}
	}

	public void setTextClose() {
		textMessage = ("Este es un mensaje autogenerado de la aplicación InfraWEB\n" + "\n"
				+ "Se ha solucionado la incidencia reportada sobre el ordenador "
				+ computerGateway.getCurrent().getLabelNum() + " en " + getLaboratory() + "\n"
				+ "Le agradecemos la molestia de reportar dicha incidencia \n"
				+ "Un saludo. Atte: Equipo de infraestructura de la ESEI");

	}

	public void setTextMessage() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		dateFormat.format(date);

		textMessage = ("Este es un mensaje autogenerado de la aplicación InfraWEB\n" + "\n"
				+ "El usuario " + userGateway.getCurrent().getName() + " " + userGateway.getCurrent().getFirstSurname()
				+ " " + userGateway.getCurrent().getSecondSurname() + " ha realizado a fecha de "
				+ new java.sql.Date(date.getTime()) + " la siguiente incidencia en el ordenador número " + labelNum
				+ " de " + getLaboratory() + "\n" + "Categorías: ");
		for (IncidenceType type : incidenceGateway.getCurrent().getTypes()) {
			textMessage += (type.getType() + " ");
		}
		String description = incidenceGateway.getCurrent().getDescription();
		if (description != null && !description.equals("")) {
			textMessage += ("\n" + "Descripción: " + description);
		}
	}

	public void doSendComputerToRepair() {
		computerGateway.find(getComputerNum(), getLaboratory());
		computerGateway.getCurrent().setState(State.UNDER_REPAIR);
		computerGateway.save();
	}

	public void doFinishIncidences() {
		computerGateway.find(getComputerNum(), getLaboratory());
		for (Incidence incidence : incidenceGateway
				.getAllComputerUnsolvedIncidences(computerGateway.getCurrent())) {
			incidence.setState(2);
			setTextClose();
			mail.sendEmail(getTextMessage(), "[Infraestructura] Cierre de incidencia", incidence.getUser().getEmail());
		}
		incidenceGateway.save();
		computerGateway.getCurrent().setState(State.OK);
		computerGateway.save();
	}

	public List<Incidence> unresolvedIncidences() {
		computerGateway.find(getComputerNum(), getLaboratory());
		return incidenceGateway.getAllComputerUnsolvedIncidences(computerGateway.getCurrent());
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

	public int getPcLabel() {
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

	public int getSelectedLabelNum() {
		return selectedLabelNum;
	}

	public void setSelectedLabelNum(int selectedLabelNum) {
		this.selectedLabelNum = selectedLabelNum;
	}

	public int getPcLabel(int num) {
		int oldPc = 0;
		boolean flag = false;

		if (computerGateway.getCurrent() != null) {
			oldPc = computerGateway.getCurrent().getId();
			flag = true;
		}
		int label = 0;
		computerGateway.find(num, getLaboratory());
		if (computerGateway.getCurrent() != null)
			label = computerGateway.getCurrent().getLabelNum();
		if (flag) {
			computerGateway.find(oldPc, getLaboratory());
		}
		return label;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
