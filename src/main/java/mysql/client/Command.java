package mysql.client;

/**
 * @author yangqf
 * @version 1.0 2016/9/13
 */
public class Command{
    //A COM_QUERY is used to send the server a text-based query that is executed immediately.
    //返回数据由列定义和row数据构成
    //响应:http://dev.mysql.com/doc/internals/en/com-query-response.html
    public static final byte QUERY = 3;


}
