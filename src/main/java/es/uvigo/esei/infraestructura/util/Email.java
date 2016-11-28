package es.uvigo.esei.infraestructura.util;

import java.util.Properties;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import es.uvigo.esei.infraestructura.facade.ConfigurationGatewayBean;

@Default
@Stateless
public class Email {

	@Inject
	private ConfigurationGatewayBean configurationGateway;
	
	private Properties props;
	private Session session;

	public void sendEmail(String text, String subject, String destination) {
		if (configurationGateway.getCurrent() == null)
			init();
		try {

			Message message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(destination));
			message.setSubject(subject);
			message.setText(text);
			Transport.send(message);
			
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public void sendEmail(String text, String subject) {
		if (configurationGateway.getCurrent() == null)
			init();
		sendEmail(text,subject,configurationGateway.getCurrent().getTargetMail());
	}

	private void init() {
		configurationGateway.find();
		props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.socketFactory.port", "465");
	    props.put("mail.smtp.socketFactory.class",
	            "javax.net.ssl.SSLSocketFactory");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.starttls.enable", "true");

		session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(configurationGateway.getCurrent().getMail(), configurationGateway.getCurrent().getPasswordMail());
			}
		});
	}
}
