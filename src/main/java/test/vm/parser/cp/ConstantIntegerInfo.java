package test.vm.parser.cp;

import test.vm.parser.IConstantPoolObject;
import test.vm.parser.Utils;

import java.io.DataInput;
import java.io.IOException;

/**
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class ConstantIntegerInfo implements IConstantPoolObject {
    byte tag = 3;
    int bytes[] = new int[4];//Big-Endian , byte short boolean char 都用integer表示

    public void parse(DataInput dataInput) throws IOException {
//        tag = dataInput.readByte();
        Utils.readUnsignedBytes(bytes, dataInput);
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
