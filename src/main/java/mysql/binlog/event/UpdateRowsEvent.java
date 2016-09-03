package mysql.binlog.event;

import lombok.Data;
import mysql.binlog.BinlogReader;
import mysql.binlog.LogEvent;
import mysql.binlog.LogEventData;
import mysql.binlog.LogEventType;
import mysql.binlog.datatype.Integer6;

import java.io.IOException;

/**
 * @author yangqf
 * @version 1.0 2016/9/1
 */
@Data
public class UpdateRowsEvent extends LogEvent{
    private UpdateRowsEventData data = new UpdateRowsEventData();

    @Override
    public int eventType(){
        return LogEventType.UPDATE_ROWS_EVENT;
    }

    public class UpdateRowsEventData extends LogEventData{
        @Override
        public void parse(BinlogReader reader) throws IOException{
            byte postHeaderLength = getBinLog().eventPostHeaderLength(UpdateRowsEvent.this);
            if(postHeaderLength == 6){
                int tableId = reader.readInt();
            }else{
                long tableId = reader.readInteger6().value();
                System.out.println("tableId = " + Long.toHexString(tableId));
                //如果该值不是0x00ffffff, 那么需要查看Table_Map_Event
            }
            short flag = reader.readShort();
            long columnsNum = reader.readFieldLength();
            byte[] columnsPresentBitmap1 = reader.read((int)(columnsNum + 7)/8);
            //if(UPDATE_ROWS_EVENTv1 or v2){
            byte[] columnsPresentBitmap2 = reader.read((int)(columnsNum + 7)/8);
            //}

            //begin to repeat to read row data until the end of current event
//            byte[] nulBitmap = reader.read(colu)
            System.out.println();

        }
    }
}
