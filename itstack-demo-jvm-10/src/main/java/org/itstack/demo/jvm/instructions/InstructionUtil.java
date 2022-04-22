package org.itstack.demo.jvm.instructions;

import org.apache.commons.lang3.StringUtils;
import org.itstack.demo.jvm.instructions.base.Instruction;
import org.itstack.demo.jvm.rtda.Frame;

/**
 * @author: 悟心
 * @time: 2022/4/21 20:57
 * @description:
 */
public class InstructionUtil {
    public static   String debugClassName = "com/lzc/wuxin/demo/HelloWorld";
    public static   String lastInstruction = null;


    public static String debug(Instruction instruction, Frame frame,String idxOrVal, String desc) {
//        if(!frame.method().clazz.name.equals(debugClassName)){
//            return;
//        }
//        if(instruction.getClass().getSimpleName().toLowerCase().equals(lastInstruction)){
//            return;
//        }
        //-15
        //#3                  // Field abc2:Ljava/lang/Byte;
        //#2                  // Method java/lang/Byte.valueOf:(B)Ljava/lang/Byte;
        StringBuilder sb = new StringBuilder();
        sb.append("        ")
                .append(StringUtils.rightPad(instruction.getClass().getSimpleName().toLowerCase(), 20))
                .append(StringUtils.rightPad(idxOrVal, 10))
//                .append(StringUtils.rightPad(frame.method().toString(), 60))
                .append(desc);
        System.out.println(sb.toString());

//        lastInstruction=instruction.getClass().getSimpleName().toLowerCase();

        return null;
    }
}
