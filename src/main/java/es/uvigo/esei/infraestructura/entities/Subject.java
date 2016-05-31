package es.uvigo.esei.infraestructura.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Subject")
@NamedQueries({ 
	@NamedQuery(name = "findAllSubjects", query = "select s from Subject s"),
	@NamedQuery(name = "findAllSubjectPetitions", query = "Select s From Subject s Where s.petitionState > 0"),
	@NamedQuery(name = "findAllUnsolvedSubjectPetitions", query = "Select s From Subject s Where s.petitionState = 1")
})

public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "subjectName", length = 80, nullable = false)
	private String subjectName;

	@Column(name = "code", length = 13, nullable = false, unique = true)
	private String code;

	@Column(name = "degree", length = 6, nullable = false)
	@Enumerated(EnumType.STRING)
	private SubjectDegree degree;

	// 0 for no petition, 1 for petition done and 2 for petition solved
	@Column(name = "petitionState", nullable = false)
	private int petitionState;

	@Column(name = "description", length = 1000)
	private String description;

	@ManyToMany(mappedBy = "subjects")
	private List<User> users;

	@ManyToMany
	@JoinTable(name = "SUB_SOFT", joinColumns = @JoinColumn(name = "subjectId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "softwareId", referencedColumnName = "id"))
	private List<Software> softwares;

	Subject() {
	}

	public Subject(String subjectName, String code, String degree) {
		super();
		this.subjectName = subjectName;
		this.code = code;
		if (degree.equals("grado"))
			this.degree = SubjectDegree.GRADE;
		if (degree.equals("máster"))
			this.degree = SubjectDegree.MASTER;
		this.petitionState = 0;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public SubjectDegree getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		if (degree.equals("grado"))
			this.degree = SubjectDegree.GRADE;
		if (degree.equals("máster"))
			this.degree = SubjectDegree.MASTER;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Software> getSoftwares() {
		return softwares;
	}

	public void setSoftwares(List<Software> softwares) {
		this.softwares = softwares;
	}

	public int getPetitionState() {
		return petitionState;
	}

	public void setPetitionState(int petitionState) {
		this.petitionState = petitionState;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			if (((Subject) obj).getSubjectName().equals(this.getSubjectName()))
				return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}

}
