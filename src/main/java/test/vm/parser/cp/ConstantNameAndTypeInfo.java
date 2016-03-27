package test.vm.parser.cp;

import test.vm.parser.IConstantPoolObject;

import java.io.DataInput;

/**
 *用于表示字段和方法,由名字和描述构成,当然这里也只是存储指向utf8_info的索引
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class ConstantNameAndTypeInfo implements IConstantPoolObject {
    byte tag = 12;
    int name_index;//指向常量池的一个索引,在常量池中的数据类型为utf8_info
    int descriptor_index;//描述符,字段类型和方法描述符


    @Override
    public void parse(DataInput dataInput) throws Exception {
//        Utils.readFromDataInput(this, dataInput);
        name_index = dataInput.readUnsignedShort();
        descriptor_index = dataInput.readUnsignedShort();
    }
    public byte getTag() {
        return tag;
    }
    public void setTag(byte tag) {
        this.tag = tag;
    }

    public int getName_index() {
        return name_index;
    }

    public void setName_index(int name_index) {
        this.name_index = name_index;
    }

    public int getDescriptor_index() {
        return descriptor_index;
    }

    public void setDescriptor_index(int descriptor_index) {
        this.descriptor_index = descriptor_index;
    }
}
