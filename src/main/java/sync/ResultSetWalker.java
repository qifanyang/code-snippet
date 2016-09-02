package sync;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yangqf
 * @version 1.0 2016/5/16
 */
public interface ResultSetWalker{

    public void beforeWalk(String tableName);

    //遍历结果集
    public void walk(ResultSet resultSet) throws SQLException;

    public void afterWalk();
}
