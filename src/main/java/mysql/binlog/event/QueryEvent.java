package mysql.binlog.event;

import lombok.Data;
import mysql.binlog.BinlogReader;
import mysql.binlog.LogEvent;
import mysql.binlog.LogEventData;
import mysql.binlog.LogEventType;

import java.io.IOException;

/**
 * http://dev.mysql.com/doc/internals/en/query-event.html
 * The query event is used to send text query right the binlog.
 * @author yangqf
 * @version 1.0 2016/9/1
 */
@Data
public class QueryEvent extends LogEvent{
    private QueryEventData data = new QueryEventData();
    @Override
    public LogEventData getData(){
        return data;
    }

    @Override
    public int eventType(){
        return LogEventType.QUERY_EVENT;
    }

    @Data
    public class QueryEventData extends LogEventData{
        private int slaveProxyId;
        private int executionTime;
        private byte schemaLength;
        private short errorCode;
        //number of bytes in the following sequence of status-vars
        //接下来的status-vars字节数量个数
        private short statusVarsLength;
        private String statusVars;//字节数组长度等于statusVarsLength,  key-value pair, key is 1 byte

        @Override
        public void parse(BinlogReader reader) throws IOException{
//            super.parse(reader);
        }
    }
}
