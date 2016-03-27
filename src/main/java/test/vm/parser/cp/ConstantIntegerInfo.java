package test.vm.parser.cp;

import test.vm.parser.IConstantPoolParser;

import java.io.DataInput;
import java.io.IOException;

/**
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class ConstantIntegerInfo implements IConstantPoolParser {
    byte tag = 3;
    byte bytes[] = new byte[4];//Big-Endian , byte short boolean char 都用integer表示

    public void parse(DataInput dataInput) throws IOException {
//        tag = dataInput.readByte();
        dataInput.readFully(bytes);
    }
    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
