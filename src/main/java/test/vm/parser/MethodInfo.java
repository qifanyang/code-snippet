package test.vm.parser;

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
public class MethodInfo {
    short access_flags;
    short name_index;
    short descriptor_index;
    short attributes_count;
    AttributeInfo attributes[];

    public void parse(DataInput dataInput) throws IOException {
        access_flags = dataInput.readShort();
        name_index = dataInput.readShort();
        descriptor_index = dataInput.readShort();
        attributes_count = dataInput.readShort();
        attributes = new AttributeInfo[attributes_count];
        for(int i = 0; i < attributes_count; i++){
            AttributeInfo attributeInfo = new AttributeInfo();
            attributeInfo.parse(dataInput);
            attributes[i] = attributeInfo;
        }
    }

    public short getAccess_flags() {
        return access_flags;
    }

    public void setAccess_flags(short access_flags) {
        this.access_flags = access_flags;
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

    public short getAttributes_count() {
        return attributes_count;
    }

    public void setAttributes_count(short attributes_count) {
        this.attributes_count = attributes_count;
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributeInfo[] attributes) {
        this.attributes = attributes;
    }
}
