package test.vm.parser;

import java.io.DataInput;
import java.io.IOException;

/**
 * 常量池对象接口
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public interface IConstantPoolObject {

    public void parse(ClassFileReader reader) throws Exception;

    public U1 getTag();

}
