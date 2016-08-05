package test.sync;

import com.sun.xml.internal.ws.api.ha.StickyFeature;

import java.sql.*;

/**
 * @author yangqf
 * @version 1.0 2016/4/21
 */
public class GenerateDbBean{
    public static void main(String[] args){

        DataBaseTableWalker.walk("sync_sms_templateccc", new ResultSetWalker(){
            @Override
            public void beforeWalk(String tableName){
                System.out.println("package com.keruyun.calm.entity;");
                System.out.println("import com.keruyun.commons.jdbc.annotations.Column;");
                System.out.println("import com.keruyun.commons.jdbc.annotations.Id;");
                System.out.println("import com.keruyun.commons.jdbc.annotations.TableEntity;");
                System.out.println("import lombok.Data;");
                System.out.println("");
                System.out.println("import java.math.BigDecimal;");
                System.out.println("import java.sql.Timestamp;");

                System.out.println("/**");
                System.out.println("*");
                System.out.println("* @author Util Auto Create");
                System.out.println("*/");
                System.out.println("@Data");
                System.out.println("@TableEntity(\""+tableName+"\")");

                System.out.println("public class "+DataBaseTableWalker.hengXianToTuoFeng(tableName, true)+"{");
            }

            @Override
            public void walk(ResultSet resultSet) throws SQLException{
                String columnName = resultSet.getString("COLUMN_NAME");
                String columnType = resultSet.getString("TYPE_NAME").toLowerCase();
                String REMARKS = resultSet.getString("REMARKS").replace("\r\n","");

                // bigint --> long
                // tinyint int --> Integer
                // varchar --> String
                // timestamp --> TimeStamp
                //decimal --> BigDecimal

                if(columnName.equals("id")){
                    System.out.println("\t@Id");
                    System.out.println("\tprivate Long id;//" + REMARKS);
                }else {
                    if(columnName.equals("recycle_status")){
//                        System.out.println(columnType);
                    }
                    System.out.println("\t@Column(\""+columnName+"\")");
                    String type = "XXX";
                    if(columnType.equals("bigint")){
                        type = "Long";
                    }else if(columnType.endsWith("int")){
                        type = "Integer";
                    }else if(columnType.equals("varchar")){
                        type = "String";
                    }else if(columnType.equals("timestamp")){
                        type = "Timestamp";
                    }else if(columnType.equals("decimal")){
                        type = "BigDecimal";
                    }else if(columnType.equals("date")){
                        type = "Date";
                    }else if(columnType.equals("bit")){
                        type = "Integer";
                    }
                    System.out.println("\tprivate "+type+" "+DataBaseTableWalker.hengXianToTuoFeng(columnName)+";//"+REMARKS);
                }
            }

            @Override
            public void afterWalk(){
                System.out.println("}");
            }
        });
    }
}
