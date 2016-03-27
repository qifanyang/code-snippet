package test.vm.parser;

/**
 * 用于解析class文件调试,输出十六进制,
 * @author yangqf
 * @version 1.0 2016/3/27
 */
public class HexFormat {

    private int offset;//换行

    /**
     * 格式化输出无符号byte,截取低8位
     * @param s
     */
    public void formatUnsignedByte(short s){
//        byte high = (byte) ((s & 0xF0) >> 8);
        short b = (short) (s & 0xFF);
        format();
        String hexString = Integer.toHexString(b).toUpperCase();
        if(hexString.length() == 1){
            hexString = "0"+hexString;
        }
        System.out.print(hexString);
        offset++;
    }

    /**
     * 格式化输出无符号short,截取低16位
     * @param i
     */
    public void formatUnsignedShort(int i){
        short high = (short) ((i & 0xFF00) >> 8);
        short low = (short) (i & 0xFF);
        formatUnsignedByte(high);
        formatUnsignedByte(low);
    }

    public void format(byte[] bytes){
        for(byte b : bytes){
            formatUnsignedByte(b);
        }
    }

    private void format(){
        if(offset % 16 == 0){
            System.out.println();
            System.out.print(" ");
        }else {
            System.out.print(" ");
        }
    }

    public static void main(String[] args) {
        HexFormat hexFormat = new HexFormat();
        hexFormat.formatUnsignedShort(51966);
    }
}
