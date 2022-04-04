package org.itstack.demo.jvm.lzc;

import org.itstack.demo.jvm.classfile.ClassFile;
import org.itstack.demo.jvm.classfile.MemberInfo;

/**
 * @author: 悟心
 * @time: 2022/4/2 15:57
 * @description:
 */
public class Jad {
    public static void deCompile(ClassFile classFile) {

        System.out.println("classFile:" + classFile.className());

        StringBuilder buf = new StringBuilder();

        buf.append(AccessFlagsUtil.getAccessFlagsStr(classFile.accessFlags()))
                .append(classFile.className().replaceAll("/","."))
                .append("\n");

        for (MemberInfo memberInfo : classFile.fields()) {
//            System.out.format("fieldName:%s \t\t, fieldType: %s, memberInfo: %s\n", memberInfo.name(), memberInfo.descriptor(), memberInfo);
            buf.append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags()))
                    .append(DescriptorUtil.getDescriptorStr(memberInfo.descriptor()))
                    .append(memberInfo.name().replaceAll("/","."))
                    .append("\n");

        }

        System.out.println(buf.toString());
    }
}
