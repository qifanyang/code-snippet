package mysql.binlog.event;

import lombok.Data;
import mysql.binlog.BinlogReader;
import mysql.binlog.LogEvent;
import mysql.binlog.LogEventData;
import mysql.binlog.LogEventType;

import java.io.IOException;
import java.util.BitSet;

/**
 * http://dev.mysql.com/doc/internals/en/table-map-event.html
 * table event包含了被改变表数据的表结构信息
 * @author yangqf
 * @version 1.0 2016/9/1
 */
@Data
public class TableMapEvent extends LogEvent{
    private TableMapEventData data = new TableMapEventData();
    @Override
    public LogEventData getData(){
        return data;
    }

    @Override
    public int eventType(){
        return LogEventType.TABLE_MAP_EVENT;
    }

    @Data
    public class TableMapEventData extends LogEventData{

        @Override
        public void parse(BinlogReader reader) throws IOException{
            byte postHeaderLength = getBinLog().eventPostHeaderLength(TableMapEvent.this);
            long tableId;
            if(postHeaderLength == 6){
                tableId = reader.readInt();
            }else {
                tableId = reader.readInteger6().value();
            }
            //将解析出来的Table_map_event收集起来,解析对应的rows_event时需要用到
            getBinLog().getTableMapEventMap().put(Long.valueOf(tableId), TableMapEvent.this);

            short flag = reader.readShort();
            byte schemaNameLen = reader.readByte();
            //数据库名字
            String schemaName = reader.readStringUTF8(schemaNameLen);
            reader.readByte();
            byte tableNameLen = reader.readByte();
            String tableName = reader.readStringUTF8(tableNameLen);
            reader.readByte();
            //列数量
            long columnCount = reader.readFieldLength();
            //列数据类型
            byte[] columnTypeDef = reader.read((int)columnCount);
            long columnMetaDefLen = reader.readFieldLength();
            byte[] columnMetaDef = reader.read((int) columnMetaDefLen);
            //标识哪些列可以为空
            BitSet nullBitmap = reader.readBitmap((int) columnCount);



            System.out.println();
        }
    }

}
