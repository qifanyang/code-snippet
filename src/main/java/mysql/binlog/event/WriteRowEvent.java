package mysql.binlog.event;

import mysql.binlog.LogEventType;

/**
 * @author yangqf
 * @version 1.0 2016/9/10
 */
public class WriteRowEvent extends RowsEvent{
    @Override
    public int eventType(){
        return LogEventType.WRITE_ROWS_EVENT;
    }
}
