package RC4;


/**
 * Created by asus1 on 2017/3/27.
 */
public class mainrc4 {
    public static String decry_mainrc4(byte[] data, String key) {
        if (data == null || key == null) {
            return null;
        }
        return toHexString(asString(mainrc4Base(data, key)));
    }


    public static String decry_mainrc4(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        return toHexString(asString(mainrc4Base(HexString2Bytes(data), key)));
    }


    public static byte[] encry_mainrc4_byte(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        byte[] b_data = HexString2Bytes(data);
        return mainrc4Base(b_data, key);
    }


    public static String encry_mainrc4_string(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        return toHexString(asString(encry_mainrc4_byte(data, key)));
    }

    private static byte[] initKey(String aKey) {
        byte[] b_key = HexString2Bytes(aKey);
        byte state[] = new byte[256];

        for (int i = 0; i < 256; i++) {
            state[i] = (byte) i;
        }
        int index1 = 0;
        int index2 = 0;
        if (b_key == null || b_key.length == 0) {
            return null;
        }
        for (int i = 0; i < 256; i++) {
            index2 = ((b_key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;
            byte tmp = state[i];
            state[i] = state[index2];
            state[index2] = tmp;
            index1 = (index1 + 1) % b_key.length;
        }
        return state;
    }

    private static byte[] mainrc4Base (byte [] input, String mKkey) {
        int x = 0;
        int y = 0;
        byte key[] = initKey(mKkey);
        int xorIndex;
        byte[] result = new byte[input.length];

        for (int i = 0; i < input.length; i++) {
            x = (x + 1) & 0xff;
            y = ((key[x] & 0xff) + y) & 0xff;
            byte tmp = key[x];
            key[x] = key[y];
            key[y] = tmp;
            xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;
            result[i] = (byte) (input[i] ^ key[xorIndex]);
        }
        return result;
    }

    private static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch & 0xFF).toUpperCase();
            if (s4.length() == 1) {
                s4 = '0' + s4;
            }
            str = str + s4;
        }
        return str;// 0x表示十六进制
    }

    private static String asString(byte[] buf) {
        StringBuffer strbuf = new StringBuffer(buf.length);
        for (int i = 0; i < buf.length; i++) {
            strbuf.append((char) buf[i]);
        }
        return strbuf.toString();
    }

    private static byte[] HexString2Bytes(String src) {
        int size = src.length();
        byte[] ret = new byte[size / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < size / 2; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    private static byte uniteBytes(byte src0, byte src1) {
        char _b0 = (char)Byte.decode("0x" + new String(new byte[] { src0 }))
                .byteValue();
        _b0 = (char) (_b0 << 4);
        char _b1 = (char)Byte.decode("0x" + new String(new byte[] { src1 }))
                .byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }
public static void main(String [] args) {
	String input = "11223344556677889900AABBCCDDEEFF";
	String key = "13579BDF02468ACE1234567890ABCDEF";
	System.out.println("明文："+input);
	String mainrc4code = mainrc4.encry_mainrc4_string(input, key);
    System.out.println("mainrc4 加密：" + mainrc4code);
    String mainrc4output = mainrc4.decry_mainrc4(mainrc4code, key);
    System.out.println("mainrc4 解密: " + mainrc4output);
}

}
