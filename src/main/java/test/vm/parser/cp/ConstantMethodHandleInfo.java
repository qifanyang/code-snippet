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
public class ConstantMethodHandleInfo implements IConstantPoolParser {
    byte tag = 15;
    short reference_kind;
    short reference_index;
    @Override
    public void parse(DataInput dataInput) throws Exception {
//        Utils.readFromDataInput(this, dataInput);
        reference_kind = dataInput.readShort();
        reference_index = dataInput.readShort();
    }

    @Override
    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public short getReference_kind() {
        return reference_kind;
    }

    public void setReference_kind(short reference_kind) {
        this.reference_kind = reference_kind;
    }

    public short getReference_index() {
        return reference_index;
    }

    public void setReference_index(short reference_index) {
        this.reference_index = reference_index;
    }
}
