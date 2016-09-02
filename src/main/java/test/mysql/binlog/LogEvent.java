package test.mysql.binlog;

import lombok.Data;

import java.io.IOException;

/**
 * binlog由二进制日志事件构成 <br>
 * 事件由header,post-header,data构成 <br>
 * @author yangqf
 * @version 1.0 2016/8/26
 */
//v4 log event
@Data
public abstract class LogEvent{
    //v4 event header格式比较固定
    private LogEventHeader header = new LogEventHeader();

    public abstract LogEventData getData();

    public abstract int eventType();

    public void parse(BinlogReader reader) throws IOException{
        getHeader().parse(reader);
        getData().parse(reader);
    }

    /**
     *  直接跳过当前event, 准备读取下一个event, 根据event header中的nextPosition
     * @param reader
     */
    public void skipToNextEvent(BinlogReader reader) throws IOException{
            reader.skipToPosition(getHeader().getNextPosition());
    }

}
