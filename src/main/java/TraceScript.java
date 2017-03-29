///**
// * Created by Administrator on 2017/3/11.
// */
//import com.sun.btrace.annotations.*;
//
//import static com.sun.btrace.BTraceUtils.*;
//import static sun.plugin.javascript.navig.JSType.Location;
//
//
//@BTrace
//public class TraceScript
//
//{
//
//    @OnMethod(clazz="com.huawei.main.BtraceTest", method="add", location=@Location(Kind.RETURN))
//
//    public static void func(int a, int b, @Return int result)
//
//    {
//
//        jstack();
//
//        println(strcat("para A: ", str(a)));
//
//        println(strcat("para B: ", str(b)));
//
//        println(strcat("result: ", str(result)));
//
//    }
//
//}
