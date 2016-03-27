package test.vm.parser.cp;

import test.vm.parser.IConstantPoolParser;

import java.io.DataInput;

/**
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class ConstantFieldrefInfo implements IConstantPoolParser {
    byte tag = 9;
    int class_index;//指向常量池的索引,值类型ClassInfo
    int name_and_type_index;//指向常量池的索引,name_and_type_info

    @Override
    public void parse(DataInput dataInput) throws Exception {
//        Utils.readFromDataInput(this, dataInput);
        class_index = dataInput.readUnsignedShort();
        name_and_type_index = dataInput.readUnsignedShort();
    }

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public int getClass_index() {
        return class_index;
    }

    public void setClass_index(int class_index) {
        this.class_index = class_index;
    }

    public int getName_and_type_index() {
        return name_and_type_index;
    }

    public void setName_and_type_index(int name_and_type_index) {
        this.name_and_type_index = name_and_type_index;
    }
}
