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
public class ConstantFloatInfo  implements IConstantPoolParser {
    byte tag = 4;
    int bytes[] = new int[4];//Big-Endian

    public void parse(DataInput dataInput) throws Exception {
//        tag = dataInput.readByte();
        Utils.readUnsignedBytes(bytes,dataInput);
    }

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public int[] getBytes() {
        return bytes;
    }

    public void setBytes(int[] bytes) {
        this.bytes = bytes;
    }
}
