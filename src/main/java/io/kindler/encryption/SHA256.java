package io.kindler.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256 암호화 클래스(단방향 암호화)
 *
 * @author heart.kindler@gmail.com
 */
public class SHA256 {
	private MessageDigest md = null;

	public SHA256(){
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 입력값을 SHA256 으로 암호화 한다.
	 * @param str 암호화 할 값
	 * @return
	 */
	public String encrypt(String str){
		String result = "";

		md.update(str.getBytes());
		byte[] byteData = md.digest();
		result = bytesToHex(byteData);

		return result;
	}

	private String bytesToHex(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
		return result.toString();
	}
}
