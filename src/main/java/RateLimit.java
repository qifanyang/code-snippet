import java.util.concurrent.atomic.AtomicInteger;

/**
 * 滑动窗口限流工具类  https://segmentfault.com/a/1190000041548601
 *
 * @author https://www.wdbyte.com
 */
public class RateLimit {
    /**
     * 阈值
     */
    private int qps = 2;
    /**
     * 时间窗口总大小（毫秒）
     */
    private long windowSize = 1000;
    /**
     * 多少个子窗口
     */
    private Integer windowCount = 10;
    /**
     * 窗口列表
     */
    private WindowInfo[] windowArray = new WindowInfo[windowCount];

    public RateLimit(int qps) {
        this.qps = qps;
        long currentTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < windowArray.length; i++) {
            windowArray[i] = new WindowInfo(currentTimeMillis, new AtomicInteger(0));
        }
    }

    /**
     * 1. 计算当前时间窗口
     * 2. 更新当前窗口计数 & 重置过期窗口计数
     * 3. 当前 QPS 是否超过限制
     *
     * @return
     */
    public synchronized boolean tryAcquire() {
        long currentTimeMillis = System.currentTimeMillis();
        // 1. 计算当前时间窗口
        int currentIndex = (int) (currentTimeMillis % windowSize / (windowSize / windowCount));
        // 2.  更新当前窗口计数 & 重置过期窗口计数
        int sum = 0;
        for (int i = 0; i < windowArray.length; i++) {
            WindowInfo windowInfo = windowArray[i];
            //过期窗口滑动
            if ((currentTimeMillis - windowInfo.getTime()) > windowSize) {
                windowInfo.getNumber().set(0);
                windowInfo.setTime(currentTimeMillis);//活动
            }
            //单个子窗口内增加计数
            if (currentIndex == i && windowInfo.getNumber().get() < qps) {
                windowInfo.getNumber().incrementAndGet();
            }
            //整个窗口计数
            sum = sum + windowInfo.getNumber().get();
        }
        // 3. 当前 QPS 是否超过限制
        return sum <= qps;
    }

    public static void main(String[] args) {
        RateLimit rateLimit = new RateLimit(3);
        rateLimit.tryAcquire();
    }

    static class WindowInfo {
        // 窗口开始时间
        private Long time;
        // 计数器
        private AtomicInteger number;

        public WindowInfo(long time, AtomicInteger number) {
            this.time = time;
            this.number = number;
        }
        // get...set...

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        public AtomicInteger getNumber() {
            return number;
        }

        public void setNumber(AtomicInteger number) {
            this.number = number;
        }
    }

}

