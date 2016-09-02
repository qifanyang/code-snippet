package test.mysql.binlog.event;

import lombok.Data;
import test.mysql.binlog.BinlogReader;
import test.mysql.binlog.LogEvent;
import test.mysql.binlog.LogEventData;
import test.mysql.binlog.LogEventType;

import java.io.IOException;

/**
 * @author yangqf
 * @version 1.0 2016/9/1
 */
@Data
public class UpdateRowsEvent extends LogEvent{
    private UpdateRowsEventData data = new UpdateRowsEventData();

    @Override
    public int eventType(){
        return LogEventType.UPDATE_ROWS_EVENT;
    }

    public class UpdateRowsEventData extends LogEventData{
        @Override
        public void parse(BinlogReader reader) throws IOException{
            skipToNextEvent(reader);
        }
    }
}
