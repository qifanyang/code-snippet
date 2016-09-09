package mysql.binlog.event;

import mysql.binlog.LogEventData;
import mysql.binlog.LogEventType;

/**
 * @author yangqf
 * @version 1.0 2016/9/9
 */
public class DeleteRowEvent extends RowsEvent{


    @Override
    public int eventType(){
        return LogEventType.DELETE_ROWS_EVENT;
    }
}
