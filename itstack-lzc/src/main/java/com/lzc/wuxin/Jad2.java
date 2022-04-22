package com.lzc.wuxin;

import com.lzc.wuxin.util.*;
import org.apache.commons.lang3.StringUtils;
import org.itstack.demo.jvm.Cmd;
import org.itstack.demo.jvm._native.Registry;
import org.itstack.demo.jvm.classfile.ClassInfo;
import org.itstack.demo.jvm.classfile.attributes.impl.LineNumberTableAttribute;
import org.itstack.demo.jvm.classfile.attributes.impl.LocalVariableTableAttribute;
import org.itstack.demo.jvm.classpath.Classpath;
import org.itstack.demo.jvm.instructions.Factory;
import org.itstack.demo.jvm.instructions.base.BytecodeReader;
import org.itstack.demo.jvm.instructions.base.Instruction;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.OperandStack;
import org.itstack.demo.jvm.rtda.Slot;
import org.itstack.demo.jvm.rtda.Thread;
import org.itstack.demo.jvm.rtda.heap.ClassLoader;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Field;
import org.itstack.demo.jvm.rtda.heap.methodarea.Method;

import java.util.Optional;

/**
 * @author: 悟心
 * @time: 2022/4/14 23:45
 * @description:
 */
public class Jad2 {

    public static void main(String[] args) {
        Cmd cmd = Cmd.parse(args);
        if (!cmd.ok || cmd.helpFlag) {
            System.out.println("Usage: <main class> [-options] class [args...]");
            return;
        }

        deCompile(cmd);
    }


    public static void deCompile(Cmd cmd) {
        String className = cmd.getMainClass().replace(".", "/");
        String simpleClassName = className.substring(className.lastIndexOf("/") + 1);

        ClassInfo classInfo = null;
        try {
            classInfo = new Classpath(cmd.jre, cmd.classpath).readClass2ClassInfo(className);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        System.out.println(StringUtils.rightPad("Classfile", 16) + ": " + classInfo.classFromPath);
        System.out.println(StringUtils.rightPad("  Last modified ", 16) + ": " + DateTimeUtil.toDateTimeString(classInfo.lastModified, "yyyy-MM-dd") + "; size " + classInfo.length + " bytes");
        System.out.println(StringUtils.rightPad("  MD5 checksum ", 16) + ": " + classInfo.md5);
        System.out.println(StringUtils.rightPad("  Compiled from ", 16) + ": " + simpleClassName + ".java\" (反编译工具作者：Lzc)");

        Classpath classpath = new Classpath(cmd.jre, cmd.classpath);
        ClassLoader classLoader = new ClassLoader(classpath);
        Class clazz = classLoader.loadClass(className);
        StringBuilder buf = new StringBuilder();
        buf.append(clazz.deprecated ? "@Deprecated " : "").append(AccessFlagsUtil.getAccessFlagsStr(clazz.accessFlags)).append(cmd.getMainClass()).append("\n");

        buf.append("  minor version: ").append(classInfo.classFile.minorVersion()).append("\n");
        buf.append("  major version: ").append(classInfo.classFile.majorVersion()).append("\n");
        buf.append("  flags        : ").append(AccessFlagsUtil.getAccessFlagsStr(clazz.accessFlags)).append("\n");

        System.out.print(buf.toString());
        buf.delete(0, buf.length());

        buf.append("Constant pool:\n");
        Object[] constants = clazz.constantPool().constants;
        for (int i = 0; i < constants.length; i++) {
            //   #1 = Methodref          #5.#34         // java/lang/Object."<init>":()V
            buf.append(StringUtils.leftPad("#" + i, 4)).append(" = ");
            buf.append(ConstantPoolUtil.readableConstant(constants[i]));
            buf.append("\n");
        }
        System.out.print(buf.toString());
        buf.delete(0, buf.length());

        buf.append("{\n");
        for (Field memberInfo : clazz.fields) {
            Object val = ValueUtil.getValue(memberInfo, clazz);
            buf.append("  ").append(memberInfo.deprecated ? "@Deprecated " : "").append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags)).append(FieldDescriptorUtil.getDescriptorStr(memberInfo.descriptor())).append(memberInfo.name()).append(Optional.ofNullable(val).map(tmp -> " = " + tmp).orElse("")).append(";\n");
        }
        buf.append("\n");

        for (Method method : clazz.methods) {
            //方法参数类型 方法返回值类型
            MethodInfo methodInfo = MethodDescriptorUtil.getMethodInfo(method);
            buf.append("  ").append(AccessFlagsUtil.getAccessFlagsStr(method.accessFlags)).append(methodInfo.getValueType()).append(methodInfo.getMethodName()).append(methodInfo.getParamType()).append(";\n");
            buf.append("    descriptor:").append(method.descriptor).append("\n");
            buf.append("    flags: ").append(AccessFlagsUtil.getAccessFlagsStr(clazz.accessFlags)).append("\n");

            buf.append("    Code: ").append("\n");
            buf.append("      stack=").append(method.maxStack).append(", locals=").append(method.maxLocals).append(", args_size=").append(method.argSlotCount()).append("\n");

            System.out.print(buf.toString());
            buf.delete(0, buf.length());

            Thread thread = new Thread();
            Frame frame = thread.newFrame(method);
            thread.pushFrame(frame);
            Registry.initNative();


            byte[] code = method.code;
            BytecodeReader reader=new BytecodeReader();
            reader.reset(code,0);
            while (true){
                byte opcode =reader.readByte();
                Instruction inst = Factory.newInstruction(opcode);
                inst.fetchOperands(reader);

                inst.execute(frame);

                buf.append("        ")
                        .append(InstructionUtil.readableInstruction(inst, opcode).toLowerCase())
//                        .append("    ")
//                        .append(readAbleSlots(frame.operandStack()))
                        .append("\n");

                if (thread.isStackEmpty() ||  reader.pc() > code.length-1) {
                    break;
                }

                System.out.print(buf.toString());
                buf.delete(0, buf.length());
            }


            buf.append("      LineNumberTable:").append("\n");
            for (LineNumberTableAttribute.LineNumberTableEntry lineNumber : method.lineNumberTableAttribute.lineNumberTable) {
                buf.append("        line ").append(lineNumber.lineNumber).append(": ").append(lineNumber.startPC).append("\n");
            }
            buf.append("      LocalVariableTable:").append("\n");
            buf.append("        Start  Length  Slot  Name   Signature").append("\n");
            if(method.localVariableTableAttribute!=null && method.localVariableTableAttribute.localVariableTable!=null){
                for (LocalVariableTableAttribute.LocalVariableTableEntry localVar : method.localVariableTableAttribute.localVariableTable) {
                    buf.append(StringUtils.leftPad(localVar.startPC + "", 13))
                            .append(StringUtils.leftPad(localVar.length + "", 8))
                            .append(StringUtils.leftPad(localVar.idx + "", 6))
                            .append(StringUtils.leftPad(clazz.runTimeConstantPool.getConstants(localVar.nameIdx) + "", 6)).append("   ")
                            .append(StringUtils.rightPad(clazz.runTimeConstantPool.getConstants(localVar.descriptorIdx)+"" , 14))
                            .append("\n");
                }
            }


        }
        buf.append("}");

        System.out.println(buf.toString());
    }


    private static String readAbleSlots(OperandStack operandStack){
        StringBuilder sb = new StringBuilder();
        Slot[] slots = operandStack.getSlots();
        for (int i = 0; i < operandStack.getSize(); i++) {
            Slot slot = slots[i];
            sb.append(" ").append(slot.num);
        }

        return sb.toString();

    }
}