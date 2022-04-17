package org.itstack.demo.jvm.lzc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author: 悟心
 * @time: 2022/4/4 21:46
 * @description:
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MethodInfo {
   private String methodName ;
   private boolean isConstruction ;
   private String paramType ;
   private String valueType ;

}
