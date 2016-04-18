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
	//needed for JPA
	private int id;
	
	@Column(name="mail")
	private String mail;
	
	@Column(name="passwordMail")
	//MD5 mail password
	private String passwordMail;
	
	@Column(name="targetMail")
	private String targetMail;
	
	@Column(name="host")
	private String host;
	
	@Column(name="port")
	private int port;
	
	@Column(name="baseDN")
	private String baseDN;
	
	@Column(name="userDN")
	private String userDN;
	
	@Column(name="ldapPassword")
	private String ldapPassword;
	
	@Column(name="securityAuthentication")
	private String securityAuthentication;
	
	@Column(name="securityProtocol")
	private String securityProtocol;
	
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getBaseDN() {
		return baseDN;
	}

	public void setBaseDN(String baseDN) {
		this.baseDN = baseDN;
	}

	public String getUserDN() {
		return userDN;
	}

	public void setUserDN(String userDN) {
		this.userDN = userDN;
	}

	public String getLdapPassword() {
		return ldapPassword;
	}

	public void setLdapPassword(String ldapPassword) {
		this.ldapPassword = ldapPassword;
	}

	public String getSecurityProtocol() {
		return securityProtocol;
	}

	public void setSecurityProtocol(String securityProtocol) {
		this.securityProtocol = securityProtocol;
	}

	public String getSecurityAuthentication() {
		return securityAuthentication;
	}

	public void setSecurityAuthentication(String securityAuthentication) {
		this.securityAuthentication = securityAuthentication;
	}
}
