package test.vm.parser;

import test.vm.parser.cp.ConstantPoolInfo;
import test.vm.parser.cp.ConstantUtf8Info;

import java.io.DataInput;
import java.io.IOException;

/**
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class AttributeInfo {
    short attribute_name_index;
    short attribute_length;
    byte info[];//这里类似常量池,需要根据attribute_name_index的值判断是那种attribute

    ClassFile cf;
    public void parse(DataInput dataInput) throws IOException {
        attribute_name_index = dataInput.readShort();
        attribute_length = dataInput.readShort();

        ConstantPoolInfo constantPoolInfo = cf.getConstant_pool_info()[attribute_name_index];
        IConstantPoolObject constantPoolObject = constantPoolInfo.getConstantPoolObject();
        if(constantPoolInfo.getTag() == 1){
            ConstantUtf8Info utf8Info = (ConstantUtf8Info) constantPoolObject;
            String str = new String(utf8Info.getBytes(), "utf-8");
            System.out.println(str);
        }
        info = new byte[attribute_length];
        dataInput.readFully(info);
    }

    public short getAttribute_name_index() {
        return attribute_name_index;
    }

    public void setAttribute_name_index(short attribute_name_index) {
        this.attribute_name_index = attribute_name_index;
    }

    public short getAttribute_length() {
        return attribute_length;
    }

    public void setAttribute_length(short attribute_length) {
        this.attribute_length = attribute_length;
    }

    public byte[] getInfo() {
        return info;
    }

    public void setInfo(byte[] info) {
        this.info = info;
    }

    public ClassFile getCf() {
        return cf;
    }

    public void setCf(ClassFile cf) {
        this.cf = cf;
    }
}
