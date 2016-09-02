package mysql.binlog.event;

import lombok.Data;
import mysql.binlog.BinlogReader;
import mysql.binlog.LogEvent;
import mysql.binlog.LogEventData;
import mysql.binlog.LogEventType;

import java.io.IOException;

/**
 *
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
        }
    }
}
