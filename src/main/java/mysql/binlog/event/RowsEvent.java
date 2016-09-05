package mysql.binlog.event;

import lombok.Data;
import mysql.binlog.BinlogReader;
import mysql.binlog.LogEvent;
import mysql.binlog.LogEventData;
import mysql.binlog.LogEventType;

import java.io.IOException;
import java.util.BitSet;

/**
 * @author yangqf
 * @version 1.0 2016/9/1
 */
@Data
public abstract class RowsEvent extends LogEvent{
    private UpdateRowsEventData data = new UpdateRowsEventData();


    public class UpdateRowsEventData extends LogEventData{
        @Override
        public void parse(BinlogReader reader) throws IOException{
            byte postHeaderLength = getBinLog().eventPostHeaderLength(RowsEvent.this);
            long tableId;
            if(postHeaderLength == 6){
                tableId = reader.readInt();
            }else{
                tableId = reader.readInteger6().value();
                System.out.println("tableId = " + Long.toHexString(tableId));
                //如果该值不是0x00ffffff, 那么需要查看Table_Map_Event
            }
            short flag = reader.readShort();
            //http://dev.mysql.com/doc/internals/en/format-description-event.html
//            if version == 2 { //这里verison == 2 是指rows_event version, 可以根据post_header_length长度==10来确定
//                2                    extra-data-length
//                string.var_len       extra-data
//            }
            if(postHeaderLength == 10){
                short extraDataLen = reader.readShort();//at least 2
                byte[] extraData = reader.read(extraDataLen - 2);//row event extra data
            }

            long columnsNum = reader.readFieldLength();
            //string.var_len 边长字节数组
            BitSet columnsPresentBitmap1 = reader.readBitmap((int) columnsNum);

            //if(UPDATE_ROWS_EVENTv1 or v2){
            BitSet columnsPresentBitmap2 = null;
            if(getHeader().getEventType() == LogEventType.UPDATE_ROWS_EVENT_V1
                    || getHeader().getEventType() == LogEventType.UPDATE_ROWS_EVENT){
                columnsPresentBitmap2 = reader.readBitmap((int) columnsNum);
            }
            //}

            //begin to repeat to read row data until the end of current event
            while(reader.getOffset() < getHeader().getNextPosition()){
                BitSet nullBitmap1 = reader.readBitmap((columnsPresentBitmap1.length() + 7) / 8);
                //value of each field as defined in table map
                //需要通过table map去查找value, row event 通过判断tableId和table map event的tableId是否相等来关联
                //在写入rows_event 都会写入table_map_event
                if(getHeader().getEventType() == LogEventType.UPDATE_ROWS_EVENT_V1
                        || getHeader().getEventType() == LogEventType.UPDATE_ROWS_EVENT){
                    BitSet  nullBitmap2 = reader.readBitmap((columnsPresentBitmap2.length()+7)/8);
                }
            }

            /**
             * rows event解析行数据时,循环读取row data,根据tableId找到对应table_map_event
             * 然后获根据map event中的列类型和column meta def来从row data中读取对应的值
             * 这里是根据列顺序来依次读取
             *
             * 这里和JDBC ResultSet有点类似,先读取Field信息,然后读取每行数据构建ByteArrayRow,
             * 每行数据是根据lenenc byte array, 但是row event是根据map event中数据类型和meta
             * 来从row data中读取,太复杂麻烦,不过还好mysql源码中有输出log event的调试代码,可以参考
             * 解析数据可以直接翻译c++代码
             */

//            byte[] nulBitmap = reader.read(colu)
            System.out.println();

        }
    }
}
