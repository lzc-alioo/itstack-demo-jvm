package org.itstack.demo.jvm.classfile;

import org.itstack.demo.jvm.classfile.attributes.AttributeInfo;
import org.itstack.demo.jvm.classfile.attributes.impl.CodeAttribute;
import org.itstack.demo.jvm.classfile.attributes.impl.ConstantValueAttribute;
import org.itstack.demo.jvm.classfile.attributes.impl.LineNumberTableAttribute;
import org.itstack.demo.jvm.classfile.constantpool.ConstantPool;

import java.util.Arrays;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/26
 */
public class MemberInfo {

    private ConstantPool constantPool;
    private int accessFlags;
    private int nameIdx;
    private int descriptorIdx;
    private AttributeInfo[] attributes;

    private MemberInfo(ClassReader reader, ConstantPool constantPool) {
        this.constantPool = constantPool;
        this.accessFlags = reader.readUint16();
        this.nameIdx = reader.readUint16();
        this.descriptorIdx = reader.readUint16();
        this.attributes = AttributeInfo.readAttributes(reader, constantPool);
    }

    static MemberInfo[] readMembers(ClassReader reader, ConstantPool constantPool) {
        int fieldCount = reader.readUint16();
        MemberInfo[] fields = new MemberInfo[fieldCount];
        for (int i = 0; i < fieldCount; i++) {
            fields[i] = new MemberInfo(reader, constantPool);
        }
        return fields;
    }

    public int accessFlags() {
        return this.accessFlags;
    }

    public String name() {
        return this.constantPool.getUTF8(this.nameIdx);
    }

    public String descriptor() {
        return this.constantPool.getUTF8(this.descriptorIdx);
    }

    public CodeAttribute codeAttribute() {
        for (AttributeInfo attrInfo : attributes) {
            if (attrInfo instanceof CodeAttribute) return (CodeAttribute) attrInfo;
        }
        return null;
    }


    public ConstantValueAttribute constantValueAttribute() {
        for (AttributeInfo attrInfo : attributes) {
            if (attrInfo instanceof ConstantValueAttribute) return (ConstantValueAttribute) attrInfo;
        }
        return null;
    }


    public int getAccessFlags() {
        return accessFlags;
    }

    public void setAccessFlags(int accessFlags) {
        this.accessFlags = accessFlags;
    }

    public int getNameIdx() {
        return nameIdx;
    }

    public void setNameIdx(int nameIdx) {
        this.nameIdx = nameIdx;
    }

    public int getDescriptorIdx() {
        return descriptorIdx;
    }

    public void setDescriptorIdx(int descriptorIdx) {
        this.descriptorIdx = descriptorIdx;
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributeInfo[] attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "MemberInfo{" +
                "accessFlags=" + accessFlags +
                ", nameIdx=" + nameIdx +
                ", descriptorIdx=" + descriptorIdx +
                ", attributes=" + Arrays.toString(attributes) +
                ", name=" + name()+
                ", descriptor=" + descriptor()+
                '}';
    }


}
