package mysql.binlog.datatype;

import lombok.Data;

/**
 * six byte for int, java don't have this same data type
 * @author yangqf
 * @version 1.0 2016/9/2
 */
@Data
public class Integer6{
    private byte[] bytes = new byte[6];

    public Integer6(byte[] bytes){
        if(null != bytes && bytes.length != 6){
            throw new IllegalStateException("bytes length muse equal 6");
        }
        this.bytes = bytes;
    }

    public Integer6(){
    }

    /**
     * java byte --> int 为带符号扩展, 当强转为byte 只是截取,值不会有问题
     * @return
     */
    public long  value(){
        return (bytes[0] | bytes[1] << 8 | bytes[2] << 16 | bytes[3] << 24 | bytes[4] << 32
                | bytes[5] << 40);
    }


}
