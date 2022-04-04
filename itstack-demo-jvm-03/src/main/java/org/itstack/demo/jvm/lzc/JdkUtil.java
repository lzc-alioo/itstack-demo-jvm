package org.itstack.demo.jvm.lzc;

/**
 * @author: 悟心
 * @time: 2022/3/30 23:12
 * @description:
 */
public class JdkUtil {

    public static String getVersion(int majorVersion) {
        String version = null;

        switch (majorVersion) {
            case 47:
                version = "JDK1.3";
                break;
            case 48:
                version = "JDK1.4";
                break;
            case 49:
                version = "JDK1.5";
                break;
            case 50:
                version = "JDK1.6";
                break;
            case 51:
                version = "JDK1.7";
                break;
            case 52:
                version = "JDK1.8";
                break;
            default:
                version = "Unknown Jdk Version";
                break;
        }

        return version;
    }
}
