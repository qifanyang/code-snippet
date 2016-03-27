package test.vm.parser.cp;

import test.vm.parser.IConstantPoolParser;

import java.io.DataInput;
import java.io.IOException;

/**
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class ConstantClassInfo implements IConstantPoolParser {
    byte tag = 7;//在cp_info中已经有tag, 所以这里的tag只可以确定
    int name_index;//常量池索引,指向constant_utf8_info

    @Override
    public void parse(DataInput dataInput) throws IOException {
        name_index = dataInput.readUnsignedShort();
    }

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public int getName_index() {
        return name_index;
    }

    public void setName_index(int name_index) {
        this.name_index = name_index;
    }
}
