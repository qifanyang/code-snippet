package mysql.binlog.event;

import lombok.Data;
import mysql.binlog.BinlogReader;
import mysql.binlog.LogEvent;
import mysql.binlog.LogEventData;
import mysql.binlog.LogEventType;

import java.io.IOException;

/**
 * Transaction ID for 2PC, written whenever a COMMIT is expected
 * @author yangqf
 * @version 1.0 2016/9/1
 */
@Data
public class XidEvent extends LogEvent{
    private XidEventData data = new XidEventData();

    @Override
    public int eventType(){
        return LogEventType.XID_EVENT;
    }

    public class XidEventData extends LogEventData{
        @Override
        public void parse(BinlogReader reader) throws IOException{
        }
    }
}
