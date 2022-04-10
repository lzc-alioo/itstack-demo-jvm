package org.itstack.demo.jvm.lzc;

import org.itstack.demo.jvm.classfile.ClassFile;
import org.itstack.demo.jvm.classfile.MemberInfo;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Field;
import org.itstack.demo.jvm.rtda.heap.methodarea.Method;

import java.util.Optional;

/**
 * @author: 悟心
 * @time: 2022/4/2 15:57
 * @description:
 */
public class Jad {


    public static void deCompile(Class clazz) {

        System.out.println("classFile:" + clazz.name);

        StringBuilder buf = new StringBuilder();

        String className = clazz.name.replaceAll("/", ".");
        String simpleClassName = className.substring(className.lastIndexOf(".")+1);
        buf.append(org.itstack.demo.jvm.lzc.AccessFlagsUtil.getAccessFlagsStr(clazz.accessFlags))
                .append(className)
                .append("\n");

        for (Field memberInfo : clazz.fields) {
            Object val = ValueUtil.getValue(memberInfo, clazz);
            buf.append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags))
                    .append(FieldDescriptorUtil.getDescriptorStr(memberInfo.descriptor()))
                    .append(memberInfo.name())
                    .append(Optional.ofNullable(val).map(tmp -> " = " + tmp).orElse(""))
                    .append("\n");

        }

        for (Method memberInfo : clazz.methods) {
            //方法参数类型 方法返回值类型
            MethodInfo methodInfo = MethodDescriptorUtil.getMethodInfo(memberInfo,simpleClassName);

            buf.append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags))
                    .append(methodInfo.getValueType()).append(" ")
                    .append(methodInfo.getMethodName())
                    .append(methodInfo.getParamType())
                    .append("\n");

        }

        System.out.println(buf.toString());
    }


//    public static void deCompile(ClassFile classFile) {
//
//        System.out.println("classFile:" + classFile.className());
//
//        StringBuilder buf = new StringBuilder();
//
//        String className = classFile.className().replaceAll("/", ".");
//        String simpleClassName = className.substring(className.lastIndexOf(".")+1);
//        buf.append(AccessFlagsUtil.getAccessFlagsStr(classFile.accessFlags()))
//                .append(className)
//                .append("\n");
//
//        for (MemberInfo memberInfo : classFile.fields()) {
//            Object val = ValueUtil.getValue(memberInfo, classFile);
//            buf.append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags()))
//                    .append(FieldDescriptorUtil.getDescriptorStr(memberInfo.descriptor()))
//                    .append(memberInfo.name())
//                    .append(Optional.ofNullable(val).map(tmp -> " = " + tmp).orElse(""))
//                    .append("\n");
//
//        }
//
//        for (MemberInfo memberInfo : classFile.methods()) {
//            //方法参数类型 方法返回值类型
//            MethodInfo methodInfo = MethodDescriptorUtil.getMethodInfo(memberInfo,simpleClassName);
//            buf.append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags()))
//                    .append(methodInfo.getValueType()).append(" ")
//                    .append(methodInfo.getMethodName())
//                    .append(methodInfo.getParamType())
//                    .append("\n");
//
//        }
//
//        System.out.println(buf.toString());
//    }
}
