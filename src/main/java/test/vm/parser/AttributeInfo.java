package test.vm.parser;

import java.io.DataInput;
import java.io.IOException;

/**
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class AttributeInfo {
    short attribute_name_index;
    short attribute_length;
    byte info[];

    public void parse(DataInput dataInput) throws IOException {
        attribute_name_index = dataInput.readShort();
        attribute_length = dataInput.readShort();
        info = new byte[attribute_length];
        dataInput.readFully(info);
    }

    public short getAttribute_name_index() {
        return attribute_name_index;
    }

    public void setAttribute_name_index(short attribute_name_index) {
        this.attribute_name_index = attribute_name_index;
    }

    public short getAttribute_length() {
        return attribute_length;
    }

    public void setAttribute_length(short attribute_length) {
        this.attribute_length = attribute_length;
    }

    public byte[] getInfo() {
        return info;
    }

    public void setInfo(byte[] info) {
        this.info = info;
    }
}
