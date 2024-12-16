package dev.hash.airplaneAPI.airplane.comm.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SHA256Util {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SHA256Util.class);
	
	public static String getSHA256Hash(String data) {
		try {
			MessageDigest msgDigest = MessageDigest.getInstance("SHA-256");
			
			byte[] hashBytes = msgDigest.digest(data.getBytes());
			return bytesToHex(hashBytes);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("error : {}", e);
			throw new RuntimeException();
		}
	}
	
	public static String bytesToHex(byte[] hashBytes) {
		StringBuffer hexString = new StringBuffer();
		for(byte bt : hashBytes) {
			String hex = Integer.toHexString(0xff & bt);
			if(hex.length() == 1) hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString().toUpperCase();
	}

}
