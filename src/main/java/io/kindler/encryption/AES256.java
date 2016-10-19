package io.kindler.encryption;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * AES256 암/복호화 클래스(양방향 암호화)
 *
 * @author heart.kindler@gmail.com
 */
public class AES256 {
	private String iv;
	private Key keySpec;
	private Charset charset;
	private String algorithm;

	/**
	 * builder 패턴을 이용한 AES256 암호화 컴포넌트 생성
	 */
	public static class Builder {
		private String key;
		private Charset charset = StandardCharsets.UTF_8;
		private String algorithm = "AES256/CBC/PKCS5Padding";

		public Builder(String key) {
			this.key = key;
		}

		public Builder keyEncoding(Charset charset) {
			this.charset = charset;
			return this;
		}

		public Builder algorithm(String algorithm) {
			this.algorithm = algorithm;
			return this;
		}

		public AES256 build() throws InvalidKeyException {
			if (this.key == null) throw new InvalidKeyException("key is null");
			if (this.key.length() < 17) throw new InvalidKeyException("key has more than 16 digits.");
			return new AES256(this);
		}
	}

	private AES256(Builder builder) {
		this.charset = builder.charset;
		this.algorithm = builder.algorithm;
		this.iv = builder.key.substring(0, 16);

		byte[] keyBytes = new byte[16];
		byte[] b = builder.key.getBytes(builder.charset);
		int len = b.length;
		if (len > keyBytes.length) {
			len = keyBytes.length;
		}
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES256");

		this.keySpec = keySpec;
	}

	/**
	 * AES256 으로 암호화 한다
	 *
	 * @param str 암호화할 문자열
	 * @return
	 */
	public String encrypt(String str) {
		Cipher cipher;
		String encryptedStr = "";
		try {
			cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
			byte[] encryptedBytes = cipher.doFinal(str.getBytes(charset));
			encryptedStr = new String(Base64.encode(encryptedBytes));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}

		return encryptedStr;
	}

	/**
	 * AES256 으로 복호화 한다.
	 *
	 * @param str 복호화할 문자열
	 * @return
	 */
	public String decrypt(String str) {
		Cipher cipher;
		String decryptedStr = "";

		try {
			cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
			byte[] byteStr = Base64.decode(str);
			decryptedStr = new String(cipher.doFinal(byteStr), charset);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}

		return decryptedStr;
	}
}
