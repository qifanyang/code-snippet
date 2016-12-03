//package core.proxy.jdk;
//
//import sun.misc.ProxyGenerator;
//import sun.reflect.misc.ReflectUtil;
//import utils.FileUtils;
//
//import java.lang.reflect.Modifier;
//import java.util.concurrent.atomic.AtomicLong;
//
///**
// * Title:
// * Description:
// * Copyright: Copyright (c) 2012
// * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
// *
// * @author yangqf
// * @version 1.0 2016/1/24
// */
//public class JDKProxyGenerateByteCode {
//    private static final String proxyClassNamePrefix = "$Proxy";
//
//    // next number to use for generation of unique proxy class names
//    private static final AtomicLong nextUniqueNumber = new AtomicLong();
//
//    //抽取jdk动态代理的代码,查看生成的代理类字节码
//    public static void test(Class<?> intf, ClassLoader loader){
//        String proxyPkg = null;     // package to define proxy class in
//        int accessFlags = Modifier.PUBLIC | Modifier.FINAL;
//
//            /*
//             * Record the package of a non-public proxy interface so that the
//             * proxy class will be defined in the same package.  Verify that
//             * all non-public proxy interfaces are in the same package.
//             */
////        for (Class<?> intf : interfaces) {
//            int flags = intf.getModifiers();
//            if (!Modifier.isPublic(flags)) {
//                accessFlags = Modifier.FINAL;
//                String name = intf.getName();
//                int n = name.lastIndexOf('.');
//                String pkg = ((n == -1) ? "" : name.substring(0, n + 1));
//                if (proxyPkg == null) {
//                    proxyPkg = pkg;
//                } else if (!pkg.equals(proxyPkg)) {
//                    throw new IllegalArgumentException(
//                            "non-public interfaces from different packages");
//                }
//            }
////        }
//
//        if (proxyPkg == null) {
//            // if no non-public proxy interfaces, use com.sun.proxy package
//            proxyPkg = ReflectUtil.PROXY_PACKAGE + ".";
//        }
//
//            /*
//             * Choose a name for the proxy class to generate.
//             */
//        long num = nextUniqueNumber.getAndIncrement();
//        String proxyName = proxyPkg + proxyClassNamePrefix + num;
//
//            /*
//             * Generate the specified proxy class.
//             */
//        byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
//                proxyName, new Class[]{intf}, accessFlags);
//
//        FileUtils.write("dump.class", proxyClassFile);
//
//        try {
//
////            return defineClass0(loader, proxyName,
////                    proxyClassFile, 0, proxyClassFile.length);
//        } catch (ClassFormatError e) {
//                /*
//                 * A ClassFormatError here means that (barring bugs in the
//                 * proxy class generation code) there was some other
//                 * invalid aspect of the arguments supplied to the proxy
//                 * class creation (such as virtual machine limitations
//                 * exceeded).
//                 */
//            throw new IllegalArgumentException(e.toString());
//        }
//    }
//
//
//    public static void main(String[] args) {
//        test(IProxy.class, null);//反编译类查看 $Proxy0
//    }
//}
