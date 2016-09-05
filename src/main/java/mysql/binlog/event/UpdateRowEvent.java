package mysql.binlog.event;

import mysql.binlog.LogEventType;

/**
 * @author yangqf
 * @version 1.0 2016/9/3
 */
public class UpdateRowEvent extends RowsEvent{

    @Override
    public int eventType(){
        return LogEventType.UPDATE_ROWS_EVENT;
    }
}
