package org.itstack.demo.jvm.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    public static final String MD5_ALGORITHM_NAME = "MD5";

    public static String encode(byte[] data){
        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance(MD5_ALGORITHM_NAME).digest(data);
        } catch (NoSuchAlgorithmException e) {
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
