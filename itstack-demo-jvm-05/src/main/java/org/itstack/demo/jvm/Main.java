package org.itstack.demo.jvm;

import org.itstack.demo.jvm.classfile.ClassFile;
import org.itstack.demo.jvm.classfile.MemberInfo;
import org.itstack.demo.jvm.classpath.Classpath;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.LocalVars;
import org.itstack.demo.jvm.rtda.OperandStack;

import java.util.Arrays;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/24
 * -Xjre "C:\Program Files\Java\jdk1.8.0_161\jre" E:\itstack\git\istack-demo\itstack-demo-jvm\itstack-demo-jvm-05\target\test-classes\org\itstack\demo\test\HelloWorld
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
        System.out.printf("classpath:%s class:%s args:%s\n", classpath, cmd.getMainClass(), cmd.getAppArgs());
        String className = cmd.getMainClass().replace(".", "/");
        ClassFile classFile = loadClass(className, classpath);
        printClassInfo(classFile);

        MemberInfo mainMethod = getMainMethod(classFile);
        if (null == mainMethod) {
            System.out.println("Main method not found in class " + cmd.classpath);
            return;
        }
        new Interpret(mainMethod);
    }

    private static void printClassInfo(ClassFile cf) {
        System.out.println("version: " + cf.majorVersion() + "." + cf.minorVersion());
        System.out.println("constants count：" + cf.constantPool().getSize());
        System.out.format("access flags：0x%x\n", cf.accessFlags());
        System.out.println("this class：" + cf.className());
        System.out.println("super class：" + cf.superClassName());
        System.out.println("interfaces：" + Arrays.toString(cf.interfaceNames()));
        System.out.println("fields count：" + cf.fields().length);
        for (MemberInfo memberInfo : cf.fields()) {
            System.out.format("%s \t\t %s\n", memberInfo.name(), memberInfo.descriptor());
        }

        System.out.println("methods count: " + cf.methods().length);
        for (MemberInfo memberInfo : cf.methods()) {
            System.out.format("%s \t\t %s\n", memberInfo.name(), memberInfo.descriptor());
        }
    }

    private static ClassFile loadClass(String className, Classpath cp) {
        try {
            byte[] classData = cp.readClass(className);
            return new ClassFile(classData);
        } catch (Exception e) {
            System.out.println("Could not find or load main class " + className);
            e.printStackTrace();
        }
        return null;
    }

    //找到主函数入口方法
    private static MemberInfo getMainMethod(ClassFile cf) {
        if (null == cf) return null;
        MemberInfo[] methods = cf.methods();
        for (MemberInfo m : methods) {
            if ("main".equals(m.name()) && "([Ljava/lang/String;)V".equals(m.descriptor())) {
                return m;
            }
        }
        return null;
    }

}
