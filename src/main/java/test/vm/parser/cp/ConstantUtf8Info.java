package test.vm.parser.cp;

import test.vm.parser.IConstantPoolObject;
import test.vm.parser.Utils;

import java.io.DataInput;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class ConstantUtf8Info implements IConstantPoolObject {
    byte tag = 1;
    int length;//bytes长度
    byte bytes[];//utf8编码的字节数组

    /**
     * 读取class文件的时候解析
     * @param dataInput
     * @throws IOException
     */
    public void parse(DataInput dataInput) throws IOException {
//        tag = dataInput.readByte();
        length = dataInput.readUnsignedShort();
        bytes = new byte[length];
        int[] intbytes = new int[length];
        Utils.readUnsignedBytes(intbytes, dataInput);
        for(int i = 0; i < length; i++){
            bytes[i] = (byte) intbytes[i];
        }
    }

    public byte[] convert2Bytes(){
        int len = 1 + 2 + bytes.length;
        ByteBuffer buffer = ByteBuffer.allocate(len);
//        buffer.put(tag).putShort(length).put(bytes);
        return buffer.array();
    }

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
