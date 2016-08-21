package test.core.base;

import javax.xml.bind.SchemaOutputResolver;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangqf
 * @version 1.0 2016/8/15
 */
public class AnonymousInnerClassTest{
    public static void main(String[] args) throws UnsupportedEncodingException{
        List<Runnable> list = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            list.add(new Runnable(){
                @Override
                public void run(){
                    System.out.println(this);
                }
            });
        }

        for(Runnable r : list){
            r.run();
        }

        System.out.println(URLEncoder.encode("你好","utf8"));
    }
}
