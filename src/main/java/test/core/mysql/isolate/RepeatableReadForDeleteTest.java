package test.core.mysql.isolate;

import test.core.mysql.BaseDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

/**
 * t2事务id小于t1事务id,  t1删除一行数据, t2查询该记录, 因为记录的删除标识大于当前,所以返回被删除的数据
 * @author yangqf
 * @version 1.0 2016/8/5
 */
public class RepeatableReadForDeleteTest extends BaseDB{

    public static void main(String[] args) throws InterruptedException{

        Thread t1 = new Thread(){
            @Override
            public void run(){
                try{
                    TimeUnit.SECONDS.sleep(2);//事务1
                    System.out.println("t1 开始事务");
                    Connection connection = getConnection();
                    connection.setAutoCommit(false);
                    Statement stmt = connection.createStatement();
                    stmt.execute("SELECT  * FROM castest");//数据库开始事务了
                    ResultSet resultSet = stmt.executeQuery("SELECT age FROM user where id=20");
                    while(resultSet.next()){
                        System.out.println("t1 删除之前的age = "+resultSet.getInt("age"));
                    }
                    TimeUnit.SECONDS.sleep(2);
                    stmt.executeUpdate("DELETE FROM user where id=20");
                    resultSet = stmt.executeQuery("SELECT age FROM user where id=20");
                    if(resultSet.next()){
                        System.out.println("t1 更新之后的age  = "+resultSet.getInt("age"));
                    }else {
                        System.out.println("t1 删除数据后查询不到");
                    }

                    System.out.println("t1 提交事务");//让修改对t2可见
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
                    System.out.println("t2 开始事务");
                    Connection connection = getConnection();
                    connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                    connection.setAutoCommit(false);
                    Statement stmt = connection.createStatement();
                    stmt.execute("SELECT  * FROM castest");//数据库开始事务了,不要用select 1
                    TimeUnit.SECONDS.sleep(2);//事务2
                    ResultSet resultSet;
                    //read-consistent-view,多次读取相同的记录,mysql要保证结果一致
                    //如果这里读取id=20的记录,在下一次读取时,其他事务删除该记录并提交事务,重复读取值不受影响
                    //如果这里不读取的话不会创建read-view
//                    resultSet = stmt.executeQuery("SELECT age FROM user where id=20");
//                    while(resultSet.next()){
//                        System.out.println("t2 删除之前的age = "+resultSet.getInt("age"));
//                    }
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("t2 执行查询前等待t1修改数据");
                    TimeUnit.SECONDS.sleep(3);
                    //t2事务先开始,所以事务id小, t1事务id大,当t1删除数据,行删除标识为t1的事务id
                    //select将返回删除标识大于当前事务的记录
                    resultSet = stmt.executeQuery("SELECT age FROM user where id=20");
                    if(resultSet.next()){
                        System.out.println("t2 读取到的age = "+resultSet.getInt("age"));
                    }else {
                        System.out.println("t2 读取不到数据");
                    }
                    TimeUnit.SECONDS.sleep(5);
                    resultSet = stmt.executeQuery("SELECT age FROM user where id=20");
                    if(resultSet.next()){
                        System.out.println("t2 读取到的age = "+resultSet.getInt("age"));
                    }else {
                        System.out.println("t2 读取不到数据");
                    }
                    //t1删除了数据,但是t2还可以读取到,但是t1事务id比t2小,所以删除的记录删除id比当前事务id小
                    //按mvcc查询不会返回删除时间比当前事务id小的数据(小于当前事务说明在当前事务开始之前已经删除了)

                    stmt.execute("INSERT INTO user (id, age) VALUE (20, 77)");

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
