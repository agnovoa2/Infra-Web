package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.itextpdf.text.DocumentException;

import es.uvigo.esei.infraestructura.entities.Consumable;
import es.uvigo.esei.infraestructura.entities.ConsumablePetition;
import es.uvigo.esei.infraestructura.entities.ConsumablePetitionRow;
import es.uvigo.esei.infraestructura.exception.EmptyPetitionException;
import es.uvigo.esei.infraestructura.facade.ConsumableGatewayBean;
import es.uvigo.esei.infraestructura.facade.ConsumablePetitionGatewayBean;
import es.uvigo.esei.infraestructura.facade.ConsumablePetitionRowGatewayBean;
import es.uvigo.esei.infraestructura.facade.PrinterGatewayBean;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;
import es.uvigo.esei.infraestructura.util.Email;
import es.uvigo.esei.infraestructura.util.Report;

@ViewScoped
@ManagedBean(name = "consumablePetition")
public class ConsumablePetitionController {

	@Inject
	private Principal currentUser;

	@Inject
	private PrinterGatewayBean printerGateway;

	@Inject
	private UserGatewayBean userGateway;

	@Inject
	private ConsumableGatewayBean consumableGateway;

	@Inject
	private ConsumablePetitionGatewayBean petitionGateway;

	@Inject
	private ConsumablePetitionRowGatewayBean petitionRowGateway;

	@Inject
	private Email mail;

	@Inject
	private Report report;

	private int invnum;
	private List<Consumable> printerConsumables;
	private List<String> quantities;
	private String textMessage;
	private String message;
	private boolean error = false;
	private boolean success = false;

	@PostConstruct
	public void init() {
		userGateway.find(currentUser.getName());
	}

	public void initLists() {
		printerConsumables = printerGateway.find(getInvnum()).getModel().getConsumables();
		quantities = new LinkedList<String>();
		for (int i = 0; i < printerConsumables.size(); i++) {
			quantities.add("0");
		}
	}

	public List<Consumable> getPrinterConsumables() {
		return printerConsumables;
	}

	public void setPrinterConsumables(List<Consumable> printerConsumables) {
		this.printerConsumables = printerConsumables;
	}

	public int getInvnum() {
		return invnum;
	}

	public void setInvnum(int invnum) {
		this.invnum = invnum;
	}

	public void doAddPetition() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		dateFormat.format(date);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());

		ConsumablePetition petition = new ConsumablePetition(printerGateway.getCurrent(), sqlDate,
				userGateway.getCurrent());
		List<ConsumablePetitionRow> petitionRows = new LinkedList<ConsumablePetitionRow>();

		for (int i = 0; i < printerConsumables.size(); i++) {
			if (Integer.parseInt(quantities.get(i)) > 0) {
				ConsumablePetitionRow pr = new ConsumablePetitionRow(petition,
						consumableGateway.find(printerConsumables.get(i).getConsumableName()),
						Integer.parseInt(quantities.get(i)));
				consumableGateway.getCurrent().setOrdered(true);
				consumableGateway.save();
				petitionRows.add(pr);
			}
		}
		try {
			if (petitionRows.isEmpty()) {
				throw new EmptyPetitionException();
			} else {
				petition.setPetitionRows(petitionRows);
				setTextMessage(petition);
				petitionGateway.create(petition);
				petitionGateway.save();

				for (ConsumablePetitionRow petitionRow : petitionRows) {
					petitionRowGateway.create(petitionRow);
					petitionRowGateway.save();
				}
/*
				setTextMessage(petition);
				petitionGateway.create(petition);
				petitionGateway.save();

				for (ConsumablePetitionRow petitionRow : petitionRows) {
					petitionRowGateway.create(petitionRow);
					petitionRowGateway.save();
				}*/

				mail.sendEmail(getTextMessage(), "[Infraestructura] Nueva petición de consumibles");
				report.doSolicitudePDF(petition);
				error = false;
				success = true;
				message = "Petición realizada correctamente.";
			}
		} catch (DocumentException | IOException e) {
			error = true;
			success = false;
			message = "Ha sucedido algún error al realizar la petición, por favor, vuelva a intentarlo";
		} catch (EmptyPetitionException e) {
			error = true;
			success = false;
			message = "No ha escogido ningún consumible para realizar su petición";
		}
	}

	public List<String> getQuantities() {
		return quantities;
	}

	public void setQuantities(List<String> quantities) {
		this.quantities = quantities;
	}

	public void setTextMessage(ConsumablePetition petition) {
		textMessage = ("Este es un mensaje autogenerado de la aplicación [Futuro nombre aqui]\n" + "\n" + "El profesor "
				+ userGateway.getCurrent().getName() + " " + userGateway.getCurrent().getFirstSurname() + " "
				+ userGateway.getCurrent().getSecondSurname() + " ha realizado a fecha de " + petition.getPetitionDate()
				+ " la siguiente petición de consumibles para la impresora de la marca "
				+ printerGateway.getCurrent().getModel().getTradeMark() + " modelo "
				+ printerGateway.getCurrent().getModel().getModelName() + ".\n" + " \n");
		for (ConsumablePetitionRow petitionRow : petition.getPetitionRows()) {
			if (petitionRow.getConsumable().getColour() != null)
				textMessage += petitionRow.getConsumable().getColour() + ": "
						+ petitionRow.getConsumable().getConsumableName() + " Cantidad: " + petitionRow.getQuantity()
						+ "\n";
			else
				textMessage += petitionRow.getConsumable().getConsumableType().toString() + ": "
						+ petitionRow.getConsumable().getConsumableName() + " Cantidad: " + petitionRow.getQuantity()
						+ "\n";

		}
	}

	public String getTextMessage() {
		return textMessage;
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
