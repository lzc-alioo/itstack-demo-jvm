package org.itstack.demo.jvm.lzc;

import org.apache.commons.lang3.StringUtils;
import org.itstack.demo.jvm.Cmd;
import org.itstack.demo.jvm.classfile.ClassFile;
import org.itstack.demo.jvm.classpath.Classpath;
import org.itstack.demo.jvm.rtda.heap.ClassLoader;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Field;
import org.itstack.demo.jvm.rtda.heap.methodarea.Method;

import java.io.File;
import java.util.Optional;

/**
 * @author: 悟心
 * @time: 2022/4/14 23:45
 * @description:
 */
public class Lzc {

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
//        System.out.printf("classpath：%s class：%s args：%s\n", classpath, cmd.getMainClass(), cmd.getAppArgs());
        //获取className
        String className = cmd.getMainClass().replace(".", "/");

        ClassLoader classLoader = new ClassLoader(classpath);
        Class clazz = classLoader.loadClass(className);

        try {
            String path = "/Users/mac/work/gitstudy/itstack-demo-jvm/itstack-lzc/target/test-classes/";
            long lastModified=new File(path+className+ ".class").lastModified();
            long length=new File(path+className+ ".class").length();
            byte[] data = classpath.readClass(className);
            String md5=Md5Util.encode(data);

            System.out.println("Last modified "+DateTimeUtil.toDateTimeString(lastModified,"yyyy-MM-dd")+"; size "+length+" bytes");
            System.out.println("MD5 checksum "+md5);
        } catch (Exception e) {
            e.printStackTrace();
        }


        lzcpv(clazz);

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


    public static void lzcp(Class clazz) {
        String className = clazz.name.replaceAll("/", ".");
        String simpleClassName = className.substring(className.lastIndexOf(".") + 1);

        System.out.println("Compiled from \"" + simpleClassName + ".java\" (反编译工具作者：Lzc)");

        StringBuilder buf = new StringBuilder();
        buf.append(clazz.deprecated ? "@Deprecated " : "")
                .append(AccessFlagsUtil.getAccessFlagsStr(clazz.accessFlags))
                .append(className)
                .append(" {\n");

        for (Field memberInfo : clazz.fields) {
            Object val = ValueUtil.getValue(memberInfo, clazz);
            buf.append("    ")
                    .append(memberInfo.deprecated ? "@Deprecated " : "")
                    .append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags))
                    .append(FieldDescriptorUtil.getDescriptorStr(memberInfo.descriptor()))
                    .append(memberInfo.name())
                    .append(Optional.ofNullable(val).map(tmp -> " = " + tmp).orElse(""))
                    .append(";\n");
        }
        buf.append("\n");

        Method clinitMethod = clazz.getClinitMethod();
        for (Method memberInfo : clazz.methods) {
            //方法参数类型 方法返回值类型
            MethodInfo methodInfo = MethodDescriptorUtil.getMethodInfo(memberInfo, simpleClassName,clinitMethod.equals(memberInfo));
            buf.append("    ")
                    .append(memberInfo.deprecated ? "@Deprecated " : "")
                    .append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags))
                    .append(methodInfo.getValueType())
                    .append(methodInfo.getMethodName())
                    .append(methodInfo.getParamType())
                    .append(";\n");

        }
        buf.append("}");

        System.out.println(buf.toString());
    }

    public static void lzcpv(Class clazz) {
        String className = clazz.name.replaceAll("/", ".");
        String simpleClassName = className.substring(className.lastIndexOf(".") + 1);


        System.out.println("Compiled from \"" + simpleClassName + ".java\" (反编译工具作者：Lzc)");

        StringBuilder buf = new StringBuilder();
        buf.append(clazz.deprecated ? "@Deprecated " : "")
                .append(AccessFlagsUtil.getAccessFlagsStr(clazz.accessFlags))
                .append(className)
                .append(" {\n");

        System.out.println(buf.toString());
        buf.delete(0, buf.length());

        buf.append("Constant pool:\n");
        Object[] constants = clazz.constantPool().constants;
        for (int i = 0; i < constants.length; i++) {
            //   #1 = Methodref          #5.#34         // java/lang/Object."<init>":()V
            buf.append(StringUtils.leftPad("#"+i,4)).append(" = ");
            buf.append(ConstantPoolUtil.readableConstant(constants[i]));
            buf.append("\n");
        }
        System.out.println(buf.toString());
        buf.delete(0, buf.length());


        for (Field memberInfo : clazz.fields) {
            Object val = ValueUtil.getValue(memberInfo, clazz);
            buf.append("    ")
                    .append(memberInfo.deprecated ? "@Deprecated " : "")
                    .append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags))
                    .append(FieldDescriptorUtil.getDescriptorStr(memberInfo.descriptor()))
                    .append(memberInfo.name())
                    .append(Optional.ofNullable(val).map(tmp -> " = " + tmp).orElse(""))
                    .append(";\n");
        }
        buf.append("\n");

        Method clinitMethod = clazz.getClinitMethod();
        for (Method memberInfo : clazz.methods) {
            //方法参数类型 方法返回值类型
            MethodInfo methodInfo = MethodDescriptorUtil.getMethodInfo(memberInfo, simpleClassName,clinitMethod.equals(memberInfo));
            buf.append("    ")
//                    .append(memberInfo.deprecated ? "@Deprecated " : "")
                    .append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags))
                    .append(methodInfo.getValueType())
                    .append(methodInfo.getMethodName())
                    .append(methodInfo.getParamType())
                    .append(";\n");

        }
        buf.append("}");

        System.out.println(buf.toString());
    }
}