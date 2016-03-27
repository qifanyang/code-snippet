package test.vm.parser.cp;

import test.vm.parser.ClassFileReader;
import test.vm.parser.IConstantPoolObject;
import test.vm.parser.U1;
import test.vm.parser.Utils;

import java.io.DataInput;

/**
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
@lombok.Data
public class ConstantFloatInfo  implements IConstantPoolObject {
    U1 tag = U1.of(4);
    U1 bytes[] = new U1[4];//Big-Endian


    @Override
    public void parse(ClassFileReader reader) throws Exception {

    }
}
