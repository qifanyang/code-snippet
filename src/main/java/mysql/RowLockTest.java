package mysql;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * 行锁测试
 * tx1 select * from row_lock_test for update 获取锁成功
 * tx2 select * from row_lock_test for update 获取锁失败,被挂起
 *
 * tx1 检查状态是否为未完成,是执行下面,否则结束
 * tx1 所属于应用程序执行完逻辑,然后更新数据
 * tx1 结束,释放锁
 *
 * tx2 获取锁,检查状态是否为未完成,是执行下面,否则结束
 * tx2 所属于应用程序执行完逻辑,然后更新数据
 * tx2 结束,释放锁
 *
 * 测试结果
 * tx1获取锁成功后,检查状态并完成业务逻辑,然后更新状态为已处理,提交事务释放锁
 * tx2等tx1释放锁后,执行状态检查,能够读取到tx1提交的status值,检查到状态为已完成不执行业务逻辑
 *
 * 测试一致性读场景,在获取行锁前执行一个select查询
 * 测试结果,一致性读不会造成tx2无法读取tx1更改的status
 *
 * 行锁用于在多节点定时任务执行:
 * 当数据库中某条消息处于未处理状态,每个节点可以开启定时任务检查该消息状态
 * 结合数据库行锁(select * for update)可以实现互斥和可见性,用来决定是否
 * 处理业务逻辑
 *
 * 应用场景:
 * 第三方通知需要快速返回,而本地处理需要一定处理时间,就需要异步处理,直接使用executor,当宕机时消息会丢失
 * 1.可以引入持久化的MQ,MQ消费者需要实现幂等
 * 2.不引入MQ,可以将消息存储到MySQL,引入中间状态,多节点定时轮询处理
 *
 *
 *
 *
 *
 *
 * Created by yangqifan on 2017/7/9.
 */
public class RowLockTest {
    /*
CREATE TABLE `row_lock_test` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `status` tinyint(4) NOT NULL COMMENT '处理状态, 1:表示未处理, 2:表示已处理',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8


CREATE TABLE `business_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `data` varchar(50) DEFAULT NULL COMMENT '修改的业务数据',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8


     */



    public static void main(String[] args) throws InterruptedException {

        RowLockTest rowLockTest = new RowLockTest();
        rowLockTest.test();

    }

    private void test() throws InterruptedException {
        Thread t1 = new Thread(new Task("tx1",10));
        t1.start();
        TimeUnit.SECONDS.sleep(3);
        Thread t2 = new Thread(new Task("tx2", 2));
        t2.start();
    }


    class Task extends DBHelper implements Runnable{

        private int runTimeInSeconds;
        private String name;

        public Task(String name, int runTimeInSeconds){
            this.name = name;
            this.runTimeInSeconds = runTimeInSeconds;
        }

        @Override
        public void run() {
            Connection connection = getConnection();
            try {
                connection.setAutoCommit(false);

                //添加一致性读
                Statement stmt = connection.createStatement();
                ResultSet resultSet1 = stmt.executeQuery("SELECT * FROM row_lock_test");
                while (resultSet1.next()) {
                    System.out.println(resultSet1.getLong(1));
                    System.out.println(resultSet1.getInt(2));
                }

                System.out.println(name + " 将获取行锁");
                PreparedStatement ps = connection.prepareStatement("SELECT status FROM row_lock_test where id=1 FOR  UPDATE ");
                ResultSet resultSet = ps.executeQuery();
                System.out.println(name + " 获取行锁OKOKOK");
                while (resultSet.next()) {
                    int status = resultSet.getInt(1);
                    System.out.println(name +" read status = " + status);
                    if(status == 1){
                        System.out.println(name +" read status 1 表示任务未完成,需要处理业务数据");
                    }else {
                        System.out.println(name + " 不需要处理");
                        connection.commit();
                        return;
                    }
                }



                //update business data
                String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                ps = connection.prepareStatement("UPDATE business_data SET data = ? WHERE id = 1");
                ps.setString(1, name + "-" + time);
                ps.execute();

                //update status to be 2
                ps = connection.prepareStatement("UPDATE row_lock_test SET status = ? WHERE id = 1");
                ps.setInt(1, 2);
                ps.execute();
                System.out.println(name + " finish business");

                //do somthing
                TimeUnit.SECONDS.sleep(runTimeInSeconds);
                System.out.println(name + " end running ...");
                System.out.println(name + " 释放行锁");
                connection.commit();



            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                closeConnection(connection);
            }

        }
    }

}
