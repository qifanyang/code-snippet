package test.mysql.isolate;

import test.mysql.BaseDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

/**
 *1. t2 开启事务--> t1开启事务,然后插入记录并提交-->t2再次查询,无法读取到新插入的记录,因为t1事务id大于t2事务id, select不返回
 * 2. t1开启事务-->t2开启事务-->t1插入数据,提交事务-->t2查询可以读取到t1新插入的数据(如果t2在t1提交事务前查询了,则为了读取一致性则无法查询到了)
 * @author yangqf
 * @version 1.0 2016/8/5
 */
public class RepeatableReadForRangeTest extends BaseDB{

    public static void main(String[] args) throws InterruptedException{

        Thread t1 = new Thread(){
            @Override
            public void run(){
                try{
                    Connection connection = getConnection();
                    connection.setAutoCommit(false);
                    Statement stmt = connection.createStatement();
                    stmt.execute("SELECT  * FROM castest");//数据库开始事务了
                    System.out.println("t1 开始事务");
                    ResultSet resultSet1 = stmt.executeQuery("SELECT CONNECTION_ID()");
                    if(resultSet1.next()){
                        System.out.println(resultSet1.getInt("CONNECTION_ID()"));
                    }
                    System.out.println();
                    TimeUnit.SECONDS.sleep(2);//事务1
                    ResultSet resultSet;
                    System.out.println("t1 插入数据");
                    stmt.executeUpdate("INSERT user VALUE(101,99) ");

                    resultSet = stmt.executeQuery("SELECT age FROM user where id < 1000");
                    int i = 0;
                    while(resultSet.next()){
                        i++;
                    }
                    System.out.println("t1 user的age num = "+i);

                    TimeUnit.SECONDS.sleep(2);//事务1
                    System.out.println("t1 提交事务");//让修改对t2可见
                    //范围数据,t1如果在t2开启之前没提交,t2范围就无法查询到
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
                try{
                    TimeUnit.SECONDS.sleep(3);//事务2, 两个事务开启间隔超过2秒为好,不然不好区分事务id大小
                    Connection connection = getConnection();
                    connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                    connection.setAutoCommit(false);
                    Statement stmt = connection.createStatement();
                    stmt.execute("SELECT  * FROM castest");//数据库开始事务了,不要用select 1
                    System.out.println("t2 开始事务");
                    ResultSet resultSet;
                    //read-consistent-view,多次读取相同的记录,mysql要保证结果一致
                    //如果这里读取id<100的记录,在下一次读取时,其他事务删除该记录并提交事务,返回的记录还是一样的
                    //如果这里不读取的话不会创建read-view

                    int i = 0;
//                    resultSet = stmt.executeQuery("SELECT age FROM user where id < 100");
//                    while(resultSet.next()){
//                        i++;
//                    }
//                    System.out.println("t2 删除之前的age num = "+i);
//                    TimeUnit.SECONDS.sleep(2);
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("t2 执行查询前等待t1修改数据");
                    //t2事务先开始,所以事务id小, t1事务id大,当t1删除数据,行删除标识为t1的事务id
                    //select将返回删除标识大于当前事务的记录
                    //select返回新建标识小于当前事务的记录
                    resultSet = stmt.executeQuery("SELECT age FROM user where id < 1000");
                     i = 0;
                    while(resultSet.next()){
                        i++;
                    }
                    System.out.println("t2 删除之前的age num = " + i);
                    TimeUnit.SECONDS.sleep(5);
                    resultSet = stmt.executeQuery("SELECT age FROM user where id < 1000");
                     i = 0;
                    while(resultSet.next()){
                        i++;
                    }
                    System.out.println("t2 最新的age num = "+i);
                    resultSet = stmt.executeQuery("SELECT age FROM user where id=101");
                    if(resultSet.next()){
                        System.out.println("t2 读取到的age = "+resultSet.getInt("age"));
                    }else {
                        System.out.println("t2 读取不到数据");
                    }

//                    stmt.execute("INSERT INTO user (id, age) VALUE (20, 77)");
                    stmt.execute("DELETE  FROM  user WHERE id = 101");
                    connection.commit();

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


    }
}

