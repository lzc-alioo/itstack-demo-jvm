package org.itstack.demo.jvm.instructions.constants.ipush;

import org.apache.commons.lang3.StringUtils;
import org.itstack.demo.jvm.instructions.InstructionUtil;
import org.itstack.demo.jvm.instructions.base.BytecodeReader;
import org.itstack.demo.jvm.instructions.base.Instruction;
import org.itstack.demo.jvm.rtda.Frame;
import org.itstack.demo.jvm.rtda.heap.constantpool.FieldRef;
import org.itstack.demo.jvm.rtda.heap.constantpool.RunTimeConstantPool;
import org.itstack.demo.jvm.rtda.heap.methodarea.Class;
import org.itstack.demo.jvm.rtda.heap.methodarea.Field;
import org.itstack.demo.jvm.rtda.heap.methodarea.Method;

//bipush指令从操作数中获取一个byte型整数，扩展成int型，然后推入栈顶。
public class BIPUSH implements Instruction {

    private byte val;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.val = reader.readByte();

    }

    @Override
    public void execute(Frame frame) {
        frame.operandStack().pushInt(val);
    }



    @Override
    public String debug( Frame frame) {

        //-15
        InstructionUtil.debug(this,frame,"" + val,"");
        return null;
    }


}
