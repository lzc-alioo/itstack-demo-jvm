package org.itstack.demo.jvm.rtda.heap.methodarea;

import org.itstack.demo.jvm.classfile.MemberInfo;
import org.itstack.demo.jvm.classfile.attributes.AttributeInfo;
import org.itstack.demo.jvm.classfile.attributes.impl.ConstantValueAttribute;
import org.itstack.demo.jvm.classfile.attributes.impl.DeprecatedAttribute;
import org.itstack.demo.jvm.rtda.heap.constantpool.AccessFlags;

public class Field extends ClassMember {

    public int constValueIndex;
    public int slotId;
    public boolean deprecated;


    public Field[] newFields(Class clazz, MemberInfo[] cfFields) {
        Field[] fields = new Field[cfFields.length];
        for (int i = 0; i < cfFields.length; i++) {
            fields[i] = new Field();
            fields[i].clazz = clazz;
            fields[i].copyMemberInfo(cfFields[i]);
            fields[i].copyAttributes(cfFields[i]);
        }
        return fields;
    }

    public void copyAttributes(MemberInfo cfField) {
        ConstantValueAttribute valAttr = cfField.constantValueAttribute();
        if (null != valAttr) {
            this.constValueIndex = valAttr.constantValueIdx();
        }
        //lzc add 标记下过时的方法
        for(AttributeInfo attributeInfo:cfField.getAttributes()){
            if(attributeInfo instanceof DeprecatedAttribute){
                this.deprecated=true;
            }
        }
    }

    public boolean isVolatile() {
        return 0 != (this.accessFlags & AccessFlags.ACC_VOLATILE);
    }

    public boolean isTransient() {
        return 0 != (this.accessFlags & AccessFlags.ACC_TRANSIENT);
    }

    public boolean isEnum() {
        return 0 != (this.accessFlags & AccessFlags.ACC_ENUM);
    }

    public int constValueIndex() {
        return this.constValueIndex;
    }

    public int slotId() {
        return this.slotId;
    }

    public boolean isLongOrDouble() {
        return this.descriptor.equals("J") || this.descriptor.equals("D");
    }

}
