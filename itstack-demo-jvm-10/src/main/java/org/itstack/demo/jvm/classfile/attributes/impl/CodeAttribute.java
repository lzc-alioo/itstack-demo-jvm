package org.itstack.demo.jvm.classfile.attributes.impl;

import org.itstack.demo.jvm.classfile.ClassReader;
import org.itstack.demo.jvm.classfile.attributes.AttributeInfo;
import org.itstack.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/26
 */
public class CodeAttribute implements AttributeInfo {

    private ConstantPool constantPool;
    /**
     * maxStack和maxLocals字段分别存放操作数栈和局部变量表大小，这两个值是由Java编译器计算好的。
     */
    public int maxStack;
    public int maxLocals;
    /**
     * code字段存放方法字节码。
     */
    public byte[] data;
    public ExceptionTableEntry[] exceptionTable;
    public AttributeInfo[] attributes;

    public CodeAttribute(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.maxStack = reader.readUint16();
        this.maxLocals = reader.readUint16();
        int dataLength = reader.readUint32TInteger();
        this.data = reader.readBytes(dataLength);
        this.exceptionTable = ExceptionTableEntry.readExceptionTable(reader);
        this.attributes = AttributeInfo.readAttributes(reader, this.constantPool);
    }

    public int maxStack() {
        return this.maxStack;
    }

    public int maxLocals() {
        return this.maxLocals;
    }

    public byte[] data() {
        return this.data;
    }

    public ExceptionTableEntry[] exceptionTable() {
        return this.exceptionTable;
    }

    public LineNumberTableAttribute lineNumberTableAttribute() {
        for (AttributeInfo attrInfo : this.attributes) {
            if (attrInfo instanceof LineNumberTableAttribute) {
                return (LineNumberTableAttribute) attrInfo;
            }
        }
        return null;
    }

    public LocalVariableTableAttribute localVariableTableAttribute() {
        for (AttributeInfo attrInfo : attributes) {
            if (attrInfo instanceof LocalVariableTableAttribute) {
                return (LocalVariableTableAttribute) attrInfo;
            }
        }
        return null;
    }

    public AttributeInfo[] attributes() {
        return this.attributes;
    }

    public static class ExceptionTableEntry {

        private int startPC;
        private int endPC;
        private int handlerPC;
        private int catchType;

        ExceptionTableEntry(int startPC, int endPC, int handlerPC, int catchType) {
            this.startPC = startPC;
            this.endPC = endPC;
            this.handlerPC = handlerPC;
            this.catchType = catchType;
        }

        static ExceptionTableEntry[] readExceptionTable(ClassReader reader) {
            int exceptionTableLength = reader.readUint16();
            ExceptionTableEntry[] exceptionTable = new ExceptionTableEntry[exceptionTableLength];
            for (int i = 0; i < exceptionTableLength; i++) {
                exceptionTable[i] = new ExceptionTableEntry(reader.readUint16(), reader.readUint16(), reader.readUint16(), reader.readUint16());
            }
            return exceptionTable;
        }

        public int startPC() {
            return this.startPC;
        }

        public int endPC() {
            return this.endPC;
        }

        public int handlerPC() {
            return this.handlerPC;
        }

        public int catchType() {
            return this.catchType;
        }

    }

}
