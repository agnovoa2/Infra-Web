package es.uvigo.esei.infraestructura.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.Subject;
import es.uvigo.esei.infraestructura.facade.ConfigurationGatewayBean;
import es.uvigo.esei.infraestructura.facade.SubjectGatewayBean;

@ViewScoped
@ManagedBean(name = "configurationManagement")
public class ConfigurationManagementController {
	
	@Inject
	ConfigurationGatewayBean configurationGateway;
	
	@Inject
	SubjectGatewayBean subjectGateway;
	
	private String mailSource;
	private String mailDestination;
	private String mailPassword;
	private String host;
	private int port;
	private String baseDN;
	private String userDN;
	private String ldapPassword;
	private String securityAuthentication;
	private String securityProtocol;
	private boolean success = false;
	private String message;
	
	@PostConstruct
	public void init(){
		configurationGateway.find();
		mailSource = configurationGateway.getCurrent().getMail();
		mailDestination = configurationGateway.getCurrent().getTargetMail();
		host = configurationGateway.getCurrent().getHost();
		port = configurationGateway.getCurrent().getPort();
		baseDN = configurationGateway.getCurrent().getBaseDN();
		userDN = configurationGateway.getCurrent().getUserDN();
		securityAuthentication = configurationGateway.getCurrent().getSecurityAuthentication();
		securityProtocol = configurationGateway.getCurrent().getSecurityProtocol();
	}
	
	public void doNewGrade(){
		for (Subject subject : subjectGateway.getAll()) {
			subjectGateway.find(subject.getSubjectName());
			subjectGateway.getCurrent().setSoftwares(null);
			subjectGateway.getCurrent().setPetitionState(0);
			subjectGateway.save();
			success = true;
			message = "Las asignaturas se han reestablecido";
		}
	}
	
	public void doChangeConfiguration(){
		configurationGateway.getCurrent().setMail(getMailSource());
		configurationGateway.getCurrent().setTargetMail(getMailDestination());
		if(getMailPassword() != null && !getMailPassword().equals("")){
			configurationGateway.getCurrent().setPasswordMail(getMailPassword());
		}
		configurationGateway.getCurrent().setHost(getHost());
		configurationGateway.getCurrent().setPort(getPort());
		configurationGateway.getCurrent().setBaseDN(getBaseDN());
		configurationGateway.getCurrent().setUserDN(getUserDN());
		if(getLdapPassword() != null && !getLdapPassword().equals("")){
			configurationGateway.getCurrent().setLdapPassword(getLdapPassword());
		}
		configurationGateway.getCurrent().setSecurityAuthentication(getSecurityAuthentication());
		configurationGateway.getCurrent().setSecurityProtocol(getSecurityProtocol());
		configurationGateway.save();
		success = true;
		message = "Se han realizado los cambios correctamente";
	}
	
	public String getMailSource() {
		return mailSource;
	}
	public void setMailSource(String mailSource) {
		this.mailSource = mailSource;
	}
	public String getMailDestination() {
		return mailDestination;
	}
	public void setMailDestination(String mailDestination) {
		this.mailDestination = mailDestination;
	}
	public String getMailPassword() {
		return mailPassword;
	}
	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
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

	public String getSecurityAuthentication() {
		return securityAuthentication;
	}

	public void setSecurityAuthentication(String securityAuthentication) {
		this.securityAuthentication = securityAuthentication;
	}

	public String getSecurityProtocol() {
		return securityProtocol;
	}

	public void setSecurityProtocol(String securityProtocol) {
		this.securityProtocol = securityProtocol;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
