package mysql.binlog.datatype;

/**
 * @author yangqf
 * @version 1.0 2016/9/2
 */
public class Integer3{
    private byte[] bytes = new byte[3];

    public Integer3(byte[] bytes){
        if(null != bytes && bytes.length != 3){
            throw new IllegalStateException("bytes length muse equal 3");
        }
        this.bytes = bytes;
    }

    public Integer3(){
    }


    /**
     * java byte --> int 为带符号扩展, 当强转为byte 只是截取,值不会有问题
     * @return
     */
    public int value(){
        return (bytes[0] | bytes[1] << 8 | bytes[2] << 16);
    }
    private int value1(){
//        System.out.println(bytes[0] );
//        System.out.println(bytes[1] << 8);
//        System.out.println(bytes[2] << 16);
        //加运算优先级比<<优先级高,要使用括号
        return (bytes[0] + (bytes[1] << 8) + (bytes[2] << 16));
    }

    public static void main(String[] args){
        Integer3 i3 = new Integer3();
        System.out.println(i3.value());

        i3.bytes[0] = 0x03;
        i3.bytes[1] = 0x03;
        System.out.println(i3.value());
        System.out.println(i3.value1());
    }
}
