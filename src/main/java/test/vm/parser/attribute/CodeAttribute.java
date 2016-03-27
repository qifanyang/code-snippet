package test.vm.parser.attribute;

import test.vm.parser.AttributeInfo;
import test.vm.parser.ClassFile;

import java.io.DataInput;
import java.io.IOException;

/**
 * @author yangqf
 * @version 1.0 2016/3/27
 */

@lombok.Data
public class CodeAttribute {
    short attribute_name_index;
    int attribute_length;
    short max_stack;
    short max_locals;
    int code_length;
    byte code[];
    short exception_table_length;
    //TODO 异常表
    /**
     * {
     * u2 start_pc;
     * u2 end_pc;
     * u2 handler_pc;
     * u2 catch_type;
     * } exception_table[exception_table_length];
     */
    short attribute_count;
    AttributeInfo attributes[];

    ClassFile cf;

    public void parse(DataInput dataInput) throws IOException {
        attribute_name_index = dataInput.readShort();
        attribute_length = dataInput.readInt();
        max_stack = dataInput.readShort();
        max_locals = dataInput.readShort();
        code_length = dataInput.readInt();
        code = new byte[code_length];
        dataInput.readFully(code);
        exception_table_length = dataInput.readShort();
        attribute_count = dataInput.readShort();
        attributes = new AttributeInfo[attribute_count];
        for(int i = 0; i < attribute_count; i++){
            AttributeInfo attributeInfo = new AttributeInfo();
            attributeInfo.setCf(cf);
            attributeInfo.parse(dataInput);
            attributes[i] = attributeInfo;
        }
    }

}
