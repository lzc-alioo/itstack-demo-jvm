package org.itstack.demo.jvm;

import org.itstack.demo.jvm.classfile.ClassFile;
import org.itstack.demo.jvm.classfile.MemberInfo;
import org.itstack.demo.jvm.classfile.attributes.AttributeInfo;
import org.itstack.demo.jvm.classfile.constantpool.ConstantInfo;
import org.itstack.demo.jvm.classpath.Classpath;
import org.itstack.demo.jvm.lzc.Jad;
import org.itstack.demo.jvm.lzc.JdkUtil;
import org.itstack.demo.jvm.rtda.heap.ClassLoader;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/24
 * -Xjre "C:\Program Files\Java\jdk1.8.0_161\jre" java.lang.String
 */
public class Main {

    public static void main(String[] args) {
        Cmd cmd = Cmd.parse(args);
        if (!cmd.ok || cmd.helpFlag) {
            System.out.println("Usage: <main class> [-options] class [args...]");
            return;
        }
        if (cmd.versionFlag) {
            //注意案例测试都是基于1.8，另外jdk1.9以后使用模块化没有rt.jar
            System.out.println("java version \"1.8.0\"");
            return;
        }
        startJVM(cmd);
    }

    private static void startJVM(Cmd cmd) {
        Classpath classpath = new Classpath(cmd.jre, cmd.classpath);
        System.out.printf("classpath：%s class：%s args：%s\n", classpath, cmd.getMainClass(), cmd.getAppArgs());
        //获取className
        String className = cmd.getMainClass().replace(".", "/");
        ClassFile classFile = loadClass(className, classpath);
        assert classFile != null;
        printClassInfo(classFile);

        ClassLoader classLoader = new ClassLoader(classpath);
        Class clazz=classLoader.loadClass(className);
        Jad.deCompile(clazz);

    }

    private static ClassFile loadClass(String className, Classpath classpath) {
        try {
            byte[] classData = classpath.readClass(className);
            return new ClassFile(classData);
        } catch (Exception e) {
            System.out.println("Could not find or load main class " + className);
            return null;
        }
    }

    private static void printClassInfo(ClassFile cf) {
        System.out.println("version: " + cf.majorVersion() + "." + cf.minorVersion() + ",readable version:" + JdkUtil.getVersion(cf.majorVersion()));

        System.out.println("constants size：" + cf.constantPool().getSiz());
        printSplit();
        ConstantInfo[] constantInfos = cf.constantPool().getConstantInfos();
        for (int i = 0, constantInfosLength = constantInfos.length; i < constantInfosLength; i++) {
            ConstantInfo constantInfo = constantInfos[i];
            System.out.println("constants[" + i + "]    :" + constantInfo);
        }
        printSplit();

        System.out.format("access flags：0x%x\n", cf.accessFlags());
        System.out.println("this class：" + cf.className());
        System.out.println("super class：" + cf.superClassName());
        System.out.println("interfaces：" + Arrays.toString(cf.interfaceNames()));
        System.out.println("fields count：" + cf.fields().length);
        printSplit();
        for (MemberInfo memberInfo : cf.fields()) {
            System.out.format("fieldName:%s \t\t, fieldType: %s, memberInfo: %s\n", memberInfo.name(), memberInfo.descriptor(), memberInfo);
        }
        printSplit();

        System.out.println("methods count: " + cf.methods().length);
        printSplit();
        for (MemberInfo memberInfo : cf.methods()) {
            System.out.format("methodName:%s \t\t, methodType:%s, memberInfo: %s\n", memberInfo.name(), memberInfo.descriptor(), memberInfo);
        }
        printSplit();

        System.out.println("attributes count: " + cf.attributes().length);
        printSplit();
        for (AttributeInfo memberInfo : cf.attributes()) {
            System.out.format("attribute:%s  \n", memberInfo);
        }
        printSplit();


    }


    private static void printSplit() {
        IntStream.range(0, 10).boxed().forEach(i -> {
            if (i < 9) {
                System.out.print("-");
            } else {
                System.out.println();
            }
        });
    }
}
