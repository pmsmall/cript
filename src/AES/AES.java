package AES;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Java实现AES加密和解密算法
 */
public class AES {

	/**
	 * AES加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static byte[] encrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(getBytes(password)));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			/** 创建密码器 **/
			Cipher cipher = Cipher.getInstance("AES");
			/** 初始化密码器 **/
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// byte[] result = cipher.doFinal(getBytes(content));
			byte[] result = cipher.doFinal(content.getBytes());
			return result;
		} catch (Exception e) {
			System.out.println("出错了:" + e.getMessage());
		}
		return null;
	}

	/**
	 * AES解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public static byte[] decrypt(byte[] content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");

			kgen.init(128, new SecureRandom(getBytes(password)));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			/** 创建密码器 **/
			Cipher cipher = Cipher.getInstance("AES");
			/** 初始化密码器 **/
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			System.out.println("出错了:" + e.getMessage());
		}
		return null;
	}

	/**
	 * 将二进制转换成十六进制
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
	 * 将二进制转换成十六进制
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
	 * 将十六进制转换为二进制
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
	 * 测试AES加密和解密
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/** 数据初始化 **/
		String content = "11223344556677889900AABBCCDDEEFF";
		String password = "13579BDF02468ACE1234567890ABCDEF";

		/** 加密(1) **/
		System.out.println("加密前：" + content);
		byte[] encryptResult = encrypt(content, password);

		String encryptResultStr = parseByte2HexStr(encryptResult);
		System.out.println("加密后：" + encryptResultStr);

		/** 解密(2) **/
		byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);
		byte[] decryptResult = decrypt(decryptFrom, password);
		System.out.println("解密后：" + new String(decryptResult));
		// System.out.println("解密后：" + parseByte2HexStr2(decryptResult));
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
