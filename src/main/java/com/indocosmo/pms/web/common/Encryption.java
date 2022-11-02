package com.indocosmo.pms.web.common;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import javax.crypto.spec.SecretKeySpec;

public class Encryption {
	private static Cipher cipher;
	private static KeyGenerator keyGenerator;
	private static Key secretKey;

	public Encryption() throws Exception {
		keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		secretKey = generateKey();
		cipher = Cipher.getInstance("AES");
	}

	public String encrypt(String plainText) throws Exception {
		byte[] plainTextByte = plainText.getBytes();
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedByte = cipher.doFinal(plainTextByte);
		Base64.Encoder encoder = Base64.getEncoder();
		String encryptedText = encoder.encodeToString(encryptedByte);
		return encryptedText;
	}

	public String decrypt(String encryptedText) throws Exception {
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] encryptedTextByte = decoder.decode(encryptedText);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
		String decryptedText = new String(decryptedByte);
		return decryptedText;
	}

	private static Key generateKey() throws Exception {
		byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };
		Key key = new SecretKeySpec(keyValue, "AES");
		return key;
	}
}