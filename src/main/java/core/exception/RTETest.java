package core.exception;

/**
 * Created by Administrator on 2016/11/12.
 */
public class RTETest {
    private void t(){
        throw new RuntimeException("test");//测试字节码会不会
    }
}
