package org.itstack.demo.jvm.classfile.attributes.impl;

import org.itstack.demo.jvm.classfile.ClassReader;
import org.itstack.demo.jvm.classfile.attributes.AttributeInfo;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/26
 */
public class LocalVariableTableAttribute implements AttributeInfo {

    public LocalVariableTableEntry[] localVariableTable;

    @Override
    public void readInfo(ClassReader reader) {
        int localVariableTableLength = reader.readUint16();
        this.localVariableTable = new LocalVariableTableEntry[localVariableTableLength];
        for (int i = 0; i < localVariableTableLength; i++) {
            this.localVariableTable[i] = new LocalVariableTableEntry(reader.readUint16(), reader.readUint16(), reader.readUint16(), reader.readUint16(), reader.readUint16());
        }
    }

    public LocalVariableTableEntry[] getLocalVariableTable() {
        return localVariableTable;
    }

    public static class LocalVariableTableEntry {

       public int startPC;
       public int length;
       public int nameIdx;
       public int descriptorIdx;
       public int idx;

        LocalVariableTableEntry(int startPC, int length, int nameIdx, int descriptorIdx, int idx) {
            this.startPC = startPC;
            this.length = length;
            this.nameIdx = nameIdx;
            this.descriptorIdx = descriptorIdx;
            this.idx = idx;
        }
    }

}
