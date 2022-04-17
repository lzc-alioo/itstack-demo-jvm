package org.itstack.demo.jvm;

import org.apache.commons.lang3.StringUtils;
import org.itstack.demo.jvm.classfile.ClassFile;
import org.itstack.demo.jvm.classfile.MemberInfo;
import org.itstack.demo.jvm.classfile.attributes.AttributeInfo;
import org.itstack.demo.jvm.classfile.attributes.impl.CodeAttribute;
import org.itstack.demo.jvm.classfile.attributes.impl.LineNumberTableAttribute;
import org.itstack.demo.jvm.classfile.attributes.impl.LocalVariableTableAttribute;
import org.itstack.demo.jvm.classfile.constantpool.ConstantInfo;
import org.itstack.demo.jvm.classpath.Classpath;
import org.itstack.demo.jvm.instructions.Factory;
import org.itstack.demo.jvm.instructions.base.Instruction;
import org.itstack.demo.jvm.lzc.Jad;
import org.itstack.demo.jvm.lzc.JdkVersionUtil;
import org.itstack.demo.jvm.rtda.heap.ClassLoader;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Method;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/24
 * -Xjre "C:\Program Files\Java\jdk1.8.0_161\jre" E:\itstack\git\istack-demo\itstack-demo-jvm\itstack-demo-jvm-10\target\test-classes\org\itstack\demo\test\HelloWorld -verbose
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
        ClassLoader classLoader = new ClassLoader(classpath);
        //获取className
        String className = cmd.getMainClass().replace(".", "/");
        Class mainClass = classLoader.loadClass(className);
        Method mainMethod = mainClass.getMainMethod();
        if (null == mainMethod) {
            throw new RuntimeException("Main method not found in class " + cmd.getMainClass());
        }
        new Interpret(mainMethod, cmd.verboseClassFlag, cmd.args);

        ClassFile classFile = loadClass(className, classpath);
        printClassInfo(classFile);
        Jad.deCompile(mainClass);
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
        System.out.println("major_version.minor_version: " + cf.majorVersion() + "." + cf.minorVersion() + ",readable version:" + JdkVersionUtil.getVersion(cf.majorVersion()));

        System.out.println("constant_pool_count size：" + cf.constantPool().getSize());
        printSplit(20);
        ConstantInfo[] constantInfos = cf.constantPool().getConstantInfos();
        for (int i = 0, constantInfosLength = constantInfos.length; i < constantInfosLength; i++) {
            ConstantInfo constantInfo = constantInfos[i];

            StringBuilder buf = new StringBuilder();
            buf.append("[").append(StringUtils.leftPad("" + i, 2, ' ')).append("]");
            buf.append("    ");
            buf.append(constantInfo);
            System.out.println(buf.toString());
        }
        printSplit(20);

        System.out.format("access flags：0x%x\n", cf.accessFlags());
        System.out.println("this class：" + cf.className());
        System.out.println("super class：" + cf.superClassName());
        System.out.println("interfaces：" + Arrays.toString(cf.interfaceNames()));
        System.out.println("fields count：" + cf.fields().length);
        printSplit(20);
        MemberInfo[] fields = cf.fields();
        for (int i = 0, fieldsLength = fields.length; i < fieldsLength; i++) {
            MemberInfo memberInfo = fields[i];

            StringBuilder buf = new StringBuilder();
            buf.append("[").append(StringUtils.rightPad("" + i, 1, '0')).append("]");
            buf.append("    ");
            buf.append(StringUtils.rightPad("fieldName:" + memberInfo.name(), 16));
            buf.append(StringUtils.rightPad(",fieldType:" + memberInfo.descriptor(), 30));
//            buf.append(JSON.toJSONString(memberInfo));
            buf.append(memberInfo);
            System.out.println(buf.toString());


        }
        printSplit(20);

        System.out.println("methods count: " + cf.methods().length);
        printSplit(20);
        MemberInfo[] methods = cf.methods();
        for (int i = 0, methodsLength = methods.length; i < methodsLength; i++) {
            MemberInfo memberInfo = methods[i];

            StringBuilder buf = new StringBuilder();
            buf.append("[").append(StringUtils.rightPad("" + i, 1, '0')).append("]");
            buf.append("    ");
            buf.append(StringUtils.rightPad("methodName:" + memberInfo.name(), 20));
            buf.append(StringUtils.rightPad(",methodType:" + memberInfo.descriptor(), 40));
            buf.append(memberInfo);
            System.out.println(buf.toString());

        }
        printSplit(20);
        for (int i = 0, methodsLength = methods.length; i < methodsLength; i++) {
            MemberInfo memberInfo = methods[i];

            StringBuilder buf = new StringBuilder();
            buf.append("[").append(StringUtils.rightPad("" + i, 1, '0')).append("]");
            buf.append("    ");
            buf.append(StringUtils.rightPad("methodName:" + memberInfo.name(), 20));
            buf.append(StringUtils.rightPad(",methodType:" + memberInfo.descriptor(), 40));
            buf.append("\n");

            StringBuilder codesub = new StringBuilder("Code\n");
            CodeAttribute codeAttribute = memberInfo.codeAttribute();
            codesub.append("  maxStack:").append(codeAttribute.maxStack).append("    ").append("maxLocals:").append(codeAttribute.maxLocals).append("    \n");
            byte[] data = codeAttribute.data;
            for (int j = 0; j < data.length; j++) {
                Instruction instruction = Factory.newInstruction(data[j]);
                String instructionClass = Optional.ofNullable(instruction).map(obj -> obj.toString()).map(obj -> obj.substring(obj.lastIndexOf(".") + 1).split("@")[0]).orElse(data[j] + "-UNKOWN ");
                codesub.append("    ").append(instructionClass).append("\n");
            }
            buf.append(codesub.toString());


            {
                StringBuilder linesub = new StringBuilder("  LineNumber\n");
                LineNumberTableAttribute lineNumberTableAttribute = codeAttribute.lineNumberTableAttribute();
                LineNumberTableAttribute.LineNumberTableEntry[] lineNumberTable = Optional.ofNullable(lineNumberTableAttribute)
                        .map(LineNumberTableAttribute::getLineNumberTable)
                        .orElse(new LineNumberTableAttribute.LineNumberTableEntry[0]);

                linesub.append("    ").append(StringUtils.rightPad("startPC",20));
                linesub.append(StringUtils.rightPad("lineNumber",10)).append("\n");
                for (int j = 0; j < lineNumberTable.length; j++) {
                    linesub.append("    ");
                    linesub.append(StringUtils.rightPad(lineNumberTable[j].startPC+"",20));
                    linesub.append(StringUtils.rightPad(lineNumberTable[j].lineNumber+"",20));
                    linesub.append("\n");
                }
                buf.append(linesub.toString());
            }

            {
                StringBuilder localVariablesub = new StringBuilder("  LocalVariable\n");
                LocalVariableTableAttribute localVariableTableAttribute = codeAttribute.localVariableTableAttribute();
                LocalVariableTableAttribute.LocalVariableTableEntry[] localVariable = Optional.ofNullable(localVariableTableAttribute)
                        .map(LocalVariableTableAttribute::getLocalVariableTable)
                        .orElse(new LocalVariableTableAttribute.LocalVariableTableEntry[0]);
                localVariablesub.append("    ");
                localVariablesub.append(StringUtils.rightPad("startPC",10));
                localVariablesub.append(StringUtils.rightPad("length",10));
                localVariablesub.append(StringUtils.rightPad("name",10));
                localVariablesub.append(StringUtils.rightPad("descriptor",20));
                localVariablesub.append("\n");

                for (int j = 0; j < localVariable.length; j++) {
                    localVariablesub.append("     ");
                    localVariablesub.append(StringUtils.rightPad(localVariable[j].length+"", 10));
                    localVariablesub.append(StringUtils.rightPad(localVariable[j].startPC+"", 10));
                    localVariablesub.append(StringUtils.rightPad(cf.constantPool().getUTF8(localVariable[j].nameIdx), 10));
                    localVariablesub.append(StringUtils.rightPad(cf.constantPool().getUTF8(localVariable[j].descriptorIdx), 40));
                    localVariablesub.append("\n");
                }
                buf.append(localVariablesub.toString());
            }


            System.out.println(buf.toString());
        }
        printSplit(20);

        System.out.println("attributes count: " + cf.attributes.length);
        printSplit(20);
        for (AttributeInfo memberInfo : cf.attributes) {
            System.out.format("attribute:%s  \n", memberInfo);
        }
        printSplit(20);


    }


    private static void printSplit(int n) {
        IntStream.range(0, n).boxed().forEach(i -> {
            if (i < n - 1) {
                System.out.print("-");
            } else {
                System.out.println();
            }
        });
    }
}
