package com.lzc.wuxin.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    public static final String MD5_ALGORITHM_NAME = "MD5";

    public static String encode(String string, String enCode) throws Exception {
        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance(MD5_ALGORITHM_NAME).digest(string.getBytes(enCode));
        } catch (NoSuchAlgorithmException e) {
//            log.error("32位MD5加密出错---->NoSuchAlgorithmException" + e.getMessage());
        } catch (UnsupportedEncodingException e) {
//            log.error("32位MD5加密出错---->UnsupportedEncodingException" + e.getMessage());
        }

        if (hash == null) {
            throw new Exception("Md5Util.MD5 hash is null");
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * utf-8编码并捕获异常（异常会返回原值）
     *
     * @param str 原值
     * @return 编码后的值
     */
    public static String encodeCatchException(String str) {
        try {
            return Md5Util.encode(str, "utf-8");
        } catch (Exception e) {
//            log.error("encodeCatch error, str={}", str, e);
        }
        return str;
    }

    public static String encode(byte[] data) throws Exception {
        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance(MD5_ALGORITHM_NAME).digest(data);
        } catch (NoSuchAlgorithmException e) {
//            log.error("32位MD5加密出错---->NoSuchAlgorithmException" + e.getMessage());
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
