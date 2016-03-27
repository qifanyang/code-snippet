package test.vm.parser.cp;

import test.vm.parser.IConstantPoolParser;
import test.vm.parser.Utils;

import java.io.DataInput;
import java.io.IOException;

/**
 * 用于表示字符串对象java.lang.String
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class ConstantStringInfo implements IConstantPoolParser {
    byte tag = 8;
    int string_index;//指向constant_utf8_info的索引
    @Override
    public void parse(DataInput dataInput) throws Exception {
//        Utils.readFromDataInput(this, dataInput);
        string_index = dataInput.readUnsignedShort();
    }

    @Override
    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public int getString_index() {
        return string_index;
    }

    public void setString_index(int string_index) {
        this.string_index = string_index;
    }
}
