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


        @Override
        public void parse(BinlogReader reader) throws IOException{
            super.parse(reader);
            int nextPosition = getHeader().getNextPosition();
            reader.skipToPosition(nextPosition);//TODO 忽略event type header length数据
        }
    }
}
