package es.uvigo.esei.infraestructura.controller;

import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.facade.UserGatewayBean;
import es.uvigo.esei.infraestructura.util.Mail;

@ViewScoped
@ManagedBean(name = "rememberPassword")
public class RememberPasswordController {

	@Inject
	private UserGatewayBean userGateway;

	@Inject
	private Mail mail;

	private String login;
	private String message;
	private String textMessage;
	private boolean error;
	private boolean success;

	public void doResetPassword() {
		userGateway.find(login);
		if (userGateway.getCurrent() != null) {
			String password = newRandomPassword();
			userGateway.getCurrent().setPassword(password);
			userGateway.save();
			setTextMessage(password);
			mail.sendMail(textMessage, "Restablecimiento de contraseña", userGateway.getCurrent().getEmail());
			success = true;
			error = false;
			message ="Se ha enviado un correo con la nueva contraseña.";
		} else {
			error = true;
			success = false;
			message = "No existe ese usuario.";
		}
	}

	private String newRandomPassword() {
		String toRet = "";
		Random r = new Random();
		for (int i = 0; i < 6; i++) {
			toRet += (char) (r.nextInt(26) + 'a');
		}
		System.out.println(toRet);
		return toRet;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
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

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setTextMessage(String password) {
		this.textMessage = ("Este es un mensaje autogenerado de la aplicación [Futuro nombre aqui]\n" + "\n"
				+ "La nueva contraseña es: " + password);
	}
}
