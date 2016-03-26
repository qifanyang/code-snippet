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
public class ConstantStringInfo implements IConstantPoolParser {
    byte tag;
    short string_index;
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

    public short getString_index() {
        return string_index;
    }

    public void setString_index(short string_index) {
        this.string_index = string_index;
    }
}
