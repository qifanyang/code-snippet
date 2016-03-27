package test.vm.parser.cp;

import test.vm.parser.*;

import java.io.DataInput;
import java.io.IOException;

/**
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
@lombok.Data
public class ConstantLongInfo implements IConstantPoolObject {
    U1 tag = U1.of(5);
    U4 high_bytes[] = new U4[4];
    U4 low_bytes[] = new U4[4];


    @Override
    public void parse(ClassFileReader reader) throws Exception {

    }
}
