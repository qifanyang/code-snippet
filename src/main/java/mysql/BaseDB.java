package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author yangqf
 * @version 1.0 2016/8/5
 */
public abstract class BaseDB{

    private static String driver = "com.mysql.jdbc.Driver";
    private static String passwrod = "123456";
    private static String userName = "root";
    private static String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&amp;characterEncoding=utf-8";


    public static Connection getConnection(){

        try{
//            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, userName, passwrod);

            return conn;

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public void closeConn(Connection conn){
        if(conn != null){
                try{
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
    }
}
