
纸上得来终觉浅，绝知此事要躬行，今天就动手实现一个jdk自带的工具javap

# 准备工作
## 用来测试的java文件 HelloWorld.java
```java
package org.itstack.demo.test;

@Deprecated
public class HelloWorld {

    @Deprecated
    private final static byte abc = -14;
    private final static Byte abc2 = Byte.valueOf((byte) -15);
    private static int lzc;

    @Deprecated
    public static void main(String[] args) {
        int lzc2=2;
        int lzc3=3;
        int lzc4=lzc2+lzc3;
    }

}

```

## javap VS lzc
javap效果
```
javap org.itstack.demo.test.HelloWorld
Compiled from "HelloWorld.java"
public class org.itstack.demo.test.HelloWorld {
  public org.itstack.demo.test.HelloWorld();
  public static void main(java.lang.String[]);
  static {};
}
```

lzc效果
```
Compiled from "HelloWorld.java" (反编译工具作者：Lzc)
@Deprecated public class org.itstack.demo.test.HelloWorld {
    @Deprecated private static final byte abc = -14;
    private static final Byte abc2;
    private static int lzc;

    public HelloWorld();
    @Deprecated public static void main(String[]);
    static {};
}
```
相应的源码
```
    public static void lzcp(Class clazz) {
        String className = clazz.name.replaceAll("/", ".");
        String simpleClassName = className.substring(className.lastIndexOf(".") + 1);

        System.out.println("Compiled from \"" + simpleClassName + ".java\" (反编译工具作者：Lzc)");

        StringBuilder buf = new StringBuilder();
        buf.append(clazz.deprecated ? "@Deprecated " : "")
                .append(org.itstack.demo.jvm.lzc.AccessFlagsUtil.getAccessFlagsStr(clazz.accessFlags))
                .append(className)
                .append(" {\n");

        for (Field memberInfo : clazz.fields) {
            Object val = ValueUtil.getValue(memberInfo, clazz);
            buf.append("    ")
                    .append(memberInfo.deprecated ? "@Deprecated " : "")
                    .append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags))
                    .append(FieldDescriptorUtil.getDescriptorStr(memberInfo.descriptor()))
                    .append(memberInfo.name())
                    .append(Optional.ofNullable(val).map(tmp -> " = " + tmp).orElse(""))
                    .append(";\n");
        }
        buf.append("\n");

        Method clinitMethod = clazz.getClinitMethod();
        for (Method memberInfo : clazz.methods) {
            //方法参数类型 方法返回值类型
            MethodInfo methodInfo = MethodDescriptorUtil.getMethodInfo(memberInfo, simpleClassName,clinitMethod.equals(memberInfo));
            buf.append("    ")
                    .append(memberInfo.deprecated ? "@Deprecated " : "")
                    .append(AccessFlagsUtil.getAccessFlagsStr(memberInfo.accessFlags))
                    .append(methodInfo.getValueType())
                    .append(methodInfo.getMethodName())
                    .append(methodInfo.getParamType())
                    .append(";\n");

        }
        buf.append("}");

        System.out.println(buf.toString());
    }
```

## javap -v VS lzc
```
javap -v org.itstack.demo.test.HelloWorld
Classfile /Users/mac/work/gitstudy/itstack-demo-jvm/itstack-lzc/target/test-classes/org/itstack/demo/test/HelloWorld.class
  Last modified 2022-4-14; size 837 bytes
  MD5 checksum 30445a4ebcf8184da65723398bde5d75
  Compiled from "HelloWorld.java"
public class org.itstack.demo.test.HelloWorld
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #5.#34         // java/lang/Object."<init>":()V
   #2 = Methodref          #35.#36        // java/lang/Byte.valueOf:(B)Ljava/lang/Byte;
   #3 = Fieldref           #4.#37         // org/itstack/demo/test/HelloWorld.abc2:Ljava/lang/Byte;
   #4 = Class              #38            // org/itstack/demo/test/HelloWorld
   #5 = Class              #39            // java/lang/Object
   #6 = Utf8               abc
   #7 = Utf8               B
   #8 = Utf8               ConstantValue
   #9 = Integer            -14
  #10 = Utf8               Deprecated
  #11 = Utf8               RuntimeVisibleAnnotations
  #12 = Utf8               Ljava/lang/Deprecated;
  #13 = Utf8               abc2
  #14 = Utf8               Ljava/lang/Byte;
  #15 = Utf8               lzc
  #16 = Utf8               I
  #17 = Utf8               <init>
  #18 = Utf8               ()V
  #19 = Utf8               Code
  #20 = Utf8               LineNumberTable
  #21 = Utf8               LocalVariableTable
  #22 = Utf8               this
  #23 = Utf8               Lorg/itstack/demo/test/HelloWorld;
  #24 = Utf8               main
  #25 = Utf8               ([Ljava/lang/String;)V
  #26 = Utf8               args
  #27 = Utf8               [Ljava/lang/String;
  #28 = Utf8               lzc2
  #29 = Utf8               lzc3
  #30 = Utf8               lzc4
  #31 = Utf8               <clinit>
  #32 = Utf8               SourceFile
  #33 = Utf8               HelloWorld.java
  #34 = NameAndType        #17:#18        // "<init>":()V
  #35 = Class              #40            // java/lang/Byte
  #36 = NameAndType        #41:#42        // valueOf:(B)Ljava/lang/Byte;
  #37 = NameAndType        #13:#14        // abc2:Ljava/lang/Byte;
  #38 = Utf8               org/itstack/demo/test/HelloWorld
  #39 = Utf8               java/lang/Object
  #40 = Utf8               java/lang/Byte
  #41 = Utf8               valueOf
  #42 = Utf8               (B)Ljava/lang/Byte;
{
  public org.itstack.demo.test.HelloWorld();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 4: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lorg/itstack/demo/test/HelloWorld;

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
        line 13: 0
        line 14: 2
        line 15: 4
        line 16: 8
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       9     0  args   [Ljava/lang/String;
            2       7     1  lzc2   I
            4       5     2  lzc3   I
            8       1     3  lzc4   I
    Deprecated: true
    RuntimeVisibleAnnotations:
      0: #12()

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
        line 8: 0
}
SourceFile: "HelloWorld.java"
Deprecated: true
RuntimeVisibleAnnotations:
  0: #12()
```


