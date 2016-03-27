package test.vm.parser.cp;

import test.vm.parser.ClassFileReader;
import test.vm.parser.IConstantPoolObject;
import test.vm.parser.U1;
import test.vm.parser.U2;

import java.io.DataInput;

/**
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
@lombok.Data
public class ConstantInvokeDynamicInfo implements IConstantPoolObject {
    U1 tag = U1.of(18);
    U2 bootstrap_method_attr_index;
    U2 name_and_type_index;


    @Override
    public void parse(ClassFileReader reader) throws Exception {

    }
}
