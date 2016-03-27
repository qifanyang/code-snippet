package test.vm.parser.cp;

import test.vm.parser.IConstantPoolObject;

import java.io.DataInput;

/**
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class ConstantInterfaceMethodRefInfo implements IConstantPoolObject {
    byte tag = 11;
    int class_index;
    int name_and_type_index;
    @Override
    public void parse(DataInput dataInput) throws Exception {
//        Utils.readFromDataInput(this, dataInput);
        class_index = dataInput.readUnsignedShort();
        name_and_type_index = dataInput.readUnsignedShort();
    }

    @Override
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
