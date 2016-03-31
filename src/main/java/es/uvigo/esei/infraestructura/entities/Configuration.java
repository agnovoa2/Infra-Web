package es.uvigo.esei.infraestructura.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Configuration")
public class Configuration {
	
	@Id
	@Column(name="id")
	//needed for jpa
	private int id;
	
	@Column(name="mail")
	private String mail;
	
	@Column(name="passwordMail")
	//MD5 mail password
	private String passwordMail;
	
	@Column(name="targetMail")
	private String targetMail;
	
	Configuration(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPasswordMail() {
		return passwordMail;
	}

	public void setPasswordMail(String passwordMail) {
		this.passwordMail = passwordMail;
	}

	public String getTargetMail() {
		return targetMail;
	}

	public void setTargetMail(String targetMail) {
		this.targetMail = targetMail;
	}
}
