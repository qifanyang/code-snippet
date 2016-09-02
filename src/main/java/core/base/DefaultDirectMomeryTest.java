package core.base;

import sun.misc.VM;

/**
 * 测试默认直接内存大小
 * @author yangqf
 * @version 1.0 2016/8/11
 */
public class DefaultDirectMomeryTest{
    public static void main(String[] args) throws InterruptedException{
//        http://hg.openjdk.java.net/jdk7u/jdk7u/jdk/file/55f6804b4352/src/share/classes/sun/misc/VM.java#l279
        Object s = System.getProperties().remove("sun.nio.MaxDirectMemorySize");
        System.out.println(s);

        //启动参数 -Xmx128m -Xms128m , 直接内存和totalMemory一样
        System.out.println(VM.maxDirectMemory());
        System.out.println(64 * 1024 * 1024);

        //http://bugs.java.com/bugdatabase/view_bug.do?bug_id=4391499
        //返回值不等于设置值
        System.out.println(Runtime.getRuntime().totalMemory());
        System.out.println(Runtime.getRuntime().maxMemory());
//        TimeUnit.SECONDS.sleep(10000000);

        //对于大量使用直接内存,如果堆内进行垃圾回收,如果直接内存不会垃圾回收,
        // 导致直接内存不会进行垃圾回收,然后堆内存+直接内存 > 机器内存, 进程会挂掉

    }
}
