package test.vm.parser.cp;

import test.vm.parser.IConstantPoolParser;
import test.vm.parser.Utils;

import java.io.DataInput;
import java.io.IOException;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class ConstantNameAndTypeInfo implements IConstantPoolParser {
    byte tag = 12;
    short name_index;//指向常量池的一个索引,在常量池中的数据类型为utf8_info
    short descriptor_index;//描述符,字段类型和方法描述符


    @Override
    public void parse(DataInput dataInput) throws Exception {
//        Utils.readFromDataInput(this, dataInput);
        name_index = dataInput.readShort();
        descriptor_index = dataInput.readShort();
    }
    public byte getTag() {
        return tag;
    }
    public void setTag(byte tag) {
        this.tag = tag;
    }

    public short getName_index() {
        return name_index;
    }

    public void setName_index(short name_index) {
        this.name_index = name_index;
    }

    public short getDescriptor_index() {
        return descriptor_index;
    }

    public void setDescriptor_index(short descriptor_index) {
        this.descriptor_index = descriptor_index;
    }
}
