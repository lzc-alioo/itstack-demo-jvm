package org.itstack.demo.jvm.instructions.references;

import org.itstack.demo.jvm.instructions.base.InstructionIndex16;
import org.itstack.demo.jvm.instructions.base.MethodInvokeLogic;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.heap.constantpool.MethodRef;
import org.itstack.demo.jvm.rtda.heap.constantpool.RunTimeConstantPool;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Method;
import org.itstack.demo.jvm.rtda.heap.methodarea.MethodLookup;
import org.itstack.demo.jvm.rtda.heap.methodarea.Object;

public class INVOKE_SPECIAL extends InstructionIndex16 {

    @Override
    public void execute(Frame frame) {
        Class currentClass = frame.method().clazz();
        RunTimeConstantPool runTimeConstantPool = currentClass.constantPool();
        MethodRef methodRef = (MethodRef) runTimeConstantPool.getConstants(this.idx);
        Class resolvedClass = methodRef.resolvedClass();
        Method resolvedMethod = methodRef.resolvedMethod();
        if ("<init>".equals(resolvedMethod.name()) && resolvedMethod.clazz() != resolvedClass) {
            throw new NoSuchMethodError();
        }
        if (resolvedMethod.isStatic()) {
            throw new IncompatibleClassChangeError();
        }

        Object ref = frame.operandStack().getRefFromTop(resolvedMethod.argSlotCount() - 1);
        //lzc modified 20220420,不注释掉这部分的话，执行构造方法时会因为ref值为null而报NullPointerException错误
//        if (null == ref) {
//            throw new NullPointerException();
//        }

        if (resolvedMethod.isProtected() &&
                resolvedMethod.clazz().isSubClassOf(currentClass) &&
                !resolvedMethod.clazz().getPackageName().equals(currentClass.getPackageName()) &&
                ref.clazz() != currentClass &&
                !ref.clazz().isSubClassOf(currentClass)) {
            throw new IllegalAccessError();
        }

        Method methodToBeInvoked = resolvedMethod;
        if (currentClass.isSuper() &&
                resolvedClass.isSubClassOf(currentClass) &&
                !resolvedMethod.name().equals("<init>")) {
            MethodLookup.lookupMethodInClass(currentClass.superClass, methodRef.name(), methodRef.descriptor());
        }

        if (methodToBeInvoked.isAbstract()) {
            throw new AbstractMethodError();
        }

        MethodInvokeLogic.invokeMethod(frame, methodToBeInvoked);

    }

}