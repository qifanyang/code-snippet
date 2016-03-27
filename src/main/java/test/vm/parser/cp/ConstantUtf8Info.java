package test.vm.parser.cp;

import test.vm.parser.*;

import java.io.DataInput;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
@lombok.Data
public class ConstantUtf8Info implements IConstantPoolObject {
    U1 tag = U1.of(1);
    U2 length;//bytes长度
    U1 bytes[];//utf8编码的字节数组

    @Override
    public void parse(ClassFileReader reader) throws Exception {

    }
}
