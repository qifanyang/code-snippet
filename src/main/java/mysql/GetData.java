package mysql;

import java.sql.*;

/**
 * @author yangqf
 * @version 1.0 2016/8/22
 */
public class GetData{
    public static void main(String[] args) throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql://rdst5ai4d32fe3qd6if46public.mysql.rds.aliyuncs.com:3306/calm_dev", "uprd_stf_qry", "dZZglXoOrJ5WfgvVOnPh");
        Statement stmt = connection.createStatement();
        String sql = "SELECT server_update_time FROM discount_shop WHERE id=216 ";
        String s = "SELECT count(*) FROM commercial";
        ResultSet rs = stmt.executeQuery(sql);
        StringBuilder sb = new StringBuilder();
        int count = 0;
        while(rs.next()){
            Timestamp timestamp = rs.getTimestamp(1);
            System.out.println(timestamp.getNanos());
            System.out.println(timestamp.getTime());
        }


        connection.close();
    }

}
