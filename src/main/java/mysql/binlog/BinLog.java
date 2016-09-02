package mysql.binlog;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * v4 bin-log数据结构
 * event header
 * v1: 13 bytes: timestamp + type code + server ID + event length
 * v3: 19 bytes: v1 fields + next position + flags
 * v4: 19 bytes or more: v3 fields + possibly other information
 *
 * event data
 *
 * @author yangqf
 * @version 1.0 2016/8/26
 */
@Data
public class BinLog{
    private int magicNumber;
    private List<LogEvent> logEvents = new ArrayList<>();

    private static final Map<Integer, LogEvent> LOG_EVENT_CLASS_MAP = new HashMap<>();
    static {

    }

    public static void main(String[] args) throws IOException{
        InputStream ras = BinLog.class.getClassLoader().getResourceAsStream("mysql-bin.000023");
        BinlogReader reader = new BinlogReader(ras);
        reader.setReverse(true);

        BinLog binLog = new BinLog();
        binLog.setMagicNumber(reader.readInt());

//        LogEvent fdeEvent = new FormatDescriptionEvent();
//        fdeEvent.parse(reader);
        reader.readBinlog(binLog);

        //

        System.out.println();
    }




    //    class Value<T>{
//        private int offset;
//        private int length;
//
//        public Value(int offset, int length){
//            this.offset = offset;
//            this.length = length;
//        }
//
//        public T get(){
//           if(length == 4){
//               return (T)Integer.valueOf(1);
//           }
//            return (T)Integer.valueOf(1);
//        }
//
//    }

}
