package test.vm.parser.cp;

import test.vm.parser.IConstantPoolParser;
import test.vm.parser.Utils;

import java.io.DataInput;
import java.io.IOException;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class ConstantMethodTypeInfo implements IConstantPoolParser {
    byte tag;
    short descriptor_index;
    @Override
    public void parse(DataInput dataInput) throws Exception {
        Utils.readFromDataInput(this, dataInput);
    }

    @Override
    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public short getDescriptor_index() {
        return descriptor_index;
    }

    public void setDescriptor_index(short descriptor_index) {
        this.descriptor_index = descriptor_index;
    }
}
