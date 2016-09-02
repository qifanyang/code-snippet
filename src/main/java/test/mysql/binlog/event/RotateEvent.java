package test.mysql.binlog.event;

import lombok.Data;
import test.mysql.binlog.LogEvent;
import test.mysql.binlog.LogEventData;
import test.mysql.binlog.LogEventType;

/**
 * rotate event是binlog的最后一个log event, 用于指出下一个要读取的binlog文件名<br>
 * 当binlog文件大小达到一定值,会新建一个binlog文件,并写入一个rotate event<br>
 * 重新启动mysql 会在尾部写入stop event
 * @author yangqf
 * @version 1.0 2016/9/1
 */
@Data
public class RotateEvent extends LogEvent{
    private RotateEvent data = new RotateEvent();

    @Override
    public int eventType(){
        return LogEventType.ROTATE_EVENT;
    }

    public class RotateEventData extends LogEventData{

    }
}
