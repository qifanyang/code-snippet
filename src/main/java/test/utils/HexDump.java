package test.utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author yangqf
 * @version 1.0 2016/8/27
 */
public class HexDump{
    final static char[] digits = {
            '0' , '1' , '2' , '3' , '4' , '5' ,
            '6' , '7' , '8' , '9' , 'a' , 'b' ,
            'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
            'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
            'o' , 'p' , 'q' , 'r' , 's' , 't' ,
            'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    };

    public static void print(InputStream inputStream) throws IOException{
        DataInputStream dis = new DataInputStream(inputStream);
        int offset = 0;
        byte value = -1;
        while(dis.available() > 0){
            byte b = dis.readByte();
            int hight = (b & 0xf0) >> 4;
            int low = b & 0x0f;
            StringBuilder sb = new StringBuilder();
            sb.append(digits[hight]);
            sb.append(digits[low]);
            sb.append(" ");
            System.out.print(sb);
            ++offset;
            if(offset == 16){
                System.out.println();
                offset = 0;
            }
        }
    }

    public static void main(String[] args) throws IOException{
        InputStream resourceAsStream = HexDump.class.getClassLoader().getResourceAsStream("mysql-bin.000020");
        print(resourceAsStream);
    }

}
