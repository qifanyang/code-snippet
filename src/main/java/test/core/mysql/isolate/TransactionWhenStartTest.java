package test.core.mysql.isolate;

import test.core.mysql.BaseDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

/**
 * 测试jdbc何时让mysql开启一个事务
 * @author yangqf
 * @version 1.0 2016/8/6
 */
public class TransactionWhenStartTest extends BaseDB{

    public static void main(String[] args) throws InterruptedException, SQLException{
        int x = 5;
        System.out.println("准备获取连接,停止" + x + "秒钟,查看数据库事务id");
        Connection connection = getConnection();//经测试在数据库中没看见事务ID
        TimeUnit.SECONDS.sleep(x);
        connection.setAutoCommit(false);
        System.out.println("设置自动提交");
        Statement stmt = connection.createStatement();
//        stmt.execute("SELECT 1");
        stmt.execute("STArt TRANSACTION ");
//        stmt.execute("SELECT * FROM castest");
        TimeUnit.SECONDS.sleep(x);
    }

}
