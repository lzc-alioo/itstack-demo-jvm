package org.itstack.demo.jvm.lzc;

import org.apache.commons.lang3.StringUtils;
import org.itstack.demo.jvm.rtda.heap.constantpool.*;

/**
 * @author: 悟心
 * @time: 2022/4/15 01:09
 * @description:
 */
public class ConstantPoolUtil {
    public static String readableConstant(Object obj) {
        if (obj instanceof Integer) {
            StringBuilder sb = new StringBuilder(StringUtils.rightPad("Integer", 20));
            sb.append(StringUtils.rightPad(obj.toString(), 30));
            return sb.toString();
        }
        if (obj instanceof Long) {
            StringBuilder sb = new StringBuilder(StringUtils.rightPad("Long", 20));
            sb.append(StringUtils.rightPad(obj.toString(), 30));
            return sb.toString();
        }
        if (obj instanceof Float || obj instanceof Double) {
            return obj.toString();
        }
        if (obj instanceof String) {
            StringBuilder sb = new StringBuilder(StringUtils.rightPad("Utf8", 20));
            sb.append(StringUtils.rightPad(obj.toString(), 30));
            return sb.toString();

        }
        if (obj instanceof StringRef) {
            StringRef ref = (StringRef) obj;

            StringBuilder sb = new StringBuilder(StringUtils.rightPad("StringRef", 20));
            sb.append(StringUtils.rightPad("#" + ref.strIdx, 30));
            sb.append(StringUtils.rightPad("// " + ref.str, 40));
            return sb.toString();
        }
        if (obj instanceof ClassRef) {
            ClassRef ref = (ClassRef) obj;

            StringBuilder sb = new StringBuilder(StringUtils.rightPad("ClassRef", 20));
            sb.append(StringUtils.rightPad("#" + ref.nameIdx, 30));
            sb.append(StringUtils.rightPad("// " + ref.className, 40));
            return sb.toString();
        }
        if (obj instanceof FieldRef) {
            FieldRef ref = (FieldRef) obj;

            StringBuilder sb = new StringBuilder(StringUtils.rightPad("FieldRef", 20));
            sb.append(StringUtils.rightPad("#" + ref.classIdx + ".#" + ref.nameAndTypeIdx, 30));
            sb.append(StringUtils.rightPad("// " + ref.className + "." + ref.name + ":" + ref.descriptor, 40));
            return sb.toString();
        }
        if (obj instanceof InterfaceMethodRef) {
            InterfaceMethodRef ref = (InterfaceMethodRef) obj;
            return ref.toString();
        }
        if (obj instanceof MethodRef) {
            MethodRef ref = (MethodRef) obj;

            StringBuilder sb = new StringBuilder(StringUtils.rightPad("MethodRef", 20));
            sb.append(StringUtils.rightPad("#" + ref.classIdx + ".#" + ref.nameAndTypeIdx, 30));
            sb.append(StringUtils.rightPad("// " + ref.className + "." + ref.name + ":" + ref.descriptor, 40));
            return sb.toString();
        }
        return null;
    }
}
