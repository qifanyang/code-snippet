package test.vm.parser.cp;

import test.vm.parser.ClassFileReader;
import test.vm.parser.IConstantPoolObject;
import test.vm.parser.U1;
import test.vm.parser.U2;

import java.io.DataInput;

/**
 *表示方法类型,这里和name_and_type有点重复
 * @author yangqf
 * @version 1.0 2016/3/26
 */
@lombok.Data
public class ConstantMethodTypeInfo implements IConstantPoolObject {
    U1 tag = U1.of(16);
    U2 descriptor_index;

    @Override
    public void parse(ClassFileReader reader) throws Exception {
        descriptor_index = reader.readU2();
    }
}
