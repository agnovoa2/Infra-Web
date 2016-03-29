package es.uvigo.esei.infraestructura.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "User")
@NamedQuery(name = "findAllUsers", query="select u from User u")
public class User {

	// Regex for the validation of md5 passwords

	@Id
	@JoinColumn
	@Column(name="login", length = 45)
	private String login;

	@Column(length = 32, nullable = false)
	private String password;

	@Column(length = 45, nullable = false)
	private String name;

	@Column(length = 45, nullable = false)
	private String firstSurname;

	@Column(length = 45, nullable = true)
	private String secondSurname;

	@Column(length = 9, nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(length = 60, nullable = false, unique = true)
	private String email;

	@Column
	private boolean authorized;

	@ManyToMany
	@JoinTable(name = "PROF_SUB", joinColumns = @JoinColumn(name = "login", referencedColumnName = "login"), 
		inverseJoinColumns = @JoinColumn(name = "subjectName", referencedColumnName = "subjectName"))
	private List<Subject> subjects;
	
	@ManyToMany
	@JoinTable(name = "PROF_PRIN", joinColumns = @JoinColumn(name = "login", referencedColumnName = "login"), 
		inverseJoinColumns = @JoinColumn(name = "inventoryNumber", referencedColumnName = "inventoryNumber"))
	private List<Printer> printers;

	@OneToMany(mappedBy="user")
	private List<Petition> petitions;
	
	// Constructor required for JPA framework
	User() {
	}

	// Constructor required for user registration
	public User(String password, String name, String firstSurname, String secondSurname) {
		this.password = password;
		this.name = name;
		this.firstSurname = firstSurname;
		this.secondSurname = secondSurname;
		this.role = Role.STUDENT;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstSurname() {
		return firstSurname;
	}

	public void setFirstSurname(String firstSurname) {
		this.firstSurname = firstSurname;
	}

	public String getSecondSurname() {
		return secondSurname;
	}

	public void setSecondSurname(String secondSurname) {
		this.secondSurname = secondSurname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(String role) {
		switch (role) {
		case "Becario":
			this.role = Role.INTERN;
			break;
		case "Profesor":
			this.role = Role.PROFESSOR;
			break;
		case "Alumno":
			this.role = Role.STUDENT;
			break;
		default:
			break;
		}
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public List<Printer> getPrinters() {
		return printers;
	}

	public void setPrinters(List<Printer> printers) {
		this.printers = printers;
	}
}
