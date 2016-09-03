package mysql.binlog;

import lombok.Data;
import mysql.binlog.datatype.Integer3;
import mysql.binlog.datatype.Integer6;
import mysql.binlog.event.*;

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

    private DataInputStream dis;//包装
    private boolean reverse = false;//是否反转多字节,java默认大端,如果读取小端需要反转,默认不反转

    //整个文件的偏移
    private int offset = 0;

    public BinlogReader(InputStream is){
        //BufferedInputStream  class loader getResourceAsStream return
        this.dis = new DataInputStream(is);
        try{
            System.out.println("available = " + dis.available());
            System.out.println("markSupported = " + dis.markSupported());
        }catch(IOException e){
            e.printStackTrace();
        }
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

    public byte[] read(int exceptLength) throws IOException{
        byte[] bytes = new byte[exceptLength];
        read(bytes);
        return bytes;
    }

    /**
     * 读取3字节的Integer, 采用小端字节序
     * @return
     * @throws IOException
     */
    public Integer3 readInteger3() throws IOException{
        byte[] bytes = read(3);
        return new Integer3(bytes);
    }

    public Integer6 readInteger6() throws IOException{
        byte[] bytes = read(6);
        return new Integer6(bytes);
    }

    //int length encode integer type

    /**
     * 读取带长度编码的整形数值, copy from mysql jdbc Buffer
     * @return
     * @throws IOException
     */
    public long readFieldLength() throws IOException{
        int sw = readByte() & 0xff;

        switch (sw) {
            case 251:
                return -1;

            case 252:
                return readShort();//2 bytes

            case 253:
                return readInteger3().value(); //3 bytes

            case 254:
                return readInt();

            default:
                return sw;//1 byte
        }
    }

    public String readStringEOF(int exceptLength) throws IOException{
        byte[] bytes = new byte[exceptLength];
        dis.read(bytes);
        offset += bytes.length;
        return new String(bytes,"utf-8");
    }


    /**
     * 读取指定长度的字节数据,返回string, 默认不reverse字节数组
     * @param exceptLength
     * @return
     * @throws IOException
     */
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

    /**
     * 跳到指定位置
     * @param position
     * @throws IOException
     */
    public void skipToPosition(int position) throws IOException{
        while(offset < position){
            dis.readByte();
            ++offset;
        }
    }

    /**
     * 跳过字节不读取, skip 为忽略的字节数量
     * @param skip
     */
    public void skip(int skip) throws IOException{
        int num = skip;
        while(dis.available() > 0 && num > 0){
            readByte();
            --num;
        }
    }

    public void readBinlog(BinLog binLog) throws IOException{
        while(dis.available() > 0){
            //没有magic number
            dis.mark(offset);
            int timestamp = readInt();
            byte eventType = readByte();
            dis.reset();
            offset -= 5;

            LogEvent logEvent;
            //根据事件类型创建, 向一个map中注册Event, 然后调用比case好看点
            switch(eventType){
                case LogEventType.FORMAT_DESCRIPTION_EVENT:
                    logEvent = new FormatDescriptionEvent();
                    break;
                case LogEventType.QUERY_EVENT:
                    logEvent = new QueryEvent();
                    break;
                case LogEventType.TABLE_MAP_EVENT:
                    logEvent = new TableMapEvent();
                    break;
                case LogEventType.UPDATE_ROWS_EVENT:
                    logEvent = new UpdateRowsEvent();
                    break;
                case LogEventType.XID_EVENT:
                    logEvent = new XidEvent();
                    break;
                case LogEventType.STOP_EVENT:
                    logEvent = new StopEvent();
                    break;
                case LogEventType.ROTATE_EVENT:
                    logEvent = new RotateEvent();
                    break;
                default:
                    throw new IllegalStateException("unknown event type ...");

            }
            logEvent.setEventOffset(offset);
            logEvent.setBinLog(binLog);
            logEvent.parse(this);
            binLog.getLogEvents().add(logEvent);

        }

        System.out.println("parse over, offset = " + offset);
    }

}
