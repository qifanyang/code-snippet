package mysql.client;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import mysql.client.packet.Packet;

/**
 * http://dev.mysql.com/doc/internals/en/com-query-response.html
 * http://dev.mysql.com/doc/internals/en/com-query-response.html#column-definition
 * JDBC查询结果列信息
 * @author yangqf
 * @version 1.0 2016/9/13
 */
@Data
public class Field implements Packet{
    private String catalogName;//lenenc_str,  always "def"
    private String databaseName;
    private String tableName;//virtual table-name
    private String originalTableName;//physical table-name
    private String name; //virtual column name
    private String originalName;//physical column name
    private short characterSet;
    private int columnLength;
    private  byte type;
    private  short flags;
    private byte decimals;



    @Override
    public ByteBuf serialized(){
        return null;
    }

    //https://github.com/mysql/mysql-server/blob/5.6/sql/protocol.cc#L667
    @Override
    public void deserialized(ByteBuf buf){
        catalogName = ProtocolUtils.readLenencString(buf);
        databaseName = ProtocolUtils.readLenencString(buf);
        tableName = ProtocolUtils.readLenencString(buf);
        originalTableName = ProtocolUtils.readLenencString(buf);
        name = ProtocolUtils.readLenencString(buf);
        originalName = ProtocolUtils.readLenencString(buf);

        buf.readByte();//length of fixed-length fields [0c] 不知道用来干嘛的
        characterSet = buf.readShort();
        columnLength = buf.readInt();
        type = buf.readByte();
        flags = buf.readShort();
        decimals = buf.readByte();
        buf.readShort();//reversed 2 bytes for future

        //Protocol::send_result_set_metadata()
//        2              filler [00] [00]
//        if command was COM_FIELD_LIST {
//            lenenc_int     length of default-values
//                string[$len]   default values
//        }

    }
}
