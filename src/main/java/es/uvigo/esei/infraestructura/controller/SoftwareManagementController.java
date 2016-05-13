package es.uvigo.esei.infraestructura.controller;

import java.sql.SQLException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Software;
import es.uvigo.esei.infraestructura.entities.SoftwareType;
import es.uvigo.esei.infraestructura.entities.Subject;
import es.uvigo.esei.infraestructura.facade.SoftwareGatewayBean;
import es.uvigo.esei.infraestructura.facade.SubjectGatewayBean;

@ViewScoped
@ManagedBean(name = "softwareManagement")
public class SoftwareManagementController {

	@Inject
	private SubjectGatewayBean subjectGateway;

	@Inject
	private SoftwareGatewayBean softwareGateway;

	private String newSoftwareName;
	private String softwareName;
	private String downloadURL;
	private int type;
	private String message;
	private boolean editable = false;
	private boolean error = false;

	public void doAddSoftware() {
		try {
			softwareGateway.create(new Software(getSoftwareName(), getType(), getDownloadURL()));
			softwareGateway.save();
			error = false;
		} catch (SQLException e) {
			message = e.getMessage();
			error = true;
		}
	}

	public void doRemoveSoftware(String softwareName) {

		softwareGateway.find(softwareName);

		if (softwareGateway.getCurrent() != null) {
			if (softwareGateway.getCurrent().getSubjects() != null) {
				for (Subject subject : softwareGateway.getCurrent().getSubjects()) {
					subjectGateway.findByCode(subject.getCode());
					subjectGateway.getCurrent().getSoftwares().remove(softwareGateway.getCurrent());
					subjectGateway.save();
				}
			}
			softwareGateway.getCurrent().setSubjects(null);
		}
		softwareGateway.remove(softwareGateway.getCurrent().getId());
		softwareGateway.save();
	}

	public void doSetEditSoftware(String softwareName) {
		softwareGateway.find(softwareName);
		setSoftwareName(softwareGateway.getCurrent().getSoftwareName());
		setNewSoftwareName(softwareGateway.getCurrent().getSoftwareName());
		setDownloadURL(softwareGateway.getCurrent().getDownloadURL());
		if (softwareGateway.getCurrent().getType().equals(SoftwareType.PROGRAM))
			setType(0);
		if (softwareGateway.getCurrent().getType().equals(SoftwareType.OPERATIVE_SYSTEM))
			setType(1);
		editable = true;
	}

	public void doEditSoftware() {
		try {
			if (editable) {
				softwareGateway.find(getSoftwareName());
				softwareGateway.getCurrent().setSoftwareName(getNewSoftwareName());
				softwareGateway.getCurrent().setDownloadURL(getDownloadURL());
				softwareGateway.getCurrent().setType(getType());
				softwareGateway.save();
				setNewSoftwareName("");
				setDownloadURL("");
				editable = false;
				error = false;
			}
		} catch (Exception e) {
			message = "Ya existe ese software en la base de datos.";
			error = true;
		}
	}

	public List<Software> getAllSoftware() {
		return softwareGateway.getAll();
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	public String getDownloadURL() {
		return downloadURL;
	}

	public void setDownloadURL(String downloadURL) {
		this.downloadURL = downloadURL;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getNewSoftwareName() {
		return newSoftwareName;
	}

	public void setNewSoftwareName(String newSoftwareName) {
		this.newSoftwareName = newSoftwareName;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
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
}
