package test.core.mysql;

import java.sql.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author yangqf
 * @version 1.0 2016/7/20
 */
public class CASTest{
    private static String driver = "com.mysql.jdbc.Driver";
    private static String passwrod = "";
    private static String userName = "root";
    private static String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&amp;characterEncoding=utf-8";


    public static Connection getConnection(){

        try{
//            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, userName, passwrod);

           return conn;

//            if(conn != null){
//                try{
//                    conn.close();
//                }catch(SQLException e){
//                    e.printStackTrace();
//                }
//            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static void concurrentUpdate(){
        new Thread(){
            @Override
            public void run(){
                try{
                     final Connection connection = getConnection();
                    connection.setAutoCommit(false);
                    System.out.println("ready..."+ connection);
                    cdl.await();
                    xx(connection);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void xx(Connection connection) throws SQLException, InterruptedException{
        int updateCount = 0;
        String sql = "update castest set version=version+1 where id=1 and version=?";
        String sle = "select version from castest where id=1";
        for(int i = 0; i < 100; i++){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sle);
            resultSet.next();
            int version = resultSet.getInt("version");
            connection.commit();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, version);
            int update = preparedStatement.executeUpdate();
            if(update == 1){
                ++updateCount;
            }
            connection.commit();
//            Thread.sleep(3000);
        }
        System.out.println("update = " + updateCount);
    }

    private static CountDownLatch cdl = new CountDownLatch(1);
    public static void main(String[] args) throws InterruptedException{

        for(int i = 0;i < 50; i++){
            concurrentUpdate();
        }
        Thread.sleep(3000);
        long s = System.currentTimeMillis();
        cdl.countDown();
        System.out.println("用时 = " + (System.currentTimeMillis() - s));
    }


}
