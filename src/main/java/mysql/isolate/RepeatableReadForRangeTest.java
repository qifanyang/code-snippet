package mysql.isolate;

import mysql.DBHelper;

import java.sql.*;
import java.util.concurrent.TimeUnit;

/*
 * 1. t2 开启事务--> t1开启事务,然后插入记录并提交-->t2再次查询,无法读取到新插入的记录,因为t1事务id大于t2事务id, select不返回
 * 2. t1开启事务-->t2开启事务-->t1插入数据,提交事务-->t2查询可以读取到t1新插入的数据(如果t2在t1提交事务前查询了,则为了读取一致性则无法查询到了)
 * @author yangqf
 * @version 1.0 2016/8/5
 */
public class RepeatableReadForRangeTest extends DBHelper {

    class Task {

        private String name;
        private int runTimeInSeconds;
        private Connection connection;

        public Task(String name, int runTimeInSeconds){
            this.name = name;
            this.runTimeInSeconds = runTimeInSeconds;
            this.connection = DBHelper.getConnection();
        }

        /*
         * 开启事务,通过执行一个查询表的数据开启事务, select 1 和 start ,
         */
        public void startTransaction() throws SQLException {
            connection.setAutoCommit(false);
            Statement stmt = connection.createStatement();
            stmt.execute("INSERT INTO start_trx (x) VALUE (1)");
            System.out.println(name+"开始事务");
        }

        public void sleepInSeconds(int seconds){
            try {
                System.out.println(name + " 休眠"+seconds+"秒");
                TimeUnit.SECONDS.sleep(seconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void executeSql(String sql) throws SQLException {
            System.out.println(name + " 执行sql : " + sql);
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        }

        public void executeUpdate(String sql) throws SQLException {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        }

        public void executeQuery(String sql) throws SQLException {
            System.out.println(name + " 执行查询 : " + sql);
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }
        }

        public void commit() throws SQLException {
            System.out.println(name + " 提交事务");
            connection.commit();
        }

        public void rollback() throws SQLException {
            System.out.println(name + " 回滚事务");
            connection.rollback();

        }


    }


    /*
      t1开启事务-->t2开启事务-->t1插入数据,提交事务-->
      t2在t1提交事务后查询可以读取到t1新插入的数据(如果t2在t1提交事务前查询了,则为了读取一致性则无法查询到了)
     */
    private void case1() throws SQLException {
        Task tx1 = new Task("tx1", 5);
        tx1.startTransaction();

        Task tx2 = new Task("tx2", 5);
        tx2.startTransaction();

        tx1.executeSql("insert into user(age) value (22)");
        tx1.commit();

        tx2.executeQuery("select age from user");
        tx2.commit();


        //数据库原始数据  只有一条记录 age=99
        //因为tx1插入一条数据, 因为tx1 事务id 小于 tx2 所以tx2可以读取到, (必须要tx1提交事务tx2才可以读取到)
        //输出
//        tx1开始事务
//         tx2开始事务
//        99
//        22  //22 为tx1插入的数据,tx2可以查询到

    }


    /*
     t1开启事务-->t2开启事务,并插入数据,t2提交-->t1查询
     理论结果:根据MySQL的mvvc, t1查询看不见t2插入的数据,因为t2事务id比t1事务id大, 输出一条记录
     */
    private void case2() throws SQLException {


        Task tx1 = new Task("tx1", 5);
        tx1.startTransaction();
//        tx1.executeQuery("select age from user");  //创建读一致性

//        System.out.println(tx1.connection.getTransactionIsolation());

        Task tx2 = new Task("tx2", 5);
        tx2.startTransaction();

        tx2.executeSql("insert into user(age) value (22)");
        tx2.commit();

        //tx1居然可以看见tx2提交的数据, 按照mvvc查询这里应该无法看见的
        //看见可以归为幻读
        tx1.executeQuery("select age from user");
        tx1.commit();
    }

    private void testUpdate() throws SQLException {
        Task tx1 = new Task("tx1", 5);
        tx1.startTransaction();
        //update会获取行锁
        tx1.executeUpdate("update user set age = 1 where id = 1");  //创建读一致性

//        System.out.println(tx1.connection.getTransactionIsolation());

        Task tx2 = new Task("tx2", 5);
        tx2.startTransaction();

        //tx1没有提交事务,tx2没法获取都行锁,这里会获取锁超时
        tx2.executeUpdate("update user set age = 1 where id = 1");
        tx2.commit();

        //tx1居然可以看见tx2提交的数据, 按照mvvc查询这里应该无法看见的
        tx1.executeQuery("select age from user");
        tx1.rollback();

    }

    public static void main(String[] args) throws SQLException {
        RepeatableReadForRangeTest repeatableReadForRangeTest = new RepeatableReadForRangeTest();
//        repeatableReadForRangeTest.case1();
//        repeatableReadForRangeTest.case2();
        repeatableReadForRangeTest.testUpdate();

    }
    public static void main11(String[] args) throws InterruptedException{


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

