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
public class ConstantDoubleInfo  implements IConstantPoolParser {
    byte tag = 6;
    byte high_bytes[] = new byte[4];
    byte low_bytes[] = new byte[4];

    public void parse(DataInput dataInput) throws IOException {
//        tag = dataInput.readByte();
        dataInput.readFully(high_bytes);
        dataInput.readFully(low_bytes);
    }

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public byte[] getHigh_bytes() {
        return high_bytes;
    }

    public void setHigh_bytes(byte[] high_bytes) {
        this.high_bytes = high_bytes;
    }

    public byte[] getLow_bytes() {
        return low_bytes;
    }

    public void setLow_bytes(byte[] low_bytes) {
        this.low_bytes = low_bytes;
    }
}
