20220331,重点学习了itstack-demo-jvm-03，将加载的字节码数组交给ClassFile完成了字节码的解析
1. 增加了JdkUtil，提供魔数翻译成jdk version的方法
2. ConstantInfo接口的主要子类提供了toString方法，方便清晰地看到所有的子类特有的属性值
```shell

constants size：25
---------
constants[0]    :null
constants[1]    :ConstantMethodRefInfo{tag=10, classIdx=3, nameAndTypeIdx=22, className=java/lang/Object, nameAndDescriptor=alioo=={name=<init>, _type=()V}}
constants[2]    :ConstantClassInfo{tag=7, nameIdx=23, name=org/itstack/demo/test/HelloWorld}
constants[3]    :ConstantClassInfo{tag=7, nameIdx=24, name=java/lang/Object}
constants[4]    :ConstantUtf8Info{tag=1, str=abc}
constants[5]    :ConstantUtf8Info{tag=1, str=B}
constants[6]    :ConstantUtf8Info{tag=1, str=ConstantValue}
constants[7]    :ConstantIntegerInfo{tag=3, val=-14}
constants[8]    :ConstantUtf8Info{tag=1, str=lzc}
constants[9]    :ConstantUtf8Info{tag=1, str=<init>}
constants[10]    :ConstantUtf8Info{tag=1, str=()V}
constants[11]    :ConstantUtf8Info{tag=1, str=Code}
constants[12]    :ConstantUtf8Info{tag=1, str=LineNumberTable}
constants[13]    :ConstantUtf8Info{tag=1, str=LocalVariableTable}
constants[14]    :ConstantUtf8Info{tag=1, str=this}
constants[15]    :ConstantUtf8Info{tag=1, str=Lorg/itstack/demo/test/HelloWorld;}
constants[16]    :ConstantUtf8Info{tag=1, str=main}
constants[17]    :ConstantUtf8Info{tag=1, str=([Ljava/lang/String;)V}
constants[18]    :ConstantUtf8Info{tag=1, str=args}
constants[19]    :ConstantUtf8Info{tag=1, str=[Ljava/lang/String;}
constants[20]    :ConstantUtf8Info{tag=1, str=SourceFile}
constants[21]    :ConstantUtf8Info{tag=1, str=HelloWorld.java}
constants[22]    :ConstantNameAndTypeInfo{tag=12, nameIdx=9, descIdx=10}
constants[23]    :ConstantUtf8Info{tag=1, str=org/itstack/demo/test/HelloWorld}
constants[24]    :ConstantUtf8Info{tag=1, str=java/lang/Object}
---------
access flags：0x21
```

20220331,继续学习了itstack-demo-jvm-03，进一步打印了 fields，methods, attributes
```shell
fields count：2
---------
fieldName:abc 		, fieldType: B, memberInfo: MemberInfo{accessFlags=26, nameIdx=4, descriptorIdx=5, attributes=[ConstantValueAttribute{constantValueIdx=7}], accessFlags=26, name=abc, descriptor=B}
fieldName:lzc 		, fieldType: B, memberInfo: MemberInfo{accessFlags=10, nameIdx=8, descriptorIdx=5, attributes=[], accessFlags=10, name=lzc, descriptor=B}
---------
methods count: 2
---------
methodName:<init> 		, methodType:()V, memberInfo: MemberInfo{accessFlags=1, nameIdx=9, descriptorIdx=10, attributes=[CodeAttribute{maxStack=1, maxLocals=1, data=[42, -73, 0, 1, -79], exceptionTable=[], attributes=[LineNumberTableAttribute{lineNumberTable=[LineNumberTableEntry{startPC=0, lineNumber=3}]}, LocalVariableTableAttribute{localVariableTable=[LocalVariableTableEntry{startPC=0, length=5, nameIdx=14, descriptorIdx=15, idx=0}]}]}], accessFlags=1, name=<init>, descriptor=()V}
methodName:main 		, methodType:([Ljava/lang/String;)V, memberInfo: MemberInfo{accessFlags=9, nameIdx=16, descriptorIdx=17, attributes=[CodeAttribute{maxStack=0, maxLocals=1, data=[-79], exceptionTable=[], attributes=[LineNumberTableAttribute{lineNumberTable=[LineNumberTableEntry{startPC=0, lineNumber=9}]}, LocalVariableTableAttribute{localVariableTable=[LocalVariableTableEntry{startPC=0, length=1, nameIdx=18, descriptorIdx=19, idx=0}]}]}], accessFlags=9, name=main, descriptor=([Ljava/lang/String;)V}
---------
attributes count: 1
---------
attribute:SourceFileAttribute{sourceFileIdx=21,fileName=HelloWorld.java}  
---------
```
虽然都打印出来了，但是感觉没有javap -v详细
```shell
javap -v org.itstack.demo.test.HelloWorld            
Classfile /Users/mac/work/gitstudy/itstack-demo-jvm/itstack-demo-jvm-03/target/test-classes/org/itstack/demo/test/HelloWorld.class
  Last modified 2022-3-31; size 478 bytes
  MD5 checksum 2063c8190719b8c6176fb75b2cba1ca1
  Compiled from "HelloWorld.java"
public class org.itstack.demo.test.HelloWorld
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #3.#22         // java/lang/Object."<init>":()V
   #2 = Class              #23            // org/itstack/demo/test/HelloWorld
   #3 = Class              #24            // java/lang/Object
   #4 = Utf8               abc
   #5 = Utf8               B
   #6 = Utf8               ConstantValue
   #7 = Integer            -14
   #8 = Utf8               lzc
   #9 = Utf8               <init>
  #10 = Utf8               ()V
  #11 = Utf8               Code
  #12 = Utf8               LineNumberTable
  #13 = Utf8               LocalVariableTable
  #14 = Utf8               this
  #15 = Utf8               Lorg/itstack/demo/test/HelloWorld;
  #16 = Utf8               main
  #17 = Utf8               ([Ljava/lang/String;)V
  #18 = Utf8               args
  #19 = Utf8               [Ljava/lang/String;
  #20 = Utf8               SourceFile
  #21 = Utf8               HelloWorld.java
  #22 = NameAndType        #9:#10         // "<init>":()V
  #23 = Utf8               org/itstack/demo/test/HelloWorld
  #24 = Utf8               java/lang/Object
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
        line 3: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lorg/itstack/demo/test/HelloWorld;

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=0, locals=1, args_size=1
         0: return
      LineNumberTable:
        line 10: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       1     0  args   [Ljava/lang/String;
}
SourceFile: "HelloWorld.java"`1234`

```

20220401,继续学习了itstack-demo-jvm-03，进一步打印了 accessFlags(fields，methods, attributes)都打印出来了，也找到了对应的翻译类了
```java
public class AccessFlags {

    public static int ACC_PUBLIC       = 0x0001; // class field method
    public static int ACC_PRIVATE      = 0x0002; //       field method
    public static int ACC_PROTECTED    = 0x0004; //       field method
    public static int ACC_STATIC       = 0x0008; //       field method
    public static int ACC_FINAL        = 0x0010; // class field method
    public static int ACC_SUPER        = 0x0020; // class
    public static int ACC_SYNCHRONIZED = 0x0020; //             method
    public static int ACC_VOLATILE     = 0x0040; //       field
    public static int ACC_BRIDGE       = 0x0040; //             method
    public static int ACC_TRANSIENT    = 0x0080; //       field
    public static int ACC_VARARGS      = 0x0080; //             method
    public static int ACC_NATIVE       = 0x0100; //             method
    public static int ACC_INTERFACE    = 0x0200; // class
    public static int ACC_ABSTRACT     = 0x0400; // class       method
    public static int ACC_STRICT       = 0x0800; //             method
    public static int ACC_SYNTHETIC    = 0x1000; // class field method
    public static int ACC_ANNOTATION   = 0x2000; // class
    public static int ACC_ENUM         = 0x4000; // class field

}
```
以上面的accessFlags(fields)进行说明
fieldName:abc  accessFlags=26  
26转成2进制是 11010 ，则分别是 ACC_FINAL，ACC_STATIC，非ACC_PROTECTED，ACC_PRIVATE，非ACC_PUBLIC，故对应的访问权限是 private static final 会发现跟代码是一致的

fieldName:lzc  accessFlags=10
10转成2进制是 1010 ，则分别是 非ACC_FINAL，ACC_STATIC，非ACC_PROTECTED，ACC_PRIVATE，非ACC_PUBLIC，故对应的访问权限是 private static,会发现跟代码是一致的


20220404, 利用这个项目已经在类字节码进行了初步的解析，尝试进行反编译，把编译工具入口类Jad,目前的成果如下
```
classFile:org/itstack/demo/test/HelloWorld
public class org.itstack.demo.test.HelloWorld
private static final byte abc = -14
private static final Byte abc2
private static int lzc
public void HelloWorld()
public static void main(String[])
static void <clinit>()
```
//todo:private static final Byte abc2对应的value没有成功反编译出来
//todo:多出来一个<clinit>方法

20220404, 学习itstack-demo-jvm-06，将ClassFile进一步抽象成Class对象，方便后续使用

20220412，完善了JdkVersionUtil一直到jdk18
找到了《自已动手写JVM虚拟机》-张秀宏 电子版书籍
了解到classpy代码库--没有运行起来
重新整理了一class文件的数据结构
ClassFile {
u4 magic;
u2 minor_version;
u2 major_version;
u2 constant_pool_count;
cp_info constant_pool[constant_pool_count-1]; u2 access_flags;
u2 this_class;
u2 super_class;
u2 interfaces_count;
u2 interfaces[interfaces_count];
u2 fields_count;
field_info fields[fields_count];
u2 methods_count;
method_info methods[methods_count];
u2 attributes_count;
attribute_info attributes[attributes_count];
}

优化了常量池内容的打印格式
```
constant_pool_count size：37
---------
[00]    null
[01]    ConstantMethodRefInfo    {tag=10, classIdx=5, nameAndTypeIdx=28, className=java/lang/Object, nameAndDescriptor=alioo=={name=<init>, _type=()V}}
[02]    ConstantMethodRefInfo    {tag=10, classIdx=29, nameAndTypeIdx=30, className=java/lang/Byte, nameAndDescriptor=alioo=={name=valueOf, _type=(B)Ljava/lang/Byte;}}
[03]    ConstantFieldRefInfo     {tag=9, classIdx=4, nameAndTypeIdx=31, className=org/itstack/demo/test/HelloWorld, nameAndDescriptor=alioo=={name=abc2, _type=Ljava/lang/Byte;}}
[04]    ConstantClassInfo        {tag=7, nameIdx=32, name=org/itstack/demo/test/HelloWorld}
[05]    ConstantClassInfo        {tag=7, nameIdx=33, name=java/lang/Object}
[06]    ConstantUtf8Info         {tag=1, str=abc}
[07]    ConstantUtf8Info         {tag=1, str=B}
[08]    ConstantUtf8Info         {tag=1, str=ConstantValue}
[09]    ConstantIntegerInfo      {tag=3, val=-14}
[10]    ConstantUtf8Info         {tag=1, str=abc2}
[11]    ConstantUtf8Info         {tag=1, str=Ljava/lang/Byte;}
[12]    ConstantUtf8Info         {tag=1, str=lzc}
[13]    ConstantUtf8Info         {tag=1, str=I}
[14]    ConstantUtf8Info         {tag=1, str=<init>}
[15]    ConstantUtf8Info         {tag=1, str=()V}
[16]    ConstantUtf8Info         {tag=1, str=Code}
[17]    ConstantUtf8Info         {tag=1, str=LineNumberTable}
[18]    ConstantUtf8Info         {tag=1, str=LocalVariableTable}
[19]    ConstantUtf8Info         {tag=1, str=this}
[20]    ConstantUtf8Info         {tag=1, str=Lorg/itstack/demo/test/HelloWorld;}
[21]    ConstantUtf8Info         {tag=1, str=main}
[22]    ConstantUtf8Info         {tag=1, str=([Ljava/lang/String;)V}
[23]    ConstantUtf8Info         {tag=1, str=args}
[24]    ConstantUtf8Info         {tag=1, str=[Ljava/lang/String;}
[25]    ConstantUtf8Info         {tag=1, str=<clinit>}
[26]    ConstantUtf8Info         {tag=1, str=SourceFile}
[27]    ConstantUtf8Info         {tag=1, str=HelloWorld.java}
[28]    ConstantNameAndTypeInfo  {tag=12, nameIdx=14, descIdx=15}
[29]    ConstantClassInfo        {tag=7, nameIdx=34, name=java/lang/Byte}
[30]    ConstantNameAndTypeInfo  {tag=12, nameIdx=35, descIdx=36}
[31]    ConstantNameAndTypeInfo  {tag=12, nameIdx=10, descIdx=11}
[32]    ConstantUtf8Info         {tag=1, str=org/itstack/demo/test/HelloWorld}
[33]    ConstantUtf8Info         {tag=1, str=java/lang/Object}
[34]    ConstantUtf8Info         {tag=1, str=java/lang/Byte}
[35]    ConstantUtf8Info         {tag=1, str=valueOf}
[36]    ConstantUtf8Info         {tag=1, str=(B)Ljava/lang/Byte;}
---------
```
离方法体的解析还有很长的一段路。。。
mark《自已动手写JVM虚拟机》-张秀宏 学习到79页

20220313
Java虚拟机规范定义 了一种简单的语法来描述字段和方法，可以根据下面的规则生成描述符。
1)类型描述符。
1基本类型byte、short、char、int、long、float和double的描述符 是单个字母，分别对应B、S、C、I、J、F和D。注意，long的描述符是J 而不是L。
2引用类型的描述符是L+类的完全限定名+分号。
3数组类型的描述符是[+数组元素类型描述符。

2)字段描述符就是字段类型的描述符。
3)方法描述符是(分号分隔的参数类型描述符)+返回值类型描 述符，其中void返回值由单个字母V表示。

前面打印的内容只是class结构，method方法体内部的内容其实是记录在属性表CodeAttribute中
```
static AttributeInfo newAttributeInfo(String attrName, int attrLen, ConstantPool constantPool) {
switch (attrName) {
case "BootstrapMethods":
return new BootstrapMethodsAttribute();
case "Code":
return new CodeAttribute(constantPool);
case "ConstantValue":
return new ConstantValueAttribute();
case "Deprecated":
return new DeprecatedAttribute();
case "EnclosingMethod":
return new EnclosingMethodAttribute(constantPool);
case "Exceptions":
return new ExceptionsAttribute();
case "InnerClasses":
return new InnerClassesAttribute();
case "LineNumberTable":
return new LineNumberTableAttribute();
case "LocalVariableTable":
return new LocalVariableTableAttribute();
case "LocalVariableTypeTable":
return new LocalVariableTypeTableAttribute();
// case "MethodParameters":
// case "RuntimeInvisibleAnnotations":
// case "RuntimeInvisibleParameterAnnotations":
// case "RuntimeInvisibleTypeAnnotations":
// case "RuntimeVisibleAnnotations":
// case "RuntimeVisibleParameterAnnotations":
// case "RuntimeVisibleTypeAnnotations":
case "Signature":
return new SignatureAttribute(constantPool);
case "SourceFile":
return new SourceFileAttribute(constantPool);
// case "SourceDebugExtension":
// case "StackMapTable":
case "Synthetic":
return new SyntheticAttribute();
default:
return new UnparsedAttribute(attrName, attrLen);
}

    }
```
结构体如下
```
Code_attribute {
u2 attribute_name_index; u4 attribute_length;
u2 max_stack;
u2 max_locals;
u4 code_length;
u1 code[code_length];
u2 exception_table_length;
{ u2 start_pc;
u2 end_pc;
u2 handler_pc; u2 catch_type;
} exception_table[exception_table_length]; u2 attributes_count;
attribute_info attributes[attributes_count];
}
```

小有成就1
org.itstack.demo.jvm.rtda.heap.methodarea.Field中增加了deprecated来标记当前属性是否已过时（之前这个信息直接丢弃掉了）
```
public boolean deprecated;
public void copyAttributes(MemberInfo cfField) {
    ConstantValueAttribute valAttr = cfField.ConstantValueAttribute();
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
```

小有成就2
识别到一段代码存在精度丢失的问题
```
/**
 * TODO 存在精度丢失的问题
 */
public void pushFloat(float val) {
    this.slots[this.size].num = (int) val;
    this.size++;
}
```


