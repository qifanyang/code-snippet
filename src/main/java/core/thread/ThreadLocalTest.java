package core.thread;

/**
 * ThreadLocal 简写 TL
 * InheritThreadLocal 简写 ITL
 * TransmittableThreadLocal 简写TTL
 *
 * 可继承ThreadLocal, 是当前线程创建新的线程初始化时, 会把当前线程的inheritThreadLocal
 * copy到新线程的ITL, 在新的线程类使用同一个InhteritThreadLocal访问
 * 其实内部使用Thread.currentThread() 获取新的线程, 感觉是在访问父线程set的值, 其实不是
 * 内部是两份值, 访问的是copy的值(浅拷贝),
 * 使用对象封装可以实现父子线程通信, 但在使用线程
 * 池会存在问题, 线程池中的线程归返到线程池时不手动清理ITL会保留父线程TL值, 当重复使用线程时
 * 因为没有新建线程所以不会将父线程值传递给新的线程, 就算使用对象封装, 也无法更改对象值实现,
 * 因为上一次ITL传递给哪个线程和这一次是不一样的(除非线程池只有一个线程)
 */
public class ThreadLocalTest {
}
