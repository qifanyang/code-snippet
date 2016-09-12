package mysql.client;

import mysql.client.packet.MySQLPacketDecoder;

import java.io.IOException;
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

        TimeUnit.SECONDS.sleep(10000);

    }
}
