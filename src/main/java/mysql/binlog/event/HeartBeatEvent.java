package mysql.binlog.event;

import lombok.Data;
import mysql.binlog.LogEvent;
import mysql.binlog.LogEventData;
import mysql.binlog.LogEventType;

/**
 * 主库生成的一个伪造事件,从库不要写入到relay log中,当复制连接空闲了一段时间没有像slave发送数据,变会发送heartbeat<br>
 *     该event没有post-header和payload
 * @author yangqf
 * @version 1.0 2016/9/2
 */
@Data
public class HeartBeatEvent extends LogEvent{
    private HeartBeatEventData data = new HeartBeatEventData();

    @Override
    public int eventType(){
        return LogEventType.HEARTBEAT_LOG_EVENT;
    }

    public class HeartBeatEventData extends LogEventData{

    }
}
