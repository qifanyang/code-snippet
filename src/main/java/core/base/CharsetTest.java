package core.base;

import java.nio.charset.Charset;
import java.util.SortedMap;

/**
 * @author yangqf
 * @version 1.0 2016/11/17
 */
public class CharsetTest{
    public static void main(String[] args){
        //Charset也是Server Provider Framework类型实现

        //Charet提供获取服务接口(service interface) API , Charset.forname(charset_name) , CharsetProvider是服务提供者
        //Charset同时是服务接口,charset.newDecoder.decode(...)
        //Charset没有提供服务器注册API, 获取服务器时根据服务名动态反射创建服务接口实例
        printProvidedJvmCharsetName();
    }

    private static void printProvidedJvmCharsetName(){
        Charset.availableCharsets().forEach((k,v)->{
//            System.out.println(k);
//            System.out.println(v);
            v.aliases().forEach((y)->{
                System.out.println(y);//输出字符集别名
            });
//            return;
            System.exit(0);//lambda表达式中无法退出循环
        });

    }
}
