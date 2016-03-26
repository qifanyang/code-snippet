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
public class ConstantMethodRefInfo implements IConstantPoolParser {
    byte tag;
    short class_index;
    short name_and_type_index;

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

    public short getClass_index() {
        return class_index;
    }

    public void setClass_index(short class_index) {
        this.class_index = class_index;
    }

    public short getName_and_type_index() {
        return name_and_type_index;
    }

    public void setName_and_type_index(short name_and_type_index) {
        this.name_and_type_index = name_and_type_index;
    }
}
