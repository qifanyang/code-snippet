package test.mysql.binlog;

import lombok.Data;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * v4 版本的binlog header一般固定是19字节,但有可能会包含extra_headers <br>
 * FORMAT_DESCRIPTION_EVENT和ROTATE_EVENT不会包含extra_headers <br>
 * @author yangqf
 * @version 1.0 2016/8/26
 */
@Data
public class LogEventHeader{
    private int timestamp;//statement 开始执行的时间,单位秒
    private byte typeCode;//FORMAT_DESCRIPTION_EVENT = 15, event type在enum Log_event_type定义,链接:http://dev.mysql.com/doc/internals/en/event-classes-and-types.html
    private int serverId;//创建log event的服务器id,还可以避免--log-slave-updates,循环同步数据
    private int eventLength;//log event长度,包含header+data,大多数事件长度小于1000,LOAD DATA INFILE除外
    private int nextPosition;//eventLength=116 nextPosition=120, 表示Offset to the end of the event,下一事件offset为当前值+1
    private short flags;
    private byte[] extra_headers; //v4 FDE 中不会有extra_headers

    //关于将解析方法写在何处,可以为每个event type单独写个parser, 也可以将方法放在event类中,因为
    //event类有多个,为了方便查看event字段,将解析方法放在event类中
    public void parse(BinlogReader reader) throws IOException{
        this.setTimestamp(reader.readInt());
        this.setTypeCode(reader.readByte());
        this.setServerId(reader.readInt());
        this.setEventLength(reader.readInt());
        this.setNextPosition(reader.readInt());
        this.setFlags(reader.readShort());
    }

}
