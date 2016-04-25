package es.uvigo.esei.infraestructura.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Hashtable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import es.uvigo.esei.infraestructura.ejb.UserAuthorizationEJB;
import es.uvigo.esei.infraestructura.entities.Configuration;
import es.uvigo.esei.infraestructura.entities.Role;
import es.uvigo.esei.infraestructura.entities.User;
import es.uvigo.esei.infraestructura.facade.ConfigurationGatewayBean;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;

@RequestScoped
@ManagedBean(name = "loginController")
public class LoginController {

	@Inject
	private Principal currentUser;

	@Inject
	private UserAuthorizationEJB auth;

	@Inject
	private UserGatewayBean userGateway;

	@Inject
	private ConfigurationGatewayBean configurationGateway;

	private ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

	private String login;
	private String password;
	private boolean error;
	private String errorMessage;
	private Hashtable<String, String> env = new Hashtable<String, String>();

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

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public void doLogin() throws IOException, ServletException {
		HttpServletRequest request = null;
		try {
			request = (HttpServletRequest) context.getRequest();
			this.password = diggestPassword(password);
			request.login(this.getLogin(), this.getPassword());
			this.error = false;
			this.userGateway.find(this.getLogin());
			if (auth.getCurrentUser().getRole().equals(Role.INTERN))
				FacesContext.getCurrentInstance().getExternalContext().redirect("zonaBecario.xhtml");
			else
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml?login=true");
		} catch (ServletException e) {
			if (ldapLogin(request)) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml?login=true");
			} else {
				this.error = true;
				this.errorMessage = "Login or password don't match";
				context.redirect("index.xhtml?login=error");
			}
		}
	}

	public boolean ldapLogin(HttpServletRequest request) {
		try {
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			String security = "";
			Configuration conf = configurationGateway.find();
			if (conf.getSecurityProtocol() != null && conf.getSecurityProtocol().equals("ssl")) {
				security = "ldaps://";
			} else {
				security = "ldap://";
			}
			env.put(Context.PROVIDER_URL, security + conf.getHost() + ":" + conf.getPort() + "/" + conf.getBaseDN());
			env.put(Context.SECURITY_PROTOCOL, conf.getSecurityProtocol());
			env.put(Context.SECURITY_AUTHENTICATION, conf.getSecurityAuthentication());
			env.put(Context.SECURITY_PRINCIPAL, conf.getUserDN());
			env.put(Context.SECURITY_CREDENTIALS, conf.getLdapPassword());
			trustSelfSignedSSL();
			if (existAccount(getLogin(),"alumnos"))
				;
			else if (existAccount(getLogin(),"profesores"))
				;
			request.login(this.getLogin(), this.getPassword());
			return true;
		} catch (ServletException e) {
			return false;
		}
	}

	public void doLogout() throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		request.logout();
		FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
	}

	public Principal getCurrentUser() {
		return this.currentUser;
	}

	public boolean isIntern() {
		return auth.getCurrentUser().getRole().equals(Role.INTERN);
	}

	public boolean isProfessor() {
		return auth.getCurrentUser().getRole().equals(Role.PROFESSOR);
	}

	public boolean isStudent() {
		return auth.getCurrentUser().getRole().equals(Role.STUDENT);
	}

	public boolean isAnonymous() {
		return "anonymous".equals(this.getCurrentUser().getName());
	}

	public void redirectIfAnonymous() throws IOException {
		if (this.isAnonymous())
			redirectToIndex();
	}

	public void redirectIfStudent() throws IOException {
		if (this.isStudent())
			redirectToIndex();
	}

	public void redirectIfNotIntern() throws IOException {
		if (!this.isIntern())
			redirectToIndex();
	}

	private void redirectToIndex() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
	}

	private String diggestPassword(String password) {
		MessageDigest passwordDigester;
		HexBinaryAdapter adapter = new HexBinaryAdapter();
		try {
			passwordDigester = MessageDigest.getInstance("MD5");
			return adapter.marshal(passwordDigester.digest(password.getBytes())).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

	}

	private void trustSelfSignedSSL() {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {

				public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLContext.setDefault(ctx);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private String ldapMd5ToRealMd5(String string) {
		byte[] ba = Base64.getDecoder().decode(string);
		StringBuilder hex = new StringBuilder(ba.length * 2);
		for (byte b : ba)
			hex.append(String.format("%02x", b));
		return hex.toString();
	}

	private boolean existAccount(String login, String user) {
		try {
			String md5 = "";
			String firstSurname = "";
			String secondSurname = "";
			String finalPass = "";
			DirContext ctx = new InitialDirContext(env);
			Attributes att = ctx.getAttributes("uid=" + login + ",ou=" + user,
					new String[] { "cn", "sn", "userPassword" });
			Attribute pass = att.get("userPassword");
			byte[] passByte = (byte[]) pass.get();
			finalPass = new String(passByte);
			Attribute surname = att.get("sn");
			String[] surnames = surname.get().toString().split(" ");
			firstSurname = surnames[0];
			if (surnames.length > 1) {
				secondSurname = surnames[1];
				if (surnames.length > 2) {
					for (int i = 2; i < surnames.length; i++) {
						secondSurname += " " + surnames[i];
					}
				}
			}
			if (finalPass.startsWith("{MD5}")) {
				md5 = ldapMd5ToRealMd5(finalPass.substring(5));
			} else if (finalPass.startsWith("{crypt}"))
				ctx.close();
			else {
				md5 = diggestPassword(finalPass);
			}
			if (md5.equals(getPassword())) {
				Role role;
				if(user.equals("alumnos")){
					role = Role.STUDENT;
				}
				else{
					role = Role.PROFESSOR;
				}
				userGateway.create(new User(getLogin(), getLogin() + "@esei.uvigo.es", md5,
						att.get("cn").get().toString(), firstSurname, secondSurname, role));
				userGateway.save();
				return true;
			} else {
				return false;
			}
		} catch (NamingException e) {
			return false;
		}
	}
}
