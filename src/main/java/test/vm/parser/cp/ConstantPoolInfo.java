package test.vm.parser.cp;

/**
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class ConstantPoolInfo {
    byte tag;
    int info[];

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
}
