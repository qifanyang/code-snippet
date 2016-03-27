package test.vm.parser.cp;

import test.vm.parser.IConstantPoolObject;

import java.io.DataInput;

/**
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class ConstantInvokeDynamicInfo implements IConstantPoolObject {
    byte tag = 18;
    int bootstrap_method_attr_index;
    int name_and_type_index;

    @Override
    public void parse(DataInput dataInput) throws Exception {
//        Utils.readFromDataInput(this, dataInput);
        bootstrap_method_attr_index = dataInput.readUnsignedShort();
        name_and_type_index = dataInput.readUnsignedShort();
    }

    @Override
    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public int getBootstrap_method_attr_index() {
        return bootstrap_method_attr_index;
    }

    public void setBootstrap_method_attr_index(int bootstrap_method_attr_index) {
        this.bootstrap_method_attr_index = bootstrap_method_attr_index;
    }

    public int getName_and_type_index() {
        return name_and_type_index;
    }

    public void setName_and_type_index(int name_and_type_index) {
        this.name_and_type_index = name_and_type_index;
    }
}
