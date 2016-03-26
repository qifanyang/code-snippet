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
public class ConstantInvokeDynamicInfo implements IConstantPoolParser {
    byte tag = 18;
    short bootstrap_method_attr_index;
    short name_and_type_index;

    @Override
    public void parse(DataInput dataInput) throws Exception {
//        Utils.readFromDataInput(this, dataInput);
        bootstrap_method_attr_index = dataInput.readShort();
        name_and_type_index = dataInput.readShort();
    }

    @Override
    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public short getBootstrap_method_attr_index() {
        return bootstrap_method_attr_index;
    }

    public void setBootstrap_method_attr_index(short bootstrap_method_attr_index) {
        this.bootstrap_method_attr_index = bootstrap_method_attr_index;
    }

    public short getName_and_type_index() {
        return name_and_type_index;
    }

    public void setName_and_type_index(short name_and_type_index) {
        this.name_and_type_index = name_and_type_index;
    }
}
