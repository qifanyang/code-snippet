package mysql.jdbc;

import com.mysql.jdbc.Statement;
import mysql.BaseDB;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yangqf
 * @version 1.0 2016/9/22
 */
public class GetKeyTest extends BaseDB{

    public static void main(String[] args) throws Exception{
//        insertGetKey();
          batchUpdate();
    }

    //获取插入数据的主键,是通过结果集的方式,创建声明的时候要指定RETURN_GENERATED_KEYS参数
    private static void insertGetKey() throws SQLException{

        Connection conn = getConnection();
        String sql = "INSERT INTO user (age) VALUES (2),(3),(4)";//单条和多条都支持
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        //获取插入的主键
        ps.executeUpdate();

        ResultSet generatedKeys = ps.getGeneratedKeys();
        while(generatedKeys.next()){
            System.out.println("生成主键 id = " + generatedKeys.getLong(1));
        }

        conn.close();
    }

    //跟新不会返回受影响的主键id
    private static void batchUpdate() throws SQLException, NoSuchFieldException, IllegalAccessException{
        Connection connection = getConnection();
        String sql = "update user set age = ? where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
        ps.setInt(1, 9);
        ps.setInt(2, 20);
        ps.addBatch();
//
        ps.setInt(1, 99);
        ps.setInt(2, 21);
        ps.addBatch();
//
//
//        ps.setInt(1, 999);
//        ps.setInt(2, 22);
//        ps.addBatch();

        int[] ints = ps.executeBatch();//批量更新,jdbc driver是依次发送sql命令
        ps.execute();
        ResultSet generatedKeys = ps.getGeneratedKeys();
        while(generatedKeys.next()){
            System.out.println("更新主键 id = " + generatedKeys.getLong(1));
        }
//        ResultSet resultSet = ps.getResultSet();
//        Class<? extends ResultSet> cls = resultSet.getClass();
//        Field fields = cls.getField("fields");
//        fields.setAccessible(true);
//        com.mysql.jdbc.Field f = (com.mysql.jdbc.Field) fields.get(resultSet);
//        System.out.println(f.getName());
//        System.out.println();
//        for(int i : ints){
//            System.out.println("update count "+i);
//        }


    }
}
