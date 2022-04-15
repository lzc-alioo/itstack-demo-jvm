package org.itstack.demo.test;

import org.itstack.demo.jvm.lzc.MethodDescriptorUtil;
import org.junit.Test;

/**
 * @author: 悟心
 * @time: 2022/4/4 21:45
 * @description:
 */
public class MethodDescriptorUtilTest {

    @Test
    public void getDescriptorStrTest() {
        String simpleClassName = "HelloWorld";
        System.out.println(MethodDescriptorUtil.getMethodInfo("<init>", "()V", simpleClassName,true));
        System.out.println(MethodDescriptorUtil.getMethodInfo("main", "([Ljava/lang/String;)V", simpleClassName,false));
        System.out.println(MethodDescriptorUtil.getMethodInfo("<clinit>", "()V", simpleClassName,false));


    }
}