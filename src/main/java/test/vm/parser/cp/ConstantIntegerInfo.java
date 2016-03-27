package test.vm.parser.cp;

import test.vm.parser.ClassFileReader;
import test.vm.parser.IConstantPoolObject;
import test.vm.parser.U1;
import test.vm.parser.Utils;

import java.io.DataInput;
import java.io.IOException;

/**
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
@lombok.Data
public class ConstantIntegerInfo implements IConstantPoolObject {
    U1 tag = U1.of(3);
    U1 bytes[] = new U1[4];//Big-Endian , byte short boolean char 都用integer表示


    @Override
    public void parse(ClassFileReader reader) throws Exception {
        reader.readBytes(bytes);
    }
}
