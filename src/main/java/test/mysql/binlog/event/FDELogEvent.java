package test.mysql.binlog.event;

import lombok.Data;
import test.mysql.binlog.BinlogReader;
import test.mysql.binlog.LogEvent;
import test.mysql.binlog.LogEventData;

import java.io.IOException;


/**
 * format descriptor event, type value is 15
 *
 * @author yangqf
 * @version 1.0 2016/8/27
 */
@Data
public class FDELogEvent extends LogEvent{
    private FDEEventData data;

    public FDELogEvent(){
        data = new FDEEventData();
    }


    public class FDEEventData extends LogEventData{
        //http://dev.mysql.com/doc/internals/en/describing-packets.html#type-string.EOF
        //string.EOF 为一种数据类型描述方式, 表示当前字段为最后一个,那么长度等于总长度减去当前position

        //接下来的字段根据event type 不同而不一样
        private String[] string;        //FDE a array indexed by Binlog Event Type - 1 to extract the length of the event specific header.


        @Override
        public void parse(BinlogReader reader) throws IOException{
            super.parse(reader);
            int nextPosition = getHeader().getNextPosition();
            reader.skipToPosition(nextPosition);//TODO 忽略event type header length数据
        }
    }
}
