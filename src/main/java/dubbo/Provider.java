package dubbo;

/**
 * Created by Administrator on 2017/3/29.
 */
public interface Provider {
    User build(String name) throws MyException;
}
