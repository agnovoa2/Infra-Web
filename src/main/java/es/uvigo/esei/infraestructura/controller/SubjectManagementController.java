package es.uvigo.esei.infraestructura.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Software;
import es.uvigo.esei.infraestructura.entities.Subject;
import es.uvigo.esei.infraestructura.entities.SubjectDegree;
import es.uvigo.esei.infraestructura.entities.User;
import es.uvigo.esei.infraestructura.facade.SoftwareGatewayBean;
import es.uvigo.esei.infraestructura.facade.SubjectGatewayBean;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;

@ViewScoped
@ManagedBean(name = "subjectManagement")
public class SubjectManagementController {

	@Inject
	private SubjectGatewayBean subjectGateway;

	@Inject
	private UserGatewayBean userGateway;

	@Inject
	private SoftwareGatewayBean softwareGateway;

	private String newSubjectName;
	private String code;
	private String subjectName;
	private String grade;

	public void doAddSubject() {
		subjectGateway.create(new Subject(getSubjectName(), getCode(), getGrade().toLowerCase()));
		subjectGateway.save();
	}

	public void doRemoveSubject(String code) {
		subjectGateway.findByCode(code);
		if (subjectGateway.getCurrent() != null) {
			if (subjectGateway.getCurrent().getSoftwares() != null) {
				for (Software software : subjectGateway.getCurrent().getSoftwares()) {
					softwareGateway.find(software.getSoftwareName());
					softwareGateway.getCurrent().getSubjects().remove(subjectGateway.getCurrent());
					softwareGateway.save();
				}
			}
			if (subjectGateway.getCurrent().getUsers() != null) {
				for (User user : subjectGateway.getCurrent().getUsers()) {
					userGateway.find(user.getLogin());
					userGateway.getCurrent().getSubjects().remove(subjectGateway.getCurrent());
					userGateway.save();
				}
			}
			subjectGateway.getCurrent().setSoftwares(null);
			subjectGateway.getCurrent().setUsers(null);
		}
		subjectGateway.remove(subjectGateway.getCurrent().getId());
		subjectGateway.save();

	}

	public void doSetEditSubject(String code) {
		subjectGateway.findByCode(code);
		setSubjectName(subjectGateway.getCurrent().getSubjectName());
		setNewSubjectName(subjectGateway.getCurrent().getSubjectName());
		setCode(subjectGateway.getCurrent().getCode());
		if (subjectGateway.getCurrent().getDegree().equals(SubjectDegree.GRADE))
			setGrade("Grado");
		if (subjectGateway.getCurrent().getDegree().equals(SubjectDegree.MASTER))
			setGrade("Máster");
	}

	public void doEditSubject() {
		subjectGateway.findByCode(getCode());
		subjectGateway.getCurrent().setSubjectName(getNewSubjectName());
		subjectGateway.getCurrent().setCode(getCode());
		subjectGateway.getCurrent().setDegree(getGrade());
		subjectGateway.save();
	}

	public List<Subject> getAllSubjects() {
		return this.subjectGateway.getAll();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getNewSubjectName() {
		return newSubjectName;
	}

	public void setNewSubjectName(String newSubjectName) {
		this.newSubjectName = newSubjectName;
	}
}
