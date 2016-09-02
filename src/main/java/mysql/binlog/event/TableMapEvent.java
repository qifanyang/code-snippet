package mysql.binlog.event;

import lombok.Data;
import mysql.binlog.BinlogReader;
import mysql.binlog.LogEvent;
import mysql.binlog.LogEventData;
import mysql.binlog.LogEventType;

import java.io.IOException;

/**
 * @author yangqf
 * @version 1.0 2016/9/1
 */
@Data
public class TableMapEvent extends LogEvent{
    private TableMapEventData data = new TableMapEventData();
    @Override
    public LogEventData getData(){
        return data;
    }

    @Override
    public int eventType(){
        return LogEventType.TABLE_MAP_EVENT;
    }

    @Data
    public class TableMapEventData extends LogEventData{

        @Override
        public void parse(BinlogReader reader) throws IOException{
        }
    }

}
