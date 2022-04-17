package com.lzc.wuxin.util;

/**
 * @author: 悟心
 * @time: 2022/3/30 23:12
 * @description:
 */
public class JdkVersionUtil {

    public static String getVersion(int majorVersion) {
        String version = null;

        switch (majorVersion) {
            case 45:
                version = "JDK1";
                break;
            case 46:
                version = "JDK2";
                break;
            case 47:
                version = "JDK3";
                break;
            case 48:
                version = "JDK4";
                break;
            case 49:
                version = "JDK5";
                break;
            case 50:
                version = "JDK6";
                break;
            case 51:
                version = "JDK7";
                break;
            case 52:
                version = "JDK8";
                break;
            case 53:
                version = "JDK9";
                break;
            case 54:
                version = "JDK10";
                break;
            case 55:
                version = "JDK11";
                break;
            case 56:
                version = "JDK12";
                break;
            case 57:
                version = "JDK13";
                break;
            case 58:
                version = "JDK14";
                break;
            case 59:
                version = "JDK15";
                break;
            case 60:
                version = "JDK16";
                break;
            case 61:
                version = "JDK17";
                break;
            case 62:
                version = "JDK18";
                break;
            default:
                version = "Unknown Jdk Version";
                break;
        }

        return version;
    }
}
