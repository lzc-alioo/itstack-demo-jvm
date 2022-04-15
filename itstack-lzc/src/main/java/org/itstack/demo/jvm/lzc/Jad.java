package org.itstack.demo.jvm.lzc;

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
        String simpleClassName = className.substring(className.lastIndexOf(".") + 1);
        buf.append(clazz.deprecated ? "@Deprecated " : "")
                .append(org.itstack.demo.jvm.lzc.AccessFlagsUtil.getAccessFlagsStr(clazz.accessFlags))
                .append(className)
                .append("\n");

        for (Field memberInfo : clazz.fields) {
            Object val = ValueUtil.getValue(memberInfo, clazz);
            buf.append(memberInfo.deprecated ? "@Deprecated " : "")
                .append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags))
                .append(FieldDescriptorUtil.getDescriptorStr(memberInfo.descriptor()))
                .append(memberInfo.name())
                .append(Optional.ofNullable(val).map(tmp -> " = " + tmp).orElse(""))
                .append("\n");

        }

        Method clinitMethod = clazz.getClinitMethod();
        for (Method memberInfo : clazz.methods) {
            //方法参数类型 方法返回值类型
            MethodInfo methodInfo = MethodDescriptorUtil.getMethodInfo(memberInfo, simpleClassName,clinitMethod.equals(memberInfo));
            buf.append(memberInfo.deprecated ? "@Deprecated " : "")
                    .append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags))
                    .append(methodInfo.getValueType())
                    .append(methodInfo.getMethodName())
                    .append(methodInfo.getParamType())
                    .append("\n");

        }

        System.out.println(buf.toString());
    }


}
