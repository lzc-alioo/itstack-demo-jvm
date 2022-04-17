
纸上得来终觉浅，绝知此事要躬行，今天就动手实现一个jdk自带的工具javap

# 准备工作
## 用来测试的java文件 HelloWorld.java
```java
package com.lzc.wuxin.demo;

public class HelloWorld {

    private final static byte abc = -14;
    private final static Byte abc2 = Byte.valueOf((byte) -15);
    private static int lzc;

    public static void main(String[] args) {
        int lzc2 = 2;
        int lzc3 = 3;
        int lzc4 = lzc2 + lzc3;
    }

}


```

## javap VS jad1
javap效果
```
javap com.lzc.wuxin.demo.HelloWorld
Compiled from "HelloWorld.java"
public class com.lzc.wuxin.demo.HelloWorld {
  public com.lzc.wuxin.demo.HelloWorld();
  public static void main(java.lang.String[]);
  static {};
}
```

jad1效果
```
Compiled from "HelloWorld.java" (反编译工具作者：Lzc)
public class com.lzc.wuxin.demo.HelloWorld {
    private static final byte abc = -14;
    private static final Byte abc2;
    private static int lzc;

    public HelloWorld();
    public static void main(String[]);
    static {};
}
```
相应的源码
```
public static void deCompile(Cmd cmd) {
    Classpath classpath = new Classpath(cmd.jre, cmd.classpath);
    ClassLoader classLoader = new ClassLoader(classpath);

    String className =  cmd.getMainClass().replace(".", "/");
    String simpleClassName = className.substring(className.lastIndexOf("/") + 1);
    System.out.println("Compiled from \"" + simpleClassName + ".java\" (反编译工具作者：Lzc)");

    Class clazz = classLoader.loadClass(className);
    StringBuilder buf = new StringBuilder();
    buf.append(clazz.deprecated ? "@Deprecated " : "").append(AccessFlagsUtil.getAccessFlagsStr(clazz.accessFlags)).append(className).append(" {\n");

    for (Field memberInfo : clazz.fields) {
        Object val = ValueUtil.getValue(memberInfo, clazz);
        buf.append("    ").append(memberInfo.deprecated ? "@Deprecated " : "").append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags)).append(FieldDescriptorUtil.getDescriptorStr(memberInfo.descriptor())).append(memberInfo.name()).append(Optional.ofNullable(val).map(tmp -> " = " + tmp).orElse("")).append(";\n");
    }
    buf.append("\n");

    for (Method memberInfo : clazz.methods) {
        //方法参数类型 方法返回值类型
        MethodInfo methodInfo = MethodDescriptorUtil.getMethodInfo(memberInfo);
        buf.append("    ").append(memberInfo.deprecated ? "@Deprecated " : "").append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags)).append(methodInfo.getValueType()).append(methodInfo.getMethodName()).append(methodInfo.getParamType()).append(";\n");

    }
    buf.append("}");

    System.out.println(buf.toString());
}
```

## javap -v VS jad2
```
javap -v com.lzc.wuxin.demo.HelloWorld
Classfile /Users/mac/work/gitstudy/itstack-demo-jvm/itstack-lzc/target/test-classes/com/lzc/wuxin/demo/HelloWorld.class
  Last modified 2022-4-17; size 711 bytes
  MD5 checksum 17e695fb34c8c10f716ac6e92c8609f1
  Compiled from "HelloWorld.java"
public class com.lzc.wuxin.demo.HelloWorld
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #5.#31         // java/lang/Object."<init>":()V
   #2 = Methodref          #32.#33        // java/lang/Byte.valueOf:(B)Ljava/lang/Byte;
   #3 = Fieldref           #4.#34         // com/lzc/wuxin/demo/HelloWorld.abc2:Ljava/lang/Byte;
   #4 = Class              #35            // com/lzc/wuxin/demo/HelloWorld
   #5 = Class              #36            // java/lang/Object
   #6 = Utf8               abc
   #7 = Utf8               B
   #8 = Utf8               ConstantValue
   #9 = Integer            -14
  #10 = Utf8               abc2
  #11 = Utf8               Ljava/lang/Byte;
  #12 = Utf8               lzc
  #13 = Utf8               I
  #14 = Utf8               <init>
  #15 = Utf8               ()V
  #16 = Utf8               Code
  #17 = Utf8               LineNumberTable
  #18 = Utf8               LocalVariableTable
  #19 = Utf8               this
  #20 = Utf8               Lcom/lzc/wuxin/demo/HelloWorld;
  #21 = Utf8               main
  #22 = Utf8               ([Ljava/lang/String;)V
  #23 = Utf8               args
  #24 = Utf8               [Ljava/lang/String;
  #25 = Utf8               lzc2
  #26 = Utf8               lzc3
  #27 = Utf8               lzc4
  #28 = Utf8               <clinit>
  #29 = Utf8               SourceFile
  #30 = Utf8               HelloWorld.java
  #31 = NameAndType        #14:#15        // "<init>":()V
  #32 = Class              #37            // java/lang/Byte
  #33 = NameAndType        #38:#39        // valueOf:(B)Ljava/lang/Byte;
  #34 = NameAndType        #10:#11        // abc2:Ljava/lang/Byte;
  #35 = Utf8               com/lzc/wuxin/demo/HelloWorld
  #36 = Utf8               java/lang/Object
  #37 = Utf8               java/lang/Byte
  #38 = Utf8               valueOf
  #39 = Utf8               (B)Ljava/lang/Byte;
{
  public com.lzc.wuxin.demo.HelloWorld();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 3: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lcom/lzc/wuxin/demo/HelloWorld;

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=4, args_size=1
         0: iconst_2
         1: istore_1
         2: iconst_3
         3: istore_2
         4: iload_1
         5: iload_2
         6: iadd
         7: istore_3
         8: return
      LineNumberTable:
        line 10: 0
        line 11: 2
        line 12: 4
        line 13: 8
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       9     0  args   [Ljava/lang/String;
            2       7     1  lzc2   I
            4       5     2  lzc3   I
            8       1     3  lzc4   I

  static {};
    descriptor: ()V
    flags: ACC_STATIC
    Code:
      stack=1, locals=0, args_size=0
         0: bipush        -15
         2: invokestatic  #2                  // Method java/lang/Byte.valueOf:(B)Ljava/lang/Byte;
         5: putstatic     #3                  // Field abc2:Ljava/lang/Byte;
         8: return
      LineNumberTable:
        line 6: 0
}
SourceFile: "HelloWorld.java"

```


