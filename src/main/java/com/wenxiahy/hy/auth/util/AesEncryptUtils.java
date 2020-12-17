package com.wenxiahy.hy.auth.util;

import com.wenxiahy.hy.common.util.Base64Utils;
import com.wenxiahy.hy.common.util.Md5Utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Author zhouw
 * @Description
 * @Date 2019-12-17
 */
public class AesEncryptUtils {

    private static final String ENCRYPT_KEY = Md5Utils.string2Md5_16("JUST DO IT");
    private static final byte[] ENCRYPT_KEY_BYTE = ENCRYPT_KEY.getBytes();

    /**
     * 加密
     *
     * @param content
     * @param secret
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String secret) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        String encryptKey = Md5Utils.string2Md5_16(secret);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        byte[] b = cipher.doFinal(content.getBytes("utf-8"));
        return Base64Utils.encryptUrlSafeBase64(b);
    }

    /**
     * 加密，用默认的encrypt_key
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static String encrypt(String content) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(ENCRYPT_KEY_BYTE, "AES"));
        byte[] b = cipher.doFinal(content.getBytes("utf-8"));
        return Base64Utils.encryptUrlSafeBase64(b);
    }

    /**
     * 解密
     *
     * @param encryptStr
     * @param secret
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptStr, String secret) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        String decryptKey = Md5Utils.string2Md5_16(secret);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] encryptBytes = Base64Utils.decryptUrlSafeBase64Byte(encryptStr);
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    /**
     * 解密，用默认的encrypt_key
     *
     * @param encryptStr
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptStr) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(ENCRYPT_KEY_BYTE, "AES"));
        byte[] encryptBytes = Base64Utils.decryptUrlSafeBase64Byte(encryptStr);
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }
}
