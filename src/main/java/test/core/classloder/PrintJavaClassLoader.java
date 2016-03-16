package test.core.classloder;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/1/22
 */
public class PrintJavaClassLoader {
    public static void main(String[] args) {
        System.out.println(PrintJavaClassLoader.class.getClassLoader());
        System.out.println(PrintJavaClassLoader.class.getClassLoader().getParent());
        System.out.println(PrintJavaClassLoader.class.getClassLoader().getParent().getParent());
        //System.out.println(PrintJavaClassLoader.class.getClassLoader().getParent().getParent().getParent());
    }
}
