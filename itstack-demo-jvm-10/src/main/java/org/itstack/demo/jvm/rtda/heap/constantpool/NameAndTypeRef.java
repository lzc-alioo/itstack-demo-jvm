package org.itstack.demo.jvm.rtda.heap.constantpool;

import org.itstack.demo.jvm.classfile.constantpool.ConstantPool;
import org.itstack.demo.jvm.classfile.constantpool.impl.ConstantNameAndTypeInfo;

public class NameAndTypeRef extends MemberRef {

    public int nameIdx;
    public int descIdx;


    public static NameAndTypeRef newNameAndTypeRef(ConstantPool constantPool, ConstantNameAndTypeInfo refInfo){
        NameAndTypeRef ref = new NameAndTypeRef();
        ref.nameIdx = refInfo.nameIdx;
        ref.descIdx = refInfo.descIdx;

        ref.name = constantPool.getUTF8(refInfo.nameIdx);
        ref.descriptor = constantPool.getUTF8(refInfo.descIdx);

        return ref;
    }



}
