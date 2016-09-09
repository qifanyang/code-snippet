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
    private String schemaName;
    private short flag;
    private String tableName;
    private byte[] columnTypeDef;//列类型,canal使用ColumnInfo来表示
    private byte[][] columnMetaDef;//列元数据,canal使用ColumnInfo来表示

/*  For each column, a bit indicating whether data in the column
    can be NULL or not.  The number of bytes needed for this is
    int((column_count + 7) / 8).  The flag for the first column from the
    left is in the least-significant bit of the first byte, the second
    is in the second least significant bit of the first byte, the
    ninth is in the least significant bit of the second byte, and so
    on
    */
    private BitSet nullBitmap;//表定义中哪些列可以为空,可以为空bit为值为1, 表定义的列顺序对应低位bit,类似小端

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
            //每个字段详细解析
            //https://github.com/mysql/mysql-server/blob/5.7/libbinlogevents/include/rows_event.h#L50
            byte postHeaderLength = getBinLog().eventPostHeaderLength(TableMapEvent.this);
            long tableId;
            if(postHeaderLength == 6){
                tableId = reader.readInt();
            }else {
                tableId = reader.readInteger6().value();
            }
            //将解析出来的Table_map_event收集起来,解析对应的rows_event时需要用到
            getBinLog().getTableMapEventMap().put(Long.valueOf(tableId), TableMapEvent.this);

            flag = reader.readShort();//Reserved for future use; currently always 0
            byte schemaNameLen = reader.readByte();
            //数据库名字
            schemaName = reader.readStringUTF8(schemaNameLen);
            reader.readByte();//skip null-terminated [00]
            byte tableNameLen = reader.readByte();
            tableName = reader.readStringUTF8(tableNameLen);
            reader.readByte();//skip [00]
            //列数量
            long columnCount = reader.readFieldLength();//packed_integer
            //列数据类型
            columnTypeDef = reader.read((int)columnCount);
            long columnMetaDefLen = reader.readFieldLength();

            //mysql internals manual 文档是不完整的,必须要阅读mysql源码和其中的注释才能正确处理
            //log_event.h中,列出column type对应的meta长度以及含义,以及那些mysql type不能出现在binlog中
            //https://github.com/mysql/mysql-server/blob/5.6/sql/log_event.h#L3528
            //https://github.com/mysql/mysql-server/blob/5.7/libbinlogevents/include/rows_event.h#L167
            //https://github.com/mysql/mysql-server/blob/5.6/include/mysql_com.h
            //需要根据columnTypeDef来确定meta数据meta信息长度
//            byte[] columnMetaDef = reader.read((int) columnMetaDefLen);
            //因为使用int就可以满足meta数据存储,所以就不用byte[]了
            columnMetaDef = new byte[(int) columnCount][];
            for(int i = 0; i < columnCount; i++){
                switch((int)columnTypeDef[i]){
                    case MYSQL_TYPE_BLOB://the pack length , the number of bytes needed to represent the length of the blob , 1,2,3 or 4 , the max is 4G
                    case MYSQL_TYPE_DOUBLE:
                    case MYSQL_TYPE_FLOAT:
                        columnMetaDef[i] = new byte[1];
                        break;
                    case MYSQL_TYPE_STRING:
                    case MYSQL_TYPE_VAR_STRING:
                    case MYSQL_TYPE_VARCHAR://2字节表示字节长度
                    case MYSQL_TYPE_NEWDECIMAL:
                    case MYSQL_TYPE_BIT:
                        columnMetaDef[i] = new byte[2];
                        break;
                    case MYSQL_TYPE_ENUM:
                    case MYSQL_TYPE_SET:
                    case MYSQL_TYPE_TINY_BLOB:
                    case MYSQL_TYPE_MEDIUM_BLOB:
                    case MYSQL_TYPE_LONG_BLOB:
                           /*
                     * log_event.h : MYSQL_TYPE_SET & MYSQL_TYPE_ENUM : This
                     * enumeration value is only used internally and cannot
                     * exist in a binlog.
                     */
                        System.out.println("This enumeration value is only used internally "
                                + "and cannot exist in a binlog: type=" + columnTypeDef[i]);
                        break;
                    case MYSQL_TYPE_LONG:
                    case MYSQL_TYPE_LONGLONG:
                        columnMetaDef[i] = new byte[0];
                        break;
                    default:
                        throw new IllegalStateException("table map event meta parse error...");
                }
                reader.read(columnMetaDef[i]);
            }

            //标识哪些列可以为空,For each column, a bit indicating whether data in the column can be NULL or not
            nullBitmap = reader.readBitmap((int) columnCount);

            System.out.println();
        }
    }

}
