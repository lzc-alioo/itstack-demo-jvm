package com.lzc.wuxin;

import com.lzc.wuxin.util.*;
import org.apache.commons.lang3.StringUtils;
import org.itstack.demo.jvm.Cmd;
import org.itstack.demo.jvm.classfile.ClassInfo;
import org.itstack.demo.jvm.classpath.Classpath;
import org.itstack.demo.jvm.rtda.heap.ClassLoader;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Field;
import org.itstack.demo.jvm.rtda.heap.methodarea.Method;

import java.util.Optional;

/**
 * @author: 悟心
 * @time: 2022/4/14 23:45
 * @description:
 */
public class Jad2 {

    public static void main(String[] args) {
        Cmd cmd = Cmd.parse(args);
        if (!cmd.ok || cmd.helpFlag) {
            System.out.println("Usage: <main class> [-options] class [args...]");
            return;
        }

        deCompile(cmd);
    }


    public static void deCompile(Cmd cmd) {
        String className = cmd.getMainClass().replace(".", "/");
        String simpleClassName = className.substring(className.lastIndexOf("/") + 1);

        ClassInfo classInfo = null;
        try {
            classInfo = new Classpath(cmd.jre, cmd.classpath).readClass2ClassInfo(className);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        System.out.println(StringUtils.rightPad("Classfile", 16) + ": " + classInfo.classFromPath);
        System.out.println(StringUtils.rightPad("  Last modified ", 16) + ": " + DateTimeUtil.toDateTimeString(classInfo.lastModified, "yyyy-MM-dd") + "; size " + classInfo.length + " bytes");
        System.out.println(StringUtils.rightPad("  MD5 checksum ", 16) + ": " + classInfo.md5);
        System.out.println(StringUtils.rightPad("  Compiled from ", 16) + ": " + simpleClassName + ".java\" (反编译工具作者：Lzc)");

        Classpath classpath = new Classpath(cmd.jre, cmd.classpath);
        ClassLoader classLoader = new ClassLoader(classpath);
        Class clazz = classLoader.loadClass(className);
        StringBuilder buf = new StringBuilder();
        buf.append(clazz.deprecated ? "@Deprecated " : "").append(AccessFlagsUtil.getAccessFlagsStr(clazz.accessFlags)).append(cmd.getMainClass()).append("\n");

        buf.append("  minor version: ").append(classInfo.classFile.minorVersion()).append("\n");
        buf.append("  major version: ").append(classInfo.classFile.majorVersion()).append("\n");
        buf.append("  flags        : ").append(AccessFlagsUtil.getAccessFlagsStr(clazz.accessFlags)).append("\n");

        System.out.print(buf.toString());
        buf.delete(0, buf.length());

        buf.append("Constant pool:\n");
        Object[] constants = clazz.constantPool().constants;
        for (int i = 0; i < constants.length; i++) {
            //   #1 = Methodref          #5.#34         // java/lang/Object."<init>":()V
            buf.append(StringUtils.leftPad("#" + i, 4)).append(" = ");
            buf.append(ConstantPoolUtil.readableConstant(constants[i]));
            buf.append("\n");
        }
        System.out.print(buf.toString());
        buf.delete(0, buf.length());

        buf.append("{\n");
        for (Field memberInfo : clazz.fields) {
            Object val = ValueUtil.getValue(memberInfo, clazz);
            buf.append("  ").append(memberInfo.deprecated ? "@Deprecated " : "").append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags)).append(FieldDescriptorUtil.getDescriptorStr(memberInfo.descriptor())).append(memberInfo.name()).append(Optional.ofNullable(val).map(tmp -> " = " + tmp).orElse("")).append(";\n");
        }
        buf.append("\n");

        for (Method memberInfo : clazz.methods) {
            //方法参数类型 方法返回值类型
            MethodInfo methodInfo = MethodDescriptorUtil.getMethodInfo(memberInfo);
            buf.append("  ").append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags)).append(methodInfo.getValueType()).append(methodInfo.getMethodName()).append(methodInfo.getParamType()).append(";\n");
            buf.append("    descriptor:").append(memberInfo.descriptor).append("\n");
            buf.append("    flags: ").append(AccessFlagsUtil.getAccessFlagsStr(clazz.accessFlags)).append("\n");
            buf.append("    Code: ").append("\n");
            buf.append("      stack=").append(memberInfo.maxStack).append(", locals=").append(memberInfo.maxLocals).append(", args_size=").append(memberInfo.argSlotCount()).append("\n");

            for(byte o:memberInfo.code){

            }

        }
        buf.append("}");

        System.out.println(buf.toString());
    }
}