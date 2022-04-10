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
        System.out.println(MethodDescriptorUtil.getMethodInfo("", "()V", simpleClassName));
        System.out.println(MethodDescriptorUtil.getMethodInfo("", "([Ljava/lang/String;)V", simpleClassName));
        System.out.println(MethodDescriptorUtil.getMethodInfo("", "()V", simpleClassName));


    }
}