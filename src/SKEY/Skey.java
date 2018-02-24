package SKEY;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
* MD5 算法
*/

public class Skey {

	// 全局数组

	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",

			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public Skey() {

	}

	// 返回形式为数字跟字符串

	private static String byteToArrayString(byte bByte) {

		int iRet = bByte;

		// System.out.println("iRet="+iRet);

		if (iRet < 0) {

			iRet += 256;

		}

		int iD1 = iRet / 16;

		int iD2 = iRet % 16;

		return strDigits[iD1] + strDigits[iD2];

	}

	// 返回形式只为数字

	private static String byteToNum(byte bByte) {

		int iRet = bByte;

		System.out.println("iRet1=" + iRet);

		if (iRet < 0) {

			iRet += 256;

		}

		return String.valueOf(iRet);

	}

	/**
	 * 二进制转字符串
	 * @param bByte
	 * @return
	 */

	private static String byteToString(byte[] bByte) {

		StringBuffer sBuffer = new StringBuffer();

		for (int i = 0; i < bByte.length; i++) {

			sBuffer.append(byteToArrayString(bByte[i]));

		}

		return sBuffer.toString();

	}
	/**
	 * 
	 * 获取字符串的MD5值
	 * @param 字符串
	 * @return MD5
	 */
	public static String GetMD5Code(String strObj) {

		String resultString = null;

		try {

			resultString = new String(strObj);

			MessageDigest md = MessageDigest.getInstance("MD5");

			// md.digest() 该函数返回值为存放哈希值结果的byte数组

			resultString = byteToString(md.digest(strObj.getBytes()));

		} catch (NoSuchAlgorithmException ex) {

			ex.printStackTrace();

		}

		return resultString;

	}
   /**
    * SKEY协议的主要内容
    * @param str 
    * @throws IOException
    */
	public void skey(String str) throws IOException {

		Boolean bl = true;

		String string = new String();

		for (int i = 0; i < 99; i++) {

			string = GetMD5Code(str);

			str = string;

			System.out.println("第" + (99 - i) + "次验证码" + string);

		}

		string = GetMD5Code(str);

		while (bl) {

			System.out.println("请输入验证码");

			InputStreamReader isr = new InputStreamReader(System.in);

			BufferedReader br = new BufferedReader(isr);

			String str1 = br.readLine();

			if (GetMD5Code(str1).equals(string)) {

				System.out.println("验证正确");

				string = str1;

			}

			else

				System.out.println("验证错误");

		}

	}

	public static void main(String[] args) throws Exception {

		Skey getMD5 = new Skey();

		InputStreamReader isr = new InputStreamReader(System.in);

		BufferedReader br = new BufferedReader(isr);

		System.out.println("输入验证信息");

		String str = br.readLine();

		getMD5.skey(str);

	}

}