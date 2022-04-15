package org.itstack.demo.jvm.rtda.heap.constantpool;

import org.itstack.demo.jvm.classfile.constantpool.impl.ConstantStringInfo;

public class StringRef extends SymRef {
    public int strIdx;
    public String  str;

    public static StringRef newStringRef(RunTimeConstantPool runTimeConstantPool, ConstantStringInfo classInfo) {
        StringRef ref = new StringRef();
        ref.runTimeConstantPool = runTimeConstantPool;
        ref.strIdx = classInfo.strIdx;
        ref.str = classInfo.string();
        return ref;
    }

}