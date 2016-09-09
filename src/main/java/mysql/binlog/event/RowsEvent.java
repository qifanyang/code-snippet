package mysql.binlog.event;

import lombok.Data;
import mysql.binlog.BinlogReader;
import mysql.binlog.LogEvent;
import mysql.binlog.LogEventData;
import mysql.binlog.LogEventType;

import java.io.IOException;
import java.util.BitSet;

/**
 * https://github.com/mysql/mysql-server/blob/5.7/libbinlogevents/include/rows_event.h#L482
 * @author yangqf
 * @version 1.0 2016/9/1
 */
@Data
public abstract class RowsEvent extends LogEvent{
    private RowsEventData data = new RowsEventData();
    private long columnsNum;
    private BitSet columnsPresentBitmap1;//before
    private BitSet columnsPresentBitmap2;//after
    private BitSet nullBitmap1;//before
    private BitSet nullBitmap2;//after


    public class RowsEventData extends LogEventData{
        @Override
        public void parse(BinlogReader reader) throws IOException{
            byte postHeaderLength = getBinLog().eventPostHeaderLength(RowsEvent.this);
            long tableId;
            if(postHeaderLength == 6){
                tableId = reader.readInt();
            }else{
                tableId = reader.readInteger6().value();//6 bytes unsigned integer
                System.out.println("tableId = " + Long.toHexString(tableId));
                //如果该值不是0x00ffffff, 那么需要查看Table_Map_Event
            }
            short flag = reader.readShort();//Reserved for future use; currently always 0.
            //http://dev.mysql.com/doc/internals/en/format-description-event.html
//            if version == 2 { //这里verison == 2 是指rows_event version, 可以根据post_header_length长度==10来确定
//                2                    extra-data-length
//                string.var_len       extra-data
//            }
            if(postHeaderLength == 10){//post-header-length表示为V2
                short extraDataLen = reader.readShort();//at least 2
                byte[] extraData = reader.read(extraDataLen - 2);//row event extra data
            }

            columnsNum = reader.readFieldLength();
            //string.var_len
            //https://github.com/mysql/mysql-server/blob/5.7/libbinlogevents/include/rows_event.h#L550
            //Bit-field indicating whether each column is used one bit per column
            //columns_before_image   before列是否为空,为空的话在接下来读取row数据时跳过这一列
            columnsPresentBitmap1 = reader.readBitmap((int) columnsNum);

            //if(UPDATE_ROWS_EVENTv1 or v2){
            if(getHeader().getEventType() == LogEventType.UPDATE_ROWS_EVENT_V1
                    || getHeader().getEventType() == LogEventType.UPDATE_ROWS_EVENT){
                //columns_after_image  只有更新才有after image, 最新的mysql 5.7 把insert放到after image了
                columnsPresentBitmap2 = reader.readBitmap((int) columnsNum);
            }
            //}

            //begin to repeat to read row data until the end of current event
            //没一行的格式如下
            //Null_bit_mask(4)|field-1|field-2|field-3|field 4
            //减去4因为有checksum
            while(reader.getOffset() < getHeader().getNextPosition() - 4){//只要当前位置小于下一个事件位置,就表示当前事件没有读取完毕
                //注意nullBitmap的长度并不是是(列数+7)/8,而是(bits set in 'columns-present-bitmap1'+7)/8
                //也就是指有在columns-present-bitmap1中为true个个数
                int nullBitmapLen = 0;
                for(int i = 0; i < columnsNum; i++){
                    if(columnsPresentBitmap1.get(i))++nullBitmapLen;
                }
                //nullBitmap 表示该列值是否为空
                nullBitmap1 = reader.readBitmap(nullBitmapLen);

                //准备开始读取列数据
                TableMapEvent tableMapEvent = getBinLog().getTableMapEventMap().get(tableId);
                System.out.println("=======column before image=========");
                for(int i = 0; i < columnsNum; i++){
                    if(!columnsPresentBitmap1.get(i))continue;//该列没有值,继续下一列, tableMapEvent中也有nullBitmap但是表示列定义是否能为空

                    if(nullBitmap1.get(i)){
                        System.out.println("null");
                        continue;
                    }
                    //读取列数据根据列数据类型和列元数据从row data中解析
                    parseRowData(reader, tableMapEvent.getColumnTypeDef()[i], tableMapEvent.getColumnMetaDef()[i]);
                }

                System.out.println("=======column after image=========");
                //value of each field as defined in table map
                //需要通过table map去查找value, row event 通过判断tableId和table map event的tableId是否相等来关联
                //在写入rows_event 都会写入table_map_event
                if(getHeader().getEventType() == LogEventType.UPDATE_ROWS_EVENT_V1
                        || getHeader().getEventType() == LogEventType.UPDATE_ROWS_EVENT){
                    //update 还要读取after nullBitmap
                    nullBitmapLen = 0;
                    for(int i = 0; i < columnsNum; i++){
                        if(columnsPresentBitmap2.get(i))++nullBitmapLen;
                    }
                    nullBitmap2 = reader.readBitmap(nullBitmapLen);
                    for(int i = 0; i < columnsNum; i++){
                        if(!columnsPresentBitmap2.get(i))continue;//该列没有值,继续下一列, tableMapEvent中也有nullBitmap但是表示列定义是否能为空

                        if(nullBitmap2.get(i)){
                            System.out.println("null");
                            continue;
                        }
                        //读取列数据根据列数据类型和列元数据从row data中解析
                        parseRowData(reader, tableMapEvent.getColumnTypeDef()[i], tableMapEvent.getColumnMetaDef()[i]);
                    }
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

    private void parseRowData(BinlogReader reader, int columnType, byte[] columnMeta) throws IOException{

        switch(columnType){
            case MYSQL_TYPE_LONGLONG://java long
                System.out.println(reader.readLong());
                break;
            case MYSQL_TYPE_LONG: //java int
                System.out.println(reader.readInt());
                break;
            case MYSQL_TYPE_BIT:
                //4bit 返回[0]=4, [1]=0, 服务器写入的int,小端所以这里要反转
                int nbits = (columnMeta[1]*8)+columnMeta[0];
                int len = (nbits + 7) / 8;//字节数量
                if (nbits > 1) {
                    if(len == 1){
                        System.out.println(reader.readByte());
                    }else if(len == 2){
                        System.out.println(reader.readShort());
                    }else if(len == 3){
                        System.out.println(reader.readInteger3().value());
                    }else {
                        throw new IllegalStateException("xxx mysql type not be supported " + len);
                    }
                    //.....最多8字节,因为mysql bit长度最多为64
                }else {
                    System.out.println(reader.readByte());
                }
                break;
            default:
                throw new IllegalStateException("mysql type not be supported");
        }

    }

}
