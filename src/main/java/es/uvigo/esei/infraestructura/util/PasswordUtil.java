package es.uvigo.esei.infraestructura.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class PasswordUtil {
	public static String diggestPassword(String password){
		MessageDigest passwordDigester;
		HexBinaryAdapter adapter = new HexBinaryAdapter();
		try {
			passwordDigester = MessageDigest.getInstance("MD5");
			return adapter.marshal(passwordDigester.digest(password.getBytes())).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			// You never get inside this
			e.printStackTrace();
		}
		return null;
		
	}
}
