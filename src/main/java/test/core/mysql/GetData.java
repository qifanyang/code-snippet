package test.core.mysql;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.*;

/**
 * @author yangqf
 * @version 1.0 2016/8/22
 */
public class GetData{
    public static void main(String[] args) throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql://rdst5ai4d32fe3qd6if46public.mysql.rds.aliyuncs.com:3306/calm_gld", "uprd_stf_qry", "dZZglXoOrJ5WfgvVOnPh");
        Statement stmt = connection.createStatement();
        String sql = "SELECT commercialID, commercialAdress,cityName,commercialName,brandID,status FROM commercial WHERE cityName='重庆市' ";
        String s = "SELECT count(*) FROM commercial";
        ResultSet rs = stmt.executeQuery(sql);
        StringBuilder sb = new StringBuilder();
        int count = 0;
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:/cq.txt")));
        while(rs.next()){
            count++;
            sb.append(rs.getLong(1)).append(",");
            sb.append(rs.getString(2)).append(",");
            sb.append(rs.getString(3)).append(",");
            sb.append(rs.getString(4)).append(",");
            sb.append(rs.getLong(5)).append(",");
            sb.append(rs.getInt(6)).append("\r\n");
            if(count > 100){
                count = 0;
                br.write(sb.toString());
                sb = new StringBuilder();
            }
        }
        if(count != 0){
            br.write(sb.toString());
        }

        br.flush();

        connection.close();
    }

}
