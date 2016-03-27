package test.vm.parser.cp;

import test.vm.parser.IConstantPoolObject;

import java.io.DataInput;

/**
 * 表示方法句柄,
 * 如果kind为访问字段,那么index为指向constant_field_ref_info的索引
 * 如果kind为调用方法,那么index为指向constant_method_ref_info的索引
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class ConstantMethodHandleInfo implements IConstantPoolObject {
    byte tag = 15;
    int reference_kind;
    int reference_index;
    @Override
    public void parse(DataInput dataInput) throws Exception {
//        Utils.readFromDataInput(this, dataInput);
        reference_kind = dataInput.readUnsignedShort();
        reference_index = dataInput.readUnsignedShort();
    }

    @Override
    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public int getReference_kind() {
        return reference_kind;
    }

    public void setReference_kind(int reference_kind) {
        this.reference_kind = reference_kind;
    }

    public int getReference_index() {
        return reference_index;
    }

    public void setReference_index(int reference_index) {
        this.reference_index = reference_index;
    }
}
