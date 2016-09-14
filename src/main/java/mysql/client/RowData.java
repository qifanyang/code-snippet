package mysql.client;

import lombok.Data;

/**
 * jdbc diriver 使用ByteArrayRow.二维数组来存储一行数据
 * @author yangqf
 * @version 1.0 2016/9/14
 */
@Data
public class RowData{
    private byte[][] internalRowData;

}
