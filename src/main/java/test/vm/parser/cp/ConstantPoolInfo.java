package test.vm.parser.cp;

/**
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class ConstantPoolInfo {
    byte tag;
    byte info[];

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public byte[] getInfo() {
        return info;
    }

    public void setInfo(byte[] info) {
        this.info = info;
    }
}
