package test.sync;

import test.core.base.StringTest;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yangqf
 * @version 1.0 2016/4/20
 */
public class GenerateWiki{
    public static void main(String[] args){
        DataBaseTableWalker.walk("market_plan_commercial_rel", new ResultSetWalker(){
            @Override
            public void beforeWalk(String tableName){
                System.out.println("|_.字段名称|_.数据类型|_.是否必需|_.备注|");
                System.out.println("|---------|--------------------|---------|------|");
            }

            @Override
            public void walk(ResultSet colRet) throws SQLException{
                String columnName = colRet.getString("COLUMN_NAME");
                String columnType = colRet.getString("TYPE_NAME");
                String REMARKS = colRet.getString("REMARKS");
                int datasize = colRet.getInt("COLUMN_SIZE");
                int digits = colRet.getInt("DECIMAL_DIGITS");
                int nullable = colRet.getInt("NULLABLE");
//                System.out.println(columnName+" "+columnType+" "+datasize+" "+digits+" "+nullable+" "+REMARKS);
                System.out.println("|"+DataBaseTableWalker.hengXianToTuoFeng(columnName)+"|"+columnType.toLowerCase()+"|是|"+REMARKS+"|");

            }

            @Override
            public void afterWalk(){

            }
        });
    }

}
