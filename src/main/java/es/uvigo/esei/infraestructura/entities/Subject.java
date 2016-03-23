package es.uvigo.esei.infraestructura.entities;

import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@RequestScoped
public class Subject {
	
	@Id
	@JoinColumn
	@Column(name="subjectName", length = 80)
	private String subjectName;

	@Column(length = 13, nullable = false)
	private SoftwareType code;
	
	@Column(length = 6, nullable = false)
	@Enumerated(EnumType.STRING)
	private SubjectDegree degree;
	
	@ManyToMany(mappedBy="subjects")
	private List<User> users;
	
	@ManyToMany
	@JoinTable(name = "SUB_SOFT", joinColumns = @JoinColumn(name = "subjectName", referencedColumnName = "subjectName"), 
		inverseJoinColumns = @JoinColumn(name = "softwareName", referencedColumnName = "softwareName"))
	private List<Software> softwares;
	
	Subject() {}

	public Subject(String subjectName, SoftwareType code, SubjectDegree degree) {
		super();
		this.subjectName = subjectName;
		this.code = code;
		this.degree = degree;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public SoftwareType getCode() {
		return code;
	}

	public void setCode(SoftwareType code) {
		this.code = code;
	}

	public SubjectDegree getDegree() {
		return degree;
	}

	public void setDegree(SubjectDegree degree) {
		this.degree = degree;
	}
	
	
}
