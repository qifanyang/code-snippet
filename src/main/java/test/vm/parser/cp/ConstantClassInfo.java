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
public class ConstantClassInfo  implements IConstantPoolParser {
    byte tag = 7;
    short length;
    byte bytes[];

    @Override
    public void parse(DataInput dataInput) throws IOException {
        tag = dataInput.readByte();
        length = dataInput.readShort();
        bytes = new byte[length];
        dataInput.readFully(bytes);
    }

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public short getLength() {
        return length;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
