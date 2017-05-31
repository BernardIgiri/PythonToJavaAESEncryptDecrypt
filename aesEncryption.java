import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.*;

import java.util.Arrays;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.lang.Throwable;

import org.apache.commons.codec.binary.Base64;

public class aesEncryption {
	private static final String CYPHER_MODE = "AES/CBC/PKCS5Padding";

	public static byte[] encrypt(byte[] key, byte[] initVector, byte[] value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector);
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

			Cipher cipher = Cipher.getInstance(CYPHER_MODE);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			return cipher.doFinal(value);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static byte[] decrypt(byte[] key, byte[] initVector, byte[] encrypted) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector);
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

			Cipher cipher = Cipher.getInstance(CYPHER_MODE);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			return cipher.doFinal(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) {
		try {
			InputStream keyFS = new FileInputStream("encryption.key");
			byte[] key = new byte[16];
			keyFS.read(key);
			keyFS.close();
			byte[] b64content = Files.readAllBytes(Paths.get("encryptedFile"));
			byte[] content = Base64.decodeBase64(b64content);
			byte[] iv = Arrays.copyOfRange(content, 0, 16);
			byte[] cyphertext = Arrays.copyOfRange(content, 16, content.length);
			String plaintext = new String(decrypt(key, iv, cyphertext));
			System.out.println(plaintext);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
