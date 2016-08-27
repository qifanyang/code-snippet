package test.mysql.binlog;

import lombok.Data;

/**
 *
 * @author yangqf
 * @version 1.0 2016/8/26
 */
//v4 log event
@Data
public abstract class LogEvent{
    //v4 event header格式比较固定
    private LogEventHeader header = new LogEventHeader();

    public abstract LogEventData getData();

}
