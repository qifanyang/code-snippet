package test.mysql;

import java.sql.*;
import java.util.concurrent.TimeUnit;

/**
 * @author yangqf
 * @version 1.0 2016/8/8
 */
public class CreashTest extends BaseDB{

    public static void main(String[] args) throws SQLException, InterruptedException{
        Connection connection = getConnection();
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM user WHERE id = 20");
        ResultSetMetaData rsmd = rs.getMetaData();
        int nrOfColumns = rsmd.getColumnCount();
        System.out.println(nrOfColumns);
        while(rs.next()){
            long aLong = rs.getLong(1);
            System.out.println(aLong);
        }
        System.out.println("修改数据sleep中...");
        //只要没有提交事务,不会修改data file,但是日志已经记录了,可以根据日志来重新提交事务
        TimeUnit.SECONDS.sleep(100000);
    }
}
