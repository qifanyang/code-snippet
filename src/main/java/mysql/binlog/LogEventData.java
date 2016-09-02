package mysql.binlog;

import lombok.Data;

import java.io.IOException;

/**
 * event data 部分格式需要根据 event type来决定,主要包含fixed-size part和variable-size part两部分 <br>
 * 每种event type都有对应的数据格式,所以需要根据event type来实现具体的数据读取
 *
 * 参考网址:https://dev.mysql.com/doc/internals/en/event-data-for-specific-event-types.html
 */
@Data
public abstract class LogEventData{

    public void parse(BinlogReader reader) throws IOException{


    }
}
