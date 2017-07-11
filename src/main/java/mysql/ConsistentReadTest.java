package mysql;

import java.sql.*;
import java.util.concurrent.TimeUnit;

/**
 *https://dev.mysql.com/doc/refman/5.7/en/glossary.html#glos_consistent_read
 *
 * 当两个事务访问相同数据时,tx1读取数据会创建一个时间点的数据快照,不用管数据被其他事务修改,
 * 如果tx1查询的数据被其它事务修改,tx1再次查询时MySQL可以从undo log重新构建出最初的数据.
 *
 * 一致性读避免并发时加锁处理,tx1读取数据不用等待其它事务提交事务,有点类似java中的CopyOnWriteList
 * 只不过java是拷贝数据出来修改,不是基于undo log
 *
 * 在可重复读隔离级别,一致性读在第一次执行read操作时,基于当前时间点创建快照
 * 在读提交隔离级别,每次read操作,都会重置快照
 *
 * NOTE:
 * 某些场景需要tx1读取到其它事务更改的数据,也就是忽略一致性读,可以使用:
 * 1.READ_COMMITTED, 但是mysql默认时可重复读级别,所以改这个不靠谱
 * 2.locking read
 *
 *
 * Created by yangqifan on 2017/7/9.
 */
public class ConsistentReadTest {


    public static void main(String[] args) throws InterruptedException {
        long l = System.nanoTime();
        long l1 = System.nanoTime();
        System.out.println(l);
        System.out.println(l1);
        new ConsistentReadTest().test();
    }

    private void test() throws InterruptedException {
        Thread t1 = new Thread(new Task("tx1", 3, 88));
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        Thread t2 = new Thread(new Task("tx2", 6, 99));
        t2.start();
    }


    class Task extends DBHelper implements Runnable{

        private int runTimeInSeconds;
        private String name;
        private int age;

        public Task(String name, int runTimeInSeconds, int age){
            this.name = name;
            this.runTimeInSeconds = runTimeInSeconds;
            this.age = age;
        }

        @Override
        public void run() {

            Connection connection = getConnection();

            try {

                connection.setAutoCommit(false);
//                connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                Statement stmt = connection.createStatement();
                System.out.println(name + " 创建一次性读");
                //
                ResultSet resultSet = stmt.executeQuery("SELECT age FROM user where id = 1");
                while (resultSet.next()){
                    int age = resultSet.getInt(1);
                    System.out.println(name + " age = " + age);
                }

                TimeUnit.SECONDS.sleep(runTimeInSeconds);

                //读取的值和A一样
                resultSet = stmt.executeQuery("SELECT age FROM user where id = 1 FOR UPDATE ");
                while (resultSet.next()){
                    int age = resultSet.getInt(1);
                    System.out.println(name + " read again age = " + age);
                }

                PreparedStatement ps = connection.prepareStatement("UPDATE user SET age = ? WHERE id = 1");
                ps.setInt(1, age);
                ps.execute();
                System.out.println(name + " update age = " + age);
                resultSet = stmt.executeQuery("SELECT age FROM user where id = 1");
                while (resultSet.next()){
                    int age = resultSet.getInt(1);
                    System.out.println(name + " read self update age = " + age);
                }

                connection.commit();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    /*
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `age` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8

age初始值为1, 事务隔离级别为REPEATABLE_READ

tx1 创建一次性读
tx1 age = 1
tx2 创建一次性读
tx2 age = 1
tx1 read again age = 1
tx1 update age = 88
tx1 read self update age = 88
tx2 read again age = 1 //tx1更新了age, tx2读取值age还是1
tx2 update age = 99
tx2 read self update age = 99


age初始值为1, 事务隔离级别为READ_COMMITTED, tx2可以读取到最新的值

tx1 创建一次性读
tx1 age = 1
tx2 创建一次性读
tx2 age = 1
tx1 read again age = 1
tx1 update age = 88
tx1 read self update age = 88
tx2 read again age = 88 //tx2 能读取到tx1的更新值
tx2 update age = 99
tx2 read self update age = 99


age初始值为1, 不更改隔离级别, 使用locking read可以读取到最新值. select * for update

tx1 创建一次性读
tx1 age = 1
tx2 创建一次性读
tx2 age = 1
tx1 read again age = 1
tx1 update age = 88
tx1 read self update age = 88
tx2 read again age = 88 //tx2读取到tx1的更新值
tx2 update age = 99
tx2 read self update age = 99








     */
}
