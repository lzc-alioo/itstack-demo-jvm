package com.lzc.wuxin;

import com.lzc.wuxin.util.*;
import org.itstack.demo.jvm.Cmd;
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
public class Jad1 {

    public static void main(String[] args) {
        Cmd cmd = Cmd.parse(args);
        if (!cmd.ok || cmd.helpFlag) {
            System.out.println("Usage: <main class> [-options] class [args...]");
            return;
        }

        deCompile(cmd);
    }


    public static void deCompile(Cmd cmd) {
        Classpath classpath = new Classpath(cmd.jre, cmd.classpath);
        ClassLoader classLoader = new ClassLoader(classpath);

        String className =  cmd.getMainClass().replace(".", "/");
        String simpleClassName = className.substring(className.lastIndexOf("/") + 1);
        System.out.println("Compiled from \"" + simpleClassName + ".java\" (反编译工具作者：Lzc)");

        Class clazz = classLoader.loadClass(className);
        StringBuilder buf = new StringBuilder();
        buf.append(clazz.deprecated ? "@Deprecated " : "").append(AccessFlagsUtil.getAccessFlagsStr(clazz.accessFlags)).append(cmd.getMainClass()).append(" {\n");

        for (Field memberInfo : clazz.fields) {
            Object val = ValueUtil.getValue(memberInfo, clazz);
            buf.append("    ").append(memberInfo.deprecated ? "@Deprecated " : "").append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags)).append(FieldDescriptorUtil.getDescriptorStr(memberInfo.descriptor())).append(memberInfo.name()).append(Optional.ofNullable(val).map(tmp -> " = " + tmp).orElse("")).append(";\n");
        }
        buf.append("\n");

        for (Method memberInfo : clazz.methods) {
            //方法参数类型 方法返回值类型
            MethodInfo methodInfo = MethodDescriptorUtil.getMethodInfo(memberInfo);
            buf.append("    ").append(memberInfo.deprecated ? "@Deprecated " : "").append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags)).append(methodInfo.getValueType()).append(methodInfo.getMethodName()).append(methodInfo.getParamType()).append(";\n");

        }
        buf.append("}");

        System.out.println(buf.toString());
    }

}