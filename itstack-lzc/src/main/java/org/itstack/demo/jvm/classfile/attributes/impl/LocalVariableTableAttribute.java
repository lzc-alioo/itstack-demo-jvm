package org.itstack.demo.jvm.classfile.attributes.impl;

import org.itstack.demo.jvm.classfile.ClassReader;
import org.itstack.demo.jvm.classfile.attributes.AttributeInfo;

import java.util.Arrays;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/4/26
 */
public class LocalVariableTableAttribute implements AttributeInfo {

    private LocalVariableTableEntry[] localVariableTable;

    @Override
    public void readInfo(ClassReader reader) {
        int localVariableTableLength = reader.readUint16();
        this.localVariableTable = new LocalVariableTableEntry[localVariableTableLength];
        for (int i = 0; i < localVariableTableLength; i++) {
            this.localVariableTable[i] = new LocalVariableTableEntry(reader.readUint16(), reader.readUint16(), reader.readUint16(), reader.readUint16(), reader.readUint16());
        }
    }

    static class LocalVariableTableEntry {

        private int startPC;
        private int length;
        private int nameIdx;
        private int descriptorIdx;
        private int idx;

        LocalVariableTableEntry(int startPC, int length, int nameIdx, int descriptorIdx, int idx) {
            this.startPC = startPC;
            this.length = length;
            this.nameIdx = nameIdx;
            this.descriptorIdx = descriptorIdx;
            this.idx = idx;
        }

        @Override
        public String toString() {
            return "LocalVariableTableEntry{" +
                    "startPC=" + startPC +
                    ", length=" + length +
                    ", nameIdx=" + nameIdx +
                    ", descriptorIdx=" + descriptorIdx +
                    ", idx=" + idx +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LocalVariableTableAttribute{" +
                "localVariableTable=" + Arrays.toString(localVariableTable) +
                '}';
    }
}
