package mysql.client;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义结果集数据结构
 * @author yangqf
 * @version 1.0 2016/9/14
 */
@Data
public class ResultSetX{
    private Field[] fields;
    private List<RowData> rowDatas;

    private int currentRowIndex = -1;
    private boolean onValidateRow = false;

    public boolean next(){
        if(!rowDatas.isEmpty()){
            if(currentRowIndex < rowDatas.size() - 1){
                currentRowIndex++;
                onValidateRow = true;
                return true;
            }else {
                onValidateRow = false;
                return false;
            }
        }
        onValidateRow = false;
        return false;
    }

    private void checkRowPos(){
        if(!onValidateRow){
            throw new IllegalStateException("Before start of result set");
        }
    }

    public long getLong(int column){
        checkRowPos();
        //50 48  -> 20  ,  '2'的编码是50, 是字符'2'的unicode编码,  '2' - '0' 就得到unicode差值 就是2
        //转换算法就是遍历字符数组,乘以10在加上下一个值,最终结果就对了
        //mysql 使用字节数组存储long值, 可能有-, +
        int columnIndexedZero = column - 1;

        RowData rowData = rowDatas.get(currentRowIndex);
        byte[] data = rowData.getInternalRowData()[columnIndexedZero];

        int i = 0;
        int base = 10;
        for(int j = 0; j < data.length; j++){
            char c = (char) data[j];
            if (Character.isDigit(c)) {
                c -= '0';
            } else if (Character.isLetter(c)) {
                c = (char) (Character.toUpperCase(c) - 'A' + 10);
            } else {
                break;
            }

            i *= base;
            i += c;
        }

        return i;
    }

    public int getInt(int column){
        return (int) getLong(column);
    }

    public static void main(String[] args){
        ResultSetX resultSetX = new ResultSetX();
        List<RowData> rowDataList = new ArrayList<>();
        resultSetX.setRowDatas(rowDataList);
        RowData rowData = new RowData();
        rowDataList.add(rowData);
        byte[][] bytes = {{50,48}};
        rowData.setInternalRowData(bytes);
        resultSetX.next();
        System.out.println(resultSetX.getLong(1));
    }
 }
