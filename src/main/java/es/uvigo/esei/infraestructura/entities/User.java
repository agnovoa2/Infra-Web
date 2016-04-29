package es.uvigo.esei.infraestructura.entities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

@Entity
@Table(name = "User")
@NamedQueries({
	@NamedQuery(name = "findAllStudents", query="select u from User u Where u.role = es.uvigo.esei.infraestructura.entities.Role.STUDENT"),
	@NamedQuery(name = "findAllProfessors", query="select u from User u Where u.role = es.uvigo.esei.infraestructura.entities.Role.PROFESSOR"),
	@NamedQuery(name = "findAllInterns", query="select u from User u Where u.role = es.uvigo.esei.infraestructura.entities.Role.INTERN")})
public class User {

	// Regex for the validation of md5 passwords

	@Id
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
	private boolean banned;

	@ManyToMany
	@JoinTable(name = "PROF_SUB", joinColumns = @JoinColumn(name = "login", referencedColumnName = "login"), 
		inverseJoinColumns = @JoinColumn(name = "subjectId", referencedColumnName = "id"))
	private List<Subject> subjects;
	
	@ManyToMany
	@JoinTable(name = "PROF_PRIN", joinColumns = @JoinColumn(name = "login", referencedColumnName = "login"), 
		inverseJoinColumns = @JoinColumn(name = "inventoryNumber", referencedColumnName = "inventoryNumber"))
	private List<Printer> printers;

	@OneToMany(mappedBy="user")
	private List<ConsumablePetition> consumablePetitions;
	
	@OneToMany(mappedBy="user")
	private List<MaterialPetition> materialPetitions;
	
	@OneToMany(mappedBy="user")
	private List<Incidence> incidences;
	
	// Constructor required for JPA framework
	User() {
	}

	// Constructor required for user registration
	public User(String login, String email, String password, String name, String firstSurname, String secondSurname) {
		this.login = login;
		this.email = email;
		diggestPassword(password);
		this.name = name;
		this.firstSurname = firstSurname;
		this.secondSurname = secondSurname;
		this.role = Role.STUDENT;
	}
	
	public User(String login, String email, String md5, String name, String firstSurname, String secondSurname, Role role) {
		this.login = login;
		this.email = email;
		this.password = md5;
		this.name = name;
		this.firstSurname = firstSurname;
		this.secondSurname = secondSurname;
		this.role = role;
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

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
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

	public List<ConsumablePetition> getPetitions() {
		return consumablePetitions;
	}

	public void setPetitions(List<ConsumablePetition> petitions) {
		this.consumablePetitions = petitions;
	}

	public List<Incidence> getIncidences() {
		return incidences;
	}

	public void setIncidences(List<Incidence> incidences) {
		this.incidences = incidences;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return name + " " + firstSurname + " " + secondSurname;
	}
	
	private void diggestPassword(String password){
		MessageDigest passwordDigester;
		HexBinaryAdapter adapter = new HexBinaryAdapter();
		try {
			passwordDigester = MessageDigest.getInstance("MD5");
			this.password = adapter.marshal(passwordDigester.digest(password.getBytes())).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
