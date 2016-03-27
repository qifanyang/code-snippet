package test.vm.parser.cp;

import test.vm.parser.IConstantPoolParser;
import test.vm.parser.Utils;

import java.io.DataInput;
import java.io.IOException;

/**
 *表示方法类型,这里和name_and_type有点重复
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class ConstantMethodTypeInfo implements IConstantPoolParser {
    byte tag = 16;
    int descriptor_index;
    @Override
    public void parse(DataInput dataInput) throws Exception {
//        Utils.readFromDataInput(this, dataInput);
        descriptor_index = dataInput.readUnsignedShort();
    }

    @Override
    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public int getDescriptor_index() {
        return descriptor_index;
    }

    public void setDescriptor_index(int descriptor_index) {
        this.descriptor_index = descriptor_index;
    }
}
