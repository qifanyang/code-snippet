package mysql.isolate;

import mysql.BaseDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

/**
 * 可重复读,是指一个事务重复读取一个数据,值应该是一致的,不论另外一个修改该数据的事务是否提交
 *
 * mysql innodb 可重复读实现采用多版本并发控制实现(mvcc),每条记录有额外的两个隐式字段<br>
 * 创建时间,删除时间, 值为事务id, 没个事务开启时mysql都会分配一个递增的事务id<br>
 *     查询:返回创建时间小于或等于当前事务id, 并且删除时间为空或大于当前事务id<br>
 *     插入:创建时间为当前事务id,删除时间为空<br>
 *     删除:删除时间为当前事务id<br>
 *     更新:被修改的记录删除时间为当前事务id(当前事务不会读取到老记录,老记录用于其他事务读取),插入一条数据创建时间为当前事务id<br>
 * mvcc解决了并发访问时不用加锁,类似java中的CopyOnWriteArrayList,修改时拷贝一个副本出来修改,线程安全<br>
 *
 *
 *
 *
 * @author yangqf
 * @version 1.0 2016/8/5
 */
public class RepeatableReadTest extends BaseDB{

    public static void main(String[] args) throws InterruptedException{

        Thread t1 = new Thread(){
            @Override
            public void run(){
                try{
                    TimeUnit.SECONDS.sleep(2);
                    Connection connection = getConnection();
                    connection.setAutoCommit(false);
                    Statement stmt = connection.createStatement();
                    System.out.println("t1 开始事务");
                    stmt.execute("SELECT  * from castest");
                    TimeUnit.SECONDS.sleep(2);//事务1
                    ResultSet resultSet = stmt.executeQuery("SELECT age FROM user where id=20");
                    while(resultSet.next()){
                        System.out.println("t1 删除之前的age = "+resultSet.getInt("age"));
                    }
                    TimeUnit.SECONDS.sleep(2);
//                    stmt.executeUpdate("DELETE FROM user where id=20");
                    stmt.execute("UPDATE user set age = age + 1 WHERE id=20");
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
//                    TimeUnit.SECONDS.sleep(2);//事务2
                    Connection connection = getConnection();
                    connection.setAutoCommit(false);
                    Statement stmt = connection.createStatement();
                    System.out.println("t2 开始事务");
                    stmt.execute("SELECT  * from castest");
                    ResultSet resultSet;
                    //read-consistent-view,多次读取相同的记录,mysql要保证结果一致
                    //如果这里读取id=20的记录,在下一次读取时,其他事务删除该记录并提交事务,重复读取值不受影响
                    //如果这里不读取的话不会创建read-view,可重复读可能造成不能及时读取到其它事务提交的数据
                    resultSet = stmt.executeQuery("SELECT age FROM user where id=20");
                    while(resultSet.next()){
                        System.out.println("t2 删除之前的age = "+resultSet.getInt("age"));
                    }
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("t2 执行查询前等待t1修改数据");
                    TimeUnit.SECONDS.sleep(3);
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

//                    stmt.execute("INSERT INTO user (id, age) VALUE (20, 77)");
                    //可重复读导致没有读取到其它事务更新的结果,但是如果依赖最新值修改是会用到数据库中最新的结果的
                    stmt.execute("UPDATE  user  set age = 996 WHERE  id=20");
//                    connection.rollback();
                    resultSet = stmt.executeQuery("SELECT age FROM user where id=20");
                    if(resultSet.next()){
                        System.out.println("t2 读取到的age = "+resultSet.getInt("age"));
                    }else {
                        System.out.println("t2 读取不到数据");
                    }
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
