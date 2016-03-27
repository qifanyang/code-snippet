package test.vm.parser;

import java.io.DataInput;
import java.io.IOException;

/**
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public interface IConstantPoolParser {

    public void parse(DataInput dataInput) throws Exception;

    public byte getTag();

}
