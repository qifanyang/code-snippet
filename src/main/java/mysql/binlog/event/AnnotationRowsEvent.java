package mysql.binlog.event;

import lombok.Data;
import mysql.binlog.LogEvent;
import mysql.binlog.LogEventData;
import mysql.binlog.LogEventType;

/**
 * @author yangqf
 * @version 1.0 2016/9/9
 */
@Data
public class AnnotationRowsEvent extends LogEvent{

    private AnnotationRowsData data = new AnnotationRowsData();

    @Override
    public int eventType(){
        return LogEventType.ANNOTATE_ROWS_EVENT;
    }

    public class AnnotationRowsData extends LogEventData{



    }
}
