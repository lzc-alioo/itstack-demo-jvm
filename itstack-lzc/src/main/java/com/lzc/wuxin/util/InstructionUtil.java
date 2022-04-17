package com.lzc.wuxin.util;

import org.itstack.demo.jvm.instructions.base.Instruction;

/**
 * @author: 悟心
 * @time: 2022/4/17 20:55
 * @description:
 */
public class InstructionUtil {
    public static String readableInstruction(Instruction inst ){
        if(inst==null){
            return "UNKOWN";
        }
        return inst.getClass().getSimpleName();
    }
}
