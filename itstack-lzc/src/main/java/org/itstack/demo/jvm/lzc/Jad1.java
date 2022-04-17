package org.itstack.demo.jvm.lzc;

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
public class Jad1 {

    public static void main(String[] args) {
        Cmd cmd = Cmd.parse(args);
        if (!cmd.ok || cmd.helpFlag) {
            System.out.println("Usage: <main class> [-options] class [args...]");
            return;
        }

        startJVM(cmd);
    }

    private static void startJVM(Cmd cmd) {
        Classpath classpath = new Classpath(cmd.jre, cmd.classpath);
        //获取className
        String className = cmd.getMainClass().replace(".", "/");

        try {
            ClassInfo classInfo = classpath.readClass2ClassInfo(className);
            System.out.println("Last modified " + DateTimeUtil.toDateTimeString(classInfo.lastModified, "yyyy-MM-dd") + "; size " + classInfo.length + " bytes");
            System.out.println("MD5 checksum " + classInfo.md5+";classFromPath "+classInfo.classFromPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ClassLoader classLoader = new ClassLoader(classpath);
        Class clazz = classLoader.loadClass(className);
        decompile(clazz);

    }


    public static void decompile(Class clazz) {
        String className = clazz.name.replaceAll("/", ".");
        String simpleClassName = className.substring(className.lastIndexOf(".") + 1);

        System.out.println("Compiled from \"" + simpleClassName + ".java\" (反编译工具作者：Lzc)");

        StringBuilder buf = new StringBuilder();
        buf.append(clazz.deprecated ? "@Deprecated " : "").append(AccessFlagsUtil.getAccessFlagsStr(clazz.accessFlags)).append(className).append(" {\n");

        for (Field memberInfo : clazz.fields) {
            Object val = ValueUtil.getValue(memberInfo, clazz);
            buf.append("    ").append(memberInfo.deprecated ? "@Deprecated " : "").append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags)).append(FieldDescriptorUtil.getDescriptorStr(memberInfo.descriptor())).append(memberInfo.name()).append(Optional.ofNullable(val).map(tmp -> " = " + tmp).orElse("")).append(";\n");
        }
        buf.append("\n");

        Method clinitMethod = clazz.getClinitMethod();
        for (Method memberInfo : clazz.methods) {
            //方法参数类型 方法返回值类型
            MethodInfo methodInfo = MethodDescriptorUtil.getMethodInfo(memberInfo, simpleClassName, clinitMethod.equals(memberInfo));
            buf.append("    ").append(memberInfo.deprecated ? "@Deprecated " : "").append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags)).append(methodInfo.getValueType()).append(methodInfo.getMethodName()).append(methodInfo.getParamType()).append(";\n");

        }
        buf.append("}");

        System.out.println(buf.toString());
    }

}