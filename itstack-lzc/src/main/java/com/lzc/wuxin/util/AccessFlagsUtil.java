package com.lzc.wuxin.util;

import org.itstack.demo.jvm.rtda.heap.constantpool.AccessFlags;

/**
 * @author: 悟心
 * @time: 2022/4/2 16:00
 * @description:
 */
public class AccessFlagsUtil {
    public static String getAccessFlagsStr(int accessFlags) {
        StringBuilder accessFlagsBuf = new StringBuilder();

        if ((AccessFlags.ACC_PUBLIC & accessFlags) == AccessFlags.ACC_PUBLIC) {
            accessFlagsBuf.append("public ");
        }
        if ((AccessFlags.ACC_PRIVATE & accessFlags) == AccessFlags.ACC_PRIVATE) {
            accessFlagsBuf.append("private ");
        }
        if ((AccessFlags.ACC_PROTECTED & accessFlags) == AccessFlags.ACC_PROTECTED) {
            accessFlagsBuf.append("protected ");
        }
        if ((AccessFlags.ACC_STATIC & accessFlags) == AccessFlags.ACC_STATIC) {
            accessFlagsBuf.append("static ");
        }
        if ((AccessFlags.ACC_FINAL & accessFlags) == AccessFlags.ACC_FINAL) {
            accessFlagsBuf.append("final ");
        }

        if ((AccessFlags.ACC_ABSTRACT & accessFlags) == AccessFlags.ACC_ABSTRACT) {
            accessFlagsBuf.append("abstract ");
        }
        if ((AccessFlags.ACC_SUPER & accessFlags) == AccessFlags.ACC_SUPER) {
            accessFlagsBuf.append("class ");
        }
        if ((AccessFlags.ACC_INTERFACE & accessFlags) == AccessFlags.ACC_INTERFACE) {
            accessFlagsBuf.append("class ");
        }


        return accessFlagsBuf.toString();
    }
}
