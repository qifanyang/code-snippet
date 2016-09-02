package test.mysql.binlog;

import lombok.Data;

import java.io.IOException;

/**
 * event data 部分格式需要根据 event type来决定,主要包含fixed-size part和variable-size part两部分 <br>
 * 每种event type都有对应的数据格式,所以需要根据event type来实现具体的数据读取
 *
 * 参考网址:https://dev.mysql.com/doc/internals/en/event-data-for-specific-event-types.html
 */
@Data
public abstract class LogEventData{
    private short binlogVersion;
    private String serverVersion;//50
    private  int createTimestamp;//big-log的创建时间,实际上为空,为以后扩展
    //上面三个字段,v1,v3,v4 都含有, 叫做fixed-size,
    private byte headerLength;//该值减去19 等于extra_headers的长度, FDE extra_headers为0, 所以这里值为0x13

    public void parse(BinlogReader reader) throws IOException{
//        this.setBinlogVersion(reader.readShort());
//        this.setServerVersion(reader.readStringUTF8(50));
//        this.setCreateTimestamp(reader.readInt());
//        this.setHeaderLength(reader.readByte());

    }
}
