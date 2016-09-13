package mysql.client;

import com.mysql.jdbc.*;
import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author yangqf
 * @version 1.0 2016/9/12
 */
public class MysqlNativePasswordPlugin{

    public boolean nextAuthenticationStep(ByteBuf fromServer, List<ByteBuf> toServer, String pwd) throws SQLException{

        try {
            toServer.clear();

            ByteBuf bresp = null;

            if (fromServer == null || pwd == null || pwd.length() == 0) {
                bresp = ProtocolUtils.createLittleByteBuf(0);
            } else {
                byte[] bytes = MySQLEncrypted.scramble411(pwd, ProtocolUtils.readNullTerminalString(fromServer));
                bresp = ProtocolUtils.createLittleByteBuf(bytes.length).writeBytes(bytes);
            }
            toServer.add(bresp);

        } catch (NoSuchAlgorithmException nse) {
            throw SQLError.createSQLException(Messages.getString("MysqlIO.95") + Messages.getString("MysqlIO.96"), SQLError.SQL_STATE_GENERAL_ERROR, null);
        } catch (UnsupportedEncodingException e) {
            throw SQLError.createSQLException(Messages.getString("MysqlIO.95") + Messages.getString("MysqlIO.96"), SQLError.SQL_STATE_GENERAL_ERROR, null);
        }

        return true;
    }

}
