package org.itstack.demo.jvm.lzc;

import org.itstack.demo.jvm.classfile.ClassFile;
import org.itstack.demo.jvm.classfile.MemberInfo;
import org.itstack.demo.jvm.classfile.attributes.impl.ConstantValueAttribute;
import org.itstack.demo.jvm.classfile.constantpool.ConstantInfo;
import org.itstack.demo.jvm.classfile.constantpool.impl.*;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Field;

import static org.itstack.demo.jvm.classfile.constantpool.ConstantInfo.*;

/**
 * @author: 悟心
 * @time: 2022/4/2 17:42
 * @description:
 */
public class ValueUtil {

    public static Object getValue(Field field, Class clazz) {
        if (field.isStatic() && field.isFinal()) {
//            clazz.staticVars.
        }

       return clazz.runTimeConstantPool.getConstants(field.constValueIndex);
    }



//    public static Object getValue(MemberInfo memberInfo, ClassFile classFile) {
//        ConstantValueAttribute constantValueAttribute = memberInfo.ConstantValueAttribute();
//        Object val = null;
//        if (constantValueAttribute != null) {
//            ConstantInfo constantInfo = classFile.constantPool().getConstantInfos()[constantValueAttribute.constantValueIdx()];
//            val = getValue(constantInfo);
//        }
//        return val;
//    }
//
//    public static Object getValue(ConstantInfo constantInfo) {
//        int tag = constantInfo.tag();
//        switch (tag) {
//            case CONSTANT_TAG_INTEGER:
//                return ((ConstantIntegerInfo) constantInfo).value();
//            case CONSTANT_TAG_FLOAT:
//                return ((ConstantFloatInfo) constantInfo).value();
//            case CONSTANT_TAG_LONG:
//                return ((ConstantLongInfo) constantInfo).value();
//            case CONSTANT_TAG_DOUBLE:
//                return ((ConstantDoubleInfo) constantInfo).value();
//            case CONSTANT_TAG_UTF8:
//                return ((ConstantUtf8Info) constantInfo).str();
//            case CONSTANT_TAG_STRING:
//                return ((ConstantStringInfo) constantInfo).string();
////            case CONSTANT_TAG_CLASS:
////                return  ((ConstantClassInfo) constantInfo).name();
////            case CONSTANT_TAG_FIELDREF:
////                return ((ConstantFieldRefInfo) constantInfo).nameAndDescriptor();
////            case CONSTANT_TAG_METHODREF:
////                return ((ConstantMethodRefInfo) constantInfo).nameAndDescriptor();
////            case CONSTANT_TAG_INTERFACEMETHODREF:
////                return ((ConstantInterfaceMethodRefInfo) constantInfo).nameAndDescriptor() ;
////            case CONSTANT_TAG_NAMEANDTYPE:
////                return ((ConstantNameAndTypeInfo) constantInfo);
////            case CONSTANT_TAG_METHODTYPE:
////                return ((ConstantMethodTypeInfo) constantInfo);
////            case CONSTANT_TAG_METHODHANDLE:
////                return ((ConstantMethodHandleInfo) constantInfo);
////            case CONSTANT_TAG_INVOKEDYNAMIC:
////                return ((ConstantInvokeDynamicInfo) constantInfo);
//            default:
//                throw new ClassFormatError("constant pool tag");
//
//        }
//
//    }
}
