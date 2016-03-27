package test.vm.parser;

/**
 * 用于解析class文件调试,输出十六进制,
 * @author yangqf
 * @version 1.0 2016/3/27
 */
public class HexFormat {

    private int offset;//换行

    public void format(byte b){
        format();
        String hexString = Integer.toHexString(b);
        if(hexString.length() == 1){
            hexString = "0"+hexString;
        }
        System.out.print(hexString);
        offset++;
    }

    public void format(short s){
        byte high = (byte) ((s & 0xF0) >> 8);
        byte low = (byte) (s & 0x0F);
        format(high);
        format(low);
    }

    public void format(int i){
        short high = (short) (i & 0xFF00 >> 16);
        short low = (short) (i & 0xFF);
        format(high);
        format(low);
    }

    public void format(byte[] bytes){
        for(byte b : bytes){
            format(b);
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
        hexFormat.format((byte)202);
    }
}
