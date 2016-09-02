package test.mysql.binlog.event;

import lombok.Data;
import test.mysql.binlog.LogEvent;
import test.mysql.binlog.LogEventData;
import test.mysql.binlog.LogEventType;

/**
 *  A STOP_EVENT has no payload or post-header
 * @author yangqf
 * @version 1.0 2016/9/1
 */
@Data
public class StopEvent extends LogEvent{
    private StopEventData data = new StopEventData();

    @Override
    public int eventType(){
        return LogEventType.STOP_EVENT;
    }

    public class StopEventData extends LogEventData{

    }
}
