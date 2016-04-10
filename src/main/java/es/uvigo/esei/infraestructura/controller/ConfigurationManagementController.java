package es.uvigo.esei.infraestructura.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.facade.ConfigurationGatewayBean;

@ViewScoped
@ManagedBean(name = "configurationManagement")
public class ConfigurationManagementController {
	
	@Inject
	ConfigurationGatewayBean configurationGateway;
	
	private String mailSource;
	private String mailDestination;
	private String mailPassword;
	
	@PostConstruct
	public void init(){
		configurationGateway.find();
		mailSource = configurationGateway.getCurrent().getMail();
		mailDestination = configurationGateway.getCurrent().getTargetMail();
	}
	
	public void doChangeConfiguration(){
		configurationGateway.getCurrent().setMail(getMailSource());
		configurationGateway.getCurrent().setTargetMail(getMailDestination());
		if(getMailPassword() != null && !getMailPassword().equals("")){
			configurationGateway.getCurrent().setPasswordMail(getMailPassword());
		}
		configurationGateway.save();
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
}
