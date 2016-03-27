package test.vm.parser.cp;

import test.vm.parser.IConstantPoolParser;

import java.io.DataInput;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class ConstantUtf8Info implements IConstantPoolParser {
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
        dataInput.readFully(bytes);
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
