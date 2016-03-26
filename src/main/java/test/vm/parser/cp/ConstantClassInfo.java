package test.vm.parser.cp;

import test.vm.parser.IConstantPoolParser;

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
public class ConstantClassInfo implements IConstantPoolParser {
    byte tag = 7;//在cp_info中已经有tag, 所以这里的tag只可以确定
    short name_index;
//    byte bytes[];

    @Override
    public void parse(DataInput dataInput) throws IOException {
//        tag = dataInput.readByte();
        name_index = dataInput.readShort();
    }

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public short getName_index() {
        return name_index;
    }

    public void setName_index(short name_index) {
        this.name_index = name_index;
    }
}
