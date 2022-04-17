package com.lzc.wuxin.test;

import com.lzc.wuxin.util.MethodDescriptorUtil;
import org.itstack.demo.jvm.rtda.heap.constantpool.AccessFlags;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Method;
import org.junit.Test;

/**
 * @author: 悟心
 * @time: 2022/4/4 21:45
 * @description:
 */
public class MethodDescriptorUtilTest {

    @Test
    public void getDescriptorStrTest() {
        String className = "org/itstack/demo/test/HelloWorld";
        System.out.println(MethodDescriptorUtil.getMethodInfo(this.mockMethod("<init>", "()V", className)));
        System.out.println(MethodDescriptorUtil.getMethodInfo(this.mockMethod("main", "([Ljava/lang/String;)V", className)));
        System.out.println(MethodDescriptorUtil.getMethodInfo(this.mockMethod("<clinit>", "()V", className)));


    }

    private Method mockMethod(String methodName, String descriptor, String className) {
        Method method = new Method();
        method.name = methodName;
        method.descriptor = descriptor;
        method.clazz = new Class();
        method.clazz.name = className;


        method.clazz.methods = new Method[1];
        method.clazz.methods[0] = new Method();
        Method cinitMethod= method.clazz.methods[0];
        cinitMethod.accessFlags= AccessFlags.ACC_STATIC;
        cinitMethod.name = "<clinit>";
        cinitMethod.descriptor="()V";

        return method;
    }
}