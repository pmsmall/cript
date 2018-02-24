package AES;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Javaʵ��AES���ܺͽ����㷨
 */
public class AES {

	/**
	 * AES����
	 * 
	 * @param content
	 *            ��Ҫ���ܵ�����
	 * @param password
	 *            ��������
	 * @return
	 */
	public static byte[] encrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(getBytes(password)));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			/** ���������� **/
			Cipher cipher = Cipher.getInstance("AES");
			/** ��ʼ�������� **/
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// byte[] result = cipher.doFinal(getBytes(content));
			byte[] result = cipher.doFinal(content.getBytes());
			return result;
		} catch (Exception e) {
			System.out.println("������:" + e.getMessage());
		}
		return null;
	}

	/**
	 * AES����
	 * 
	 * @param content
	 *            ����������
	 * @param password
	 *            ������Կ
	 * @return
	 */
	public static byte[] decrypt(byte[] content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");

			kgen.init(128, new SecureRandom(getBytes(password)));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			/** ���������� **/
			Cipher cipher = Cipher.getInstance("AES");
			/** ��ʼ�������� **/
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			System.out.println("������:" + e.getMessage());
		}
		return null;
	}

	/**
	 * ��������ת����ʮ������
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * ��������ת����ʮ������
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr2(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			// if (hex.length() == 1) {
			// hex = '0' + hex;
			// }
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * ��ʮ������ת��Ϊ������
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1) {
			return null;
		} else {
			byte[] result = new byte[hexStr.length() / 2];
			for (int i = 0; i < hexStr.length() / 2; i++) {
				int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
				int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
				result[i] = (byte) (high * 16 + low);
			}
			return result;
		}
	}

	/**
	 * ����AES���ܺͽ���
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/** ���ݳ�ʼ�� **/
		String content = "11223344556677889900AABBCCDDEEFF";
		String password = "13579BDF02468ACE1234567890ABCDEF";

		/** ����(1) **/
		System.out.println("����ǰ��" + content);
		byte[] encryptResult = encrypt(content, password);

		String encryptResultStr = parseByte2HexStr(encryptResult);
		System.out.println("���ܺ�" + encryptResultStr);

		/** ����(2) **/
		byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);
		byte[] decryptResult = decrypt(decryptFrom, password);
		System.out.println("���ܺ�" + new String(decryptResult));
		// System.out.println("���ܺ�" + parseByte2HexStr2(decryptResult));
	}

	public static byte[] getBytes(String s) {
		byte[] bytes = new byte[s.length()];
		for (int i = 0; i < bytes.length; i++) {
			char a = s.charAt(i);
			if (a >= '0' && a <= '9')
				bytes[i] = (byte) (a - '0');
			else if (a >= 'A' && a <= 'F')
				bytes[i] = (byte) (a - 'A' + 10);
		}
		return bytes;
	}
}
