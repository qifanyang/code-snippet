package mysql.client;

import com.mysql.jdbc.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author yangqf
 * @version 1.0 2016/9/12
 */
public class MySQLEncrypted{

    public static byte[] scramble411(String password, String seed) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        //对密码sha-1签名,数据库存储的是改值
        byte[] passwordHashStage1 = md.digest(password.getBytes(ProtocolUtils.charset_utf8));
        md.reset();//

        //对密码二次sha-1签名
        byte[] passwordHashStage2 = md.digest(passwordHashStage1);
        md.reset();

        byte[] seedAsBytes = StringUtils.getBytes(seed, "ASCII"); // for debugging
        md.update(seedAsBytes);
        md.update(passwordHashStage2);

        byte[] toBeXord = md.digest();

        int numToXor = toBeXord.length;

        for (int i = 0; i < numToXor; i++) {
            toBeXord[i] = (byte) (toBeXord[i] ^ passwordHashStage1[i]);
        }

        return toBeXord;
    }
}
