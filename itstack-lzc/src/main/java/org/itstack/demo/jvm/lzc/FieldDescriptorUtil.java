package org.itstack.demo.jvm.lzc;

/**
 * @author: 悟心
 * @time: 2022/4/2 16:52
 * @description:
 */
public class FieldDescriptorUtil {
    public static String getDescriptorStr(String descriptor) {
        StringBuilder buf = new StringBuilder();
        if ("B".equals(descriptor)) {
            return "byte ";
        }
        if ("Ljava/lang/Byte;".equals(descriptor)) {
            return "Byte ";
        }
        if ("I".equals(descriptor)) {
            return "int ";
        }
        if ("Ljava/lang/Integer;".equals(descriptor)) {
            return "Integer ";
        }

        return buf.toString();
    }
}
