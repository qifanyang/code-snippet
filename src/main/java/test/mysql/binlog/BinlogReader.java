package test.mysql.binlog;

import lombok.Data;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * binlog reader , 包装{@link java.io.DataInputStream},方便扩展和改变实现
 * @author yangqf
 * @version 1.0 2016/8/27
 */
@Data
public class BinlogReader{

    private DataInputStream dis;
    private boolean reverse = false;//是否反转多字节,java默认大端,如果读取小端需要反转,默认不反转

    private int offset = 0;

    public BinlogReader(InputStream is){
        this.dis = new DataInputStream(is);
    }

    public byte readByte() throws IOException{
        byte v = dis.readByte();
        ++offset;
        return v;
    }

    public int readInt() throws IOException{
        int v = dis.readInt();
        offset += 4;
        if(reverse){
            return Integer.reverseBytes(v);
        }
        return v;
    }

    public short readShort() throws IOException{
        short v = dis.readShort();
        offset += 2;
        if(reverse){
            return Short.reverseBytes(v);
        }
        return v;
    }

    public void read(byte[] bytes) throws IOException{
        dis.read(bytes);
        offset += bytes.length;
    }

    public  String readStringUTF8(int exceptLength) throws IOException{
        return readStringUTF8(exceptLength, false);
    }

    public  String readStringUTF8(int exceptLength, boolean reverse) throws IOException{
        byte[] bytes = new byte[exceptLength];
        dis.read(bytes);
        offset += bytes.length;
        //需要截取
        int notZeroIndex = -1;
        for(int i=0; i < exceptLength; i++){
            if(bytes[i] == 0){
                notZeroIndex = i;
                break;
            }
        }

        if(notZeroIndex != -1){
            byte[] bytes1 = new byte[notZeroIndex];
            System.arraycopy(bytes, 0, bytes1, 0, notZeroIndex);
            bytes = bytes1;
        }

        if(reverse){
            for(int i=0; i < bytes.length/2; i++){
                byte b = bytes[i];
                int end = bytes.length - 1 - i;
                bytes[i] = bytes[end];
                bytes[end] = b;
            }
        }
        return new String(bytes,"utf-8");
    }

    public void skipToPosition(int position) throws IOException{
        while(offset < position){
            dis.readByte();
            ++offset;
        }
    }

}
