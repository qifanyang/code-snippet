package test.vm.parser;

import test.vm.parser.cp.*;

import java.io.*;

/**
 * Title:class文件解析器
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class ClassFileParser {
    private ClassFile cf;
    private DataInput dataInput;
    private int offset;
    private boolean isDebug;

    public ClassFileParser() {
        cf = new ClassFile();
    }

    public void parse() throws Exception {
        magic();
        minorVersion();
        majorVersion();
        constantPool();
        accessFlag();
        thisClass();
        superClass();
        interfaces();
        fields();
        methods();
        attributes();
    }

    public void magic() throws IOException {
        cf.setMagic(dataInput.readInt());
        offset+=4;
        if(isDebug){
            System.out.print(Integer.toHexString(cf.getMagic()));
        }
    }

    public void minorVersion() throws IOException {
        cf.setMinor_version(dataInput.readShort());
        offset+=2;
        if(isDebug){
            System.out.println(Integer.toHexString(cf.getMinor_version()));
        }
    }

    public void majorVersion() throws IOException {
        cf.setMajor_version(dataInput.readShort());
        offset+=2;
        if(isDebug){
            System.out.println("offset = "+ offset +",major_version = " + cf.getMajor_version());
        }
    }

    public void constantPoolCount() throws IOException {
        cf.setConstant_pool_count(dataInput.readShort());
        offset+=2;
        if(isDebug){
            System.out.println("offset = "+ offset +"constant_pool_count = " + cf.getConstant_pool_count());
        }
    }

    /**
     * 解析常量池
     */
    public void constantPool() throws Exception {
        constantPoolCount();
        short constant_pool_count = cf.getConstant_pool_count();
        //第0个不存储?
        cf.setConstant_pool_info(new ConstantPoolInfo[constant_pool_count + 1]);
        ConstantPoolInfo[] constant_pool_info = cf.getConstant_pool_info();
        //常量池的索引范围是1至constant_pool_count−1
        for(int i = 1; i < constant_pool_count; i++){
            ConstantPoolInfo cpInfo = new ConstantPoolInfo();
            IConstantPoolParser constantPoolParser = null;
            byte tag = dataInput.readByte();//常量池tag
            switch (tag){
                case 1:
                    //constant_utf8_info
                    constantPoolParser = new ConstantUtf8Info();
                    break;
                case 2:
                case 3:
                    //constant_integer
                    constantPoolParser = new ConstantIntegerInfo();
                    break;
                case 4:
                    //constant_float
                    constantPoolParser = new ConstantFloatInfo();
                    break;
                case 5:
                    //constant_long
                    constantPoolParser = new ConstantLongInfo();
                    break;
                case 6:
                    //constant_double
                    constantPoolParser = new ConstantDoubleInfo();
                    break;
                case 7:
                    //constant_class
                    constantPoolParser = new ConstantClassInfo();
                    break;
                case 8:
                    //constant_string
                    constantPoolParser = new ConstantStringInfo();
                    break;
                case 9:
                    //constant_fieldref
                    constantPoolParser = new ConstantFieldrefInfo();
                    break;
                case 10:
                    //constant_methodref
                    constantPoolParser = new ConstantMethodRefInfo();
                    break;
                case 11:
                    //constant_interfaceref
                    constantPoolParser = new ConstantInterfaceMethodRefInfo();
                    break;
                case 12:
                    //constant_nameAndType
                    constantPoolParser = new ConstantNameAndTypeInfo();
                    break;
                case 13:
                    break;
                case 15:
                    //constant_methodHandle
                    constantPoolParser = new ConstantMethodHandleInfo();
                    break;
                case 16:
                    //constant_methodType
                    constantPoolParser = new ConstantMethodTypeInfo();
                    break;
                case 18:
                    //constant_invokeDynamic
                    constantPoolParser = new ConstantInvokeDynamicInfo();
                    break;
                default:
                    throw new IllegalStateException("constant pool tag is illegal!!!, tag = " + tag + " index = " + i);
            }
            constantPoolParser.parse(dataInput);
            cpInfo.setTag(constantPoolParser.getTag());
            cpInfo.setInfo(Utils.convert2Bytes(constantPoolParser));
            constant_pool_info[i] = cpInfo;
        }

    }


    public void accessFlag() throws IOException {
        cf.setAccess_flags(dataInput.readShort());
    }

    public void thisClass() throws IOException {
        cf.setThis_class(dataInput.readShort());
    }

     public void superClass() throws IOException {
        cf.setSuper_class(dataInput.readShort());
    }

    public void interfacesCount() throws IOException {
        cf.setInterfaces_count(dataInput.readShort());
    }

    public void interfaces() throws IOException {
        interfacesCount();
        short interfaces_count = cf.getInterfaces_count();
        short interfaces[] =new short[interfaces_count];
        for(int i = 0; i < interfaces_count; i++){
            interfaces[i] = dataInput.readShort();
        }
        cf.setInterfaces(interfaces);
    }

    public void fieldsCount() throws IOException {
        cf.setFields_count(dataInput.readShort());
    }

    public void fields() throws IOException {
        fieldsCount();
        short fields_count = cf.getFields_count();
        FieldInfo fields[] = new FieldInfo[fields_count];
        cf.setFields(fields);
        for(int i = 0; i < fields_count; i++) {
            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.parse(dataInput);
            fields[i] = fieldInfo;
        }
    }

    public void methodsCount() throws IOException {
        cf.setMethods_count(dataInput.readShort());
    }

    public void methods() throws IOException {
        methodsCount();
        short methods_count = cf.getMethods_count();
        MethodInfo methods[] = new MethodInfo[methods_count];
        cf.setMethods(methods);
        for(int i = 0; i < methods_count; i++){
            MethodInfo methodInfo = new MethodInfo();
            methodInfo.parse(dataInput);
            methods[i] = methodInfo;
        }
    }

    public void attributesCount() throws IOException {
        cf.setAttributes_count(dataInput.readShort());
    }

    public void attributes() throws IOException {
        attributesCount();
        short attributes_count = cf.getAttributes_count();
        AttributeInfo attributes[] = new AttributeInfo[attributes_count];
        cf.setAttributes(attributes);

        for(int i = 0; i < attributes_count; i++){
            AttributeInfo attributeInfo = new AttributeInfo();
            attributeInfo.parse(dataInput);
            attributes[i] = attributeInfo;
        }
    }

    public static void main(String[] args) throws Exception {
//        InputStream in = ClassFileParser.class.getClassLoader().getResourceAsStream("");
        File file = new File(System.getProperty("user.dir") + "\\target\\classes\\A.class").getCanonicalFile();
        System.out.println("file = " + file.getAbsolutePath());
        FileInputStream fis = new FileInputStream(file);
        DataInputStream dataInput = new DataInputStream(fis);
        ClassFileParser parser = new ClassFileParser();
        parser.dataInput = dataInput;
        parser.isDebug = true;
        parser.parse();
    }
}
