package com.whforever.common.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

// import org.apache.log4j.Logger;

public class AesUtil {

    public static Map<String, String> props = PropertiesUtils.prop;
    // private static Logger logger=Logger.getLogger(AesUtil.class);
    static String password = props.get("aes.password");// "54950D7759E0A444F15C3D71C6C103C7";

    public static void main(String[] args)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {}

    public static String encrypt(String content)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        byte[] result = encrypt(content, password);
        // logger.info("xxxxxxxx content:"+content+",
        // password:"+props.get("aes_password")+",result:"+new String(result)
        // +", parseByte2HexStr(result):"+parseByte2HexStr(result));
        return parseByte2HexStr(result);
    }

    public static String decrypt(String content)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException {
        byte[] decryptFrom = parseHexStr2Byte(content);
        byte[] decryptResult = decrypt(decryptFrom, password);
        return new String(decryptResult);
    }

    /**
     * 加密
     * 
     * @param content 需要加密的内容
     * @param password 加密密码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private static byte[] encrypt(String content, String password)
            throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // KeyGenerator kgen = KeyGenerator.getInstance("AES");
        // kgen.init(128, new SecureRandom(password.getBytes()));
        // SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
        // secureRandom.setSeed(password.getBytes());
        // kgen.init(128,secureRandom);

        // SecretKey secretKey = kgen.generateKey();
        // byte[] enCodeFormat = secretKey.getEncoded();
        byte[] enCodeFormat = parseHexStr2Byte(password);
        // System.out.println("enCodeFormat:"+parseByte2HexStr(enCodeFormat));
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
        byte[] byteContent = content.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(byteContent);
        return result; // 加密

    }

    /**
     * 解密
     * 
     * @param content 待解密内容
     * @param password 解密密钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private static byte[] decrypt(byte[] content, String password)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        /*
         * KeyGenerator kgen = KeyGenerator.getInstance("AES"); //kgen.init(128, new
         * SecureRandom(password.getBytes())); SecureRandom secureRandom =
         * SecureRandom.getInstance("SHA1PRNG" ); secureRandom.setSeed(password.getBytes());
         * kgen.init(128,secureRandom);
         * 
         * SecretKey secretKey = kgen.generateKey(); byte[] enCodeFormat = secretKey.getEncoded();
         */
        byte[] enCodeFormat = parseHexStr2Byte(password);
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(content);
        return result; // 加密

    }

    /**
     * 将二进制转换成16进制
     * 
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte buf[]) {
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
     * 将16进制转换为二进制
     * 
     * @param hexStr
     * @return
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
