package mysql.client;


import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * @author yangqf
 * @version 1.0 2016/9/12
 */
public class MainNew{
    public static void main(String[] args) throws SQLException, InterruptedException{
        Session session = new Session();
        session.connect();

//        ResultSetX resultSetX = session.executeSQL("SELECT * FROM user WHERE id = 20");
        ResultSetX resultSetX = session.executeSQL("SELECT * FROM user");
        while(resultSetX.next()){
            System.out.println("id = " + resultSetX.getLong(1) + ",age = " + resultSetX.getLong(2)+",num = " + resultSetX.getLong(3));
        }

        TimeUnit.SECONDS.sleep(10000);

    }
}
