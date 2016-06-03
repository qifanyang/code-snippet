package test.sync;

import java.sql.*;

/**
 * @author yangqf
 * @version 1.0 2016/5/16
 */
public class DataBaseTableWalker{
    private static String driver = "com.mysql.jdbc.Driver";
    private static String passwrod = "8p6j9ObLxtTOmrpv9O0L";
    private static String userName = "dev_calm_sync";
    private static String url = "jdbc:mysql://rdst5ai4d32fe3qd6if46public.mysql.rds.aliyuncs.com:3306/calm_dev?useUnicode=true&amp;characterEncoding=utf-8/";


    public static void walk(String tableName, ResultSetWalker walker){

        try {
//            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, userName, passwrod);

            DatabaseMetaData metaData = conn.getMetaData();
            String columnName;
            String columnType;
            String REMARKS;
            ResultSet colRet = metaData.getColumns(null,"%", tableName,"%");
            walker.beforeWalk(tableName);
            while(colRet.next()) {
                walker.walk(colRet);
            }
            walker.afterWalk();
            // 关闭链接对象
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String hengXianToTuoFeng(String name){
        return hengXianToTuoFeng(name, false);
    }

    public static String hengXianToTuoFeng(String name, boolean isUpperFirstChar){

        char[] chars = name.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < chars.length; i++){
            if(chars[i] == '_'){
                sb.append(new String(chars, i+1, 1).toUpperCase());
                i++;
            }else{
                if(isUpperFirstChar && i == 0){
                    sb.append(new String(chars, 0, 1).toUpperCase());
                }else {
                    sb.append(chars[i]);
                }
            }
        }

        return sb.toString();
    }
}
