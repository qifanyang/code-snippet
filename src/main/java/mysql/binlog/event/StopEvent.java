package mysql.binlog.event;

import lombok.Data;
import mysql.binlog.*;

import java.io.IOException;

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

    @Override
    public LogEventHeader createHeader(){
        return new StopEventHeader();
    }

    public class StopEventHeader extends LogEventHeader{
        @Override
        protected void parseExtraHeaders(BinlogReader reader) throws IOException{
            //stop event header 有四字节extra_header
            reader.skip(4);
        }

    }

    public class StopEventData extends LogEventData{

        @Override
        public void parse(BinlogReader reader) throws IOException{
        }
    }
}
