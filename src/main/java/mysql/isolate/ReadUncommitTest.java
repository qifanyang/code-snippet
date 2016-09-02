package mysql.isolate;

import mysql.BaseDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

/**
 * sql隔离级别未提交读测试<br>
 * 脏读测试验证,设置为Connection.TRANSACTION_READ_UNCOMMITTED的事务会读取到其它事务没有提交的数据
 *
 * 提交读,把事务当做一个线程理解,就是一个线程不能够理解看见另一个线程对共享变量的修改,相当于在线程
 * 栈中修改的值还未同步到主存中,其它线程还不可见
 * @author yangqf
 * @version 1.0 2016/8/5
 */
public class ReadUncommitTest extends BaseDB{

//    CREATE TABLE `user` (
//            `id` int(11) NOT NULL,
//    `age` int(11) NOT NULL,
//    PRIMARY KEY (`id`)
//    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
    public static void main(String[] args) throws InterruptedException{

        Thread t1 = new Thread(){
            @Override
            public void run(){
                Connection connection = getConnection();
                try{
//                    connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                    connection.setAutoCommit(false);
                    Statement stmt = connection.createStatement();
                    ResultSet resultSet = stmt.executeQuery("SELECT age FROM user where id=3");
                    while(resultSet.next()){
                        System.out.println("更新之前的age = "+resultSet.getInt("age"));
                    }
                    stmt.executeUpdate("update user set age=age+1 where id=3");
                    resultSet = stmt.executeQuery("SELECT age FROM user where id=3");
                    while(resultSet.next()){
                        System.out.println("更新之后的age  = "+resultSet.getInt("age"));
                    }
                    System.out.println("t1 update数据后,睡眠5秒后提交事务, 对于脏读,没有提交数据之前t1读取数据应该是更新后的值");
                    TimeUnit.SECONDS.sleep(6);
                    System.out.println("t1 提交事务");
                    connection.commit();
                }catch(SQLException e){
                    e.printStackTrace();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        };

       Thread t2 =  new Thread(){
            @Override
            public void run(){
                Connection connection = getConnection();
                try{
                    connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                    Statement stmt = connection.createStatement();
                    System.out.println("t2 执行查询前等待t1修改数据");
                    TimeUnit.SECONDS.sleep(3);
                    ResultSet resultSet = stmt.executeQuery("SELECT age FROM user where id=3");
                    while(resultSet.next()){
                        System.out.println("t2 读取到的age = "+resultSet.getInt("age"));
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        };

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        //脏读测试结果,设置为Connection.TRANSACTION_READ_UNCOMMITTED的事务会读取到其它事务没有提交的数据
    }
}
