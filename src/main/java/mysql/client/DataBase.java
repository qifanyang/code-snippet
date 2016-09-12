package mysql.client;

import lombok.Data;

/**
 * 操作数据库首先要获得数据库对象,然后开启一个会话{@link Session_Old},通过会话执行sql
 * @author yangqifan
 */
@Data
public class DataBase{

    private String url;
    private String account;
    private String password;

    public Session_Old getSession(){
        return null;
    }

}
