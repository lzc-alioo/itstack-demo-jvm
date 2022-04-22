package org.itstack.demo.jvm.instructions.base;

import org.itstack.demo.jvm.instructions.InstructionUtil;
import org.itstack.demo.jvm.instructions.references.INVOKE_STATIC;
import org.itstack.demo.jvm.instructions.references.PUT_STATIC;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.heap.constantpool.FieldRef;
import org.itstack.demo.jvm.rtda.heap.constantpool.MethodRef;
import org.itstack.demo.jvm.rtda.heap.constantpool.RunTimeConstantPool;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Field;
import org.itstack.demo.jvm.rtda.heap.methodarea.Method;

public class InstructionIndex16 implements Instruction {

    protected int idx;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.idx = reader.readShort();


    }

    @Override
    public void execute(Frame frame) {

    }

    @Override
    public String debug(Frame frame) {
        //#3                  // Field abc2:Ljava/lang/Byte;
        //#2                  // Method java/lang/Byte.valueOf:(B)Ljava/lang/Byte;
        if (this instanceof PUT_STATIC) {
            Method currentMethod = frame.method();
            Class currentClazz = currentMethod.clazz();
            RunTimeConstantPool runTimeConstantPool = currentClazz.constantPool();
            FieldRef fieldRef = (FieldRef) runTimeConstantPool.getConstants(this.idx);
            Field field = fieldRef.resolvedField();

            InstructionUtil.debug(this, frame, "#" + idx, field.toString());
        } else if (this instanceof INVOKE_STATIC) {
            RunTimeConstantPool runTimeConstantPool = frame.method().clazz().constantPool();
            MethodRef methodRef = (MethodRef) runTimeConstantPool.getConstants(this.idx);
            Method resolvedMethod = methodRef.resolvedMethod();

            InstructionUtil.debug(this, frame, "#" + idx, resolvedMethod.toString());
        }

        return null;
    }

}
