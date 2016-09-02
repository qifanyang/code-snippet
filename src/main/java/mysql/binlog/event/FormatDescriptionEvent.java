package mysql.binlog.event;

import lombok.Data;
import mysql.binlog.BinlogReader;
import mysql.binlog.LogEvent;
import mysql.binlog.LogEventData;
import mysql.binlog.LogEventType;

import java.io.IOException;


/**
 * format descriptor event, type value is 15
 * 该事件是binlog的第一个事件,用于描述其它事件如何布局
 *
 * @author yangqf
 * @version 1.0 2016/8/27
 */
@Data
public class FormatDescriptionEvent extends LogEvent{
    private FDEEventData data;

    public FormatDescriptionEvent(){
        data = new FDEEventData();
    }

    @Override
    public int eventType(){
        return LogEventType.FORMAT_DESCRIPTION_EVENT;
    }


    @Data
    public class FDEEventData extends LogEventData{
        private short binlogVersion; //mysql 5.6 为v4
        private String serverVersion;//50  5.6-log
        private  int createTimestamp;//big-log的创建时间,实际上为空,为以后扩展
        //上面三个字段,v1,v3,v4 都含有, 叫做fixed-size,
        private byte headerLength;//该值减去19 等于extra_headers的长度, FDE extra_headers为0, 所以这里值为0x13

        //http://dev.mysql.com/doc/internals/en/describing-packets.html#type-string.EOF
        //string.EOF 为一种数据类型描述方式, 表示当前字段为最后一个,那么长度等于总长度减去当前position
        //FDE a array indexed by Binlog Event Type - 1 to extract the length of the event specific header.
        private byte[] eventTypeHeaderLength;


        @Override
        public void parse(BinlogReader reader) throws IOException{
            this.setBinlogVersion(reader.readShort());
            this.setServerVersion(reader.readStringUTF8(50));
            this.setCreateTimestamp(reader.readInt());
            this.setHeaderLength(reader.readByte());
            //string.EOF 用于一个包最后一个字段,长度等于包的长度减去当前位置,可能会有一些空的,但是nextPosition会跳过
            int eventLength = getHeader().getEventLength();
            int eventTypeHeaderLengthArrayLength = eventLength - (reader.getOffset() - getEventOffset());
            this.setEventTypeHeaderLength(reader.read(eventTypeHeaderLengthArrayLength));
        }
    }
}
