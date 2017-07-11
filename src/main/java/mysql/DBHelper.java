package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库工具类,获取连接,创建表
 * @author yangqf
 * @version 1.0 2016/8/5
 */
public abstract class DBHelper {

    private static String driver = "com.mysql.jdbc.Driver";
    private static String passwrod = "123456";
    private static String userName = "root";
    private static String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false";


    protected static Connection getConnection(){

        try{
//            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, userName, passwrod);

            return conn;

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    protected static void closeConnection(Connection conn){
        if(conn != null){
                try{
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
    }

}
