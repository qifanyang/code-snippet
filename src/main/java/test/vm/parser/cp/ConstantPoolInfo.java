package test.vm.parser.cp;

import test.vm.parser.IConstantPoolObject;

/**
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class ConstantPoolInfo {
    byte tag;
    int info[];
    IConstantPoolObject constantPoolObject;

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public int[] getInfo() {
        return info;
    }

    public void setInfo(int[] info) {
        this.info = info;
    }

    public IConstantPoolObject getConstantPoolObject() {
        return constantPoolObject;
    }

    public void setConstantPoolObject(IConstantPoolObject constantPoolObject) {
        this.constantPoolObject = constantPoolObject;
    }
}
