package test.mysql.binlog;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author yangqf
 * @version 1.0 2016/8/25
 */
public class BinlogReaderTest{
    public static void main(String[] args) throws Exception{
        InputStream ras = BinlogReaderTest.class.getClassLoader().getResourceAsStream("mysql-bin.000020");

        DataInputStream dis = new DataInputStream(ras);

        //4 byte magic number
        System.out.println(Integer.toHexString(dis.readByte()));
        System.out.println(Integer.toHexString(dis.readByte()));
        System.out.println(Integer.toHexString(dis.readByte()));
        System.out.println(Integer.toHexString(dis.readByte()));

        System.out.println(0x57a2f48e);
    }
}
