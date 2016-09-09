package mysql.binlog;

import lombok.Data;

import java.io.IOException;

/**
 * binlog由二进制日志事件构成 <br>
 * 事件由header,post-header,data构成 <br>
 *
 * event type : http://dev.mysql.com/doc/internals/en/binlog-event.html <br>
 * 主要分为binlog management, statement based replication events, row based replication events, LOAD INFILE replication
 *
 *
 * @author yangqf
 * @version 1.0 2016/8/26
 */
//v4 log event
@Data
public abstract class LogEvent{
    //v4 event header格式比较固定
    private LogEventHeader header;

    //自定义LogEvent信息,方便解析,不属于binlog文件中的信息
    private int eventOffset;
    private BinLog binLog;

    public LogEvent(){
        header = createHeader();
    }

    public LogEventHeader createHeader(){
        return new LogEventHeader();
    }

    public abstract LogEventData getData();

    public abstract int eventType();

    public void parse(BinlogReader reader) throws IOException{
        getHeader().parse(reader);
        getData().parse(reader);
        //suppose event data don't have be read fully, skip offset to next event data position
        skipToNextEvent(reader);
    }

    /**
     *  直接跳过当前event, 准备读取下一个event, 根据event header中的nextPosition
     * @param reader
     */
    public void skipToNextEvent(BinlogReader reader) throws IOException{
            reader.skipToPosition(getHeader().getNextPosition());
    }

    //https://github.com/mysql/mysql-server/blob/5.6/include/mysql_com.h#L369
    /** enum_field_types */
    public static final int    MYSQL_TYPE_DECIMAL                       = 0;
    public static final int    MYSQL_TYPE_TINY                          = 1;
    public static final int    MYSQL_TYPE_SHORT                         = 2;
    public static final int    MYSQL_TYPE_LONG                          = 3;
    public static final int    MYSQL_TYPE_FLOAT                         = 4;
    public static final int    MYSQL_TYPE_DOUBLE                        = 5;
    public static final int    MYSQL_TYPE_NULL                          = 6;
    public static final int    MYSQL_TYPE_TIMESTAMP                     = 7;
    public static final int    MYSQL_TYPE_LONGLONG                      = 8;
    public static final int    MYSQL_TYPE_INT24                         = 9;
    public static final int    MYSQL_TYPE_DATE                          = 10;
    public static final int    MYSQL_TYPE_TIME                          = 11;
    public static final int    MYSQL_TYPE_DATETIME                      = 12;
    public static final int    MYSQL_TYPE_YEAR                          = 13;
    public static final int    MYSQL_TYPE_NEWDATE                       = 14;
    public static final int    MYSQL_TYPE_VARCHAR                       = 15;
    public static final int    MYSQL_TYPE_BIT                           = 16;
    public static final int    MYSQL_TYPE_TIMESTAMP2                    = 17;
    public static final int    MYSQL_TYPE_DATETIME2                     = 18;
    public static final int    MYSQL_TYPE_TIME2                         = 19;
    public static final int    MYSQL_TYPE_JSON                          = 245;
    public static final int    MYSQL_TYPE_NEWDECIMAL                    = 246;
    public static final int    MYSQL_TYPE_ENUM                          = 247;
    public static final int    MYSQL_TYPE_SET                           = 248;
    public static final int    MYSQL_TYPE_TINY_BLOB                     = 249;
    public static final int    MYSQL_TYPE_MEDIUM_BLOB                   = 250;
    public static final int    MYSQL_TYPE_LONG_BLOB                     = 251;
    public static final int    MYSQL_TYPE_BLOB                          = 252;
    public static final int    MYSQL_TYPE_VAR_STRING                    = 253;
    public static final int    MYSQL_TYPE_STRING                        = 254;
    public static final int    MYSQL_TYPE_GEOMETRY                      = 255;

}
