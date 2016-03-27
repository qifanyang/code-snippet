package test.vm.parser.cp;

import test.vm.parser.IConstantPoolParser;
import test.vm.parser.Utils;

import java.io.DataInput;
import java.io.IOException;

/**
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class ConstantLongInfo implements IConstantPoolParser {
    byte tag = 5;
    int high_bytes[] = new int[4];
    int low_bytes[] = new int[4];

    public void parse(DataInput dataInput) throws IOException {
//        tag = dataInput.readByte();
        Utils.readUnsignedBytes(high_bytes,dataInput);
        Utils.readUnsignedBytes(low_bytes,dataInput);
    }

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public int[] getHigh_bytes() {
        return high_bytes;
    }

    public void setHigh_bytes(int[] high_bytes) {
        this.high_bytes = high_bytes;
    }

    public int[] getLow_bytes() {
        return low_bytes;
    }

    public void setLow_bytes(int[] low_bytes) {
        this.low_bytes = low_bytes;
    }
}
