package mysql.jdbc;

import mysql.DBHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author yangqf
 * @version 1.0 2016/9/26
 */
public class SimpleTest extends DBHelper {
    public static void main(String[] args) throws Exception{
        Connection connection = getConnection();
        connection.setAutoCommit(false);
        //使用ConnectionImpl创建statementImpl,采用默认resultType和resultSetConcurrency
        //connection-->命令模式客户端
        //statement-->命令模式的命令
        Statement statement = connection.createStatement();//创建命令实作

        //where is executor? ready to execute sql command
        //最终的执行器还是connection,最终交给MysqlIO发送sql命令包,然后读取响应,返回业务层可以操作的对象
        //具体的sql和参数,都是命令对象的参数
        ResultSet rs = statement.executeQuery("SELECT * FROM user WHERE id = 20");


    }
}
