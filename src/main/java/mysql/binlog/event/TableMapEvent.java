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
            byte[] nullBitmapBytes = reader.read((int) ((columnCount+7)/8));
            //为什么是(x+7)/8 ?
            //因为一个字节是8bit,使用位图即一个bit代表一个标识,但是计算机网络传输等使用最小单位是字节(8bit)
            // 所以使用(x+7)/8 来转换为整数个字节
            //比如 x = 1, 需要1个bit来标识, 至少需要一个字节所以加7/8,
            //还可以使用x/8 + 1, 单这种情况,x = 8 时会浪费一个字节, 所以不可行
            BitSet nullBitmap = new BitSet((int) columnCount);
            int pos = 0;
            for (int bit = 0; bit < columnCount; bit += 8) {
                int f = ((int) nullBitmapBytes[pos++]) & 0xff;
                if (f == 0) continue;//没有设置值
                if ((f & 0x01) != 0) nullBitmap.set(bit);
                if ((f & 0x02) != 0) nullBitmap.set(bit + 1);
                if ((f & 0x04) != 0) nullBitmap.set(bit + 2);
                if ((f & 0x08) != 0) nullBitmap.set(bit + 3);
                if ((f & 0x10) != 0) nullBitmap.set(bit + 4);
                if ((f & 0x20) != 0) nullBitmap.set(bit + 5);
                if ((f & 0x40) != 0) nullBitmap.set(bit + 6);
                if ((f & 0x80) != 0) nullBitmap.set(bit + 7);
            }
            System.out.println();
        }
    }

}
