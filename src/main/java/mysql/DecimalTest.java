package mysql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mysql 字段decimal(10, 2)测试, 10代表precision, 2代表scale 和java的bigDecimal含义一致
 * @author yangqf
 * @version 1.0 2016/11/16
 */
public class DecimalTest extends BaseDB{

    public static void main(String[] args) throws SQLException{
        Connection connection = getConnection();

        //数据库decimal(10,2)
        BigDecimal bb =new BigDecimal("7.349999904632568");

        PreparedStatement ps = connection.prepareStatement("INSERT INTO tdecimal (`ddecimal`) VALUE (?)");
        ps.setBigDecimal(1, bb);
        ps.execute();
        ps.close();

        PreparedStatement ps1 = connection.prepareStatement("SELECT ddecimal FROM tdecimal WHERE id = 1");
        ResultSet resultSet = ps1.executeQuery();
        while(resultSet.next()){
            BigDecimal bigDecimal = resultSet.getBigDecimal(1);
            System.out.println(bigDecimal);//输出  7.35
        }
    }
}
