package algorithm;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Title: api call rate limit algorithm , token bucket
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/2/14
 */
public class TokenBucket {

    private final int capacity;
    private final int tokensPerSeconds;
    private int tokens = 0;
    private long timestamp = System.currentTimeMillis();

    public TokenBucket(int tokensPerUnit, TimeUnit unit) {
        capacity = tokensPerSeconds = (int) (tokensPerUnit / unit.toSeconds(1L));
    }

    public boolean take() {
        long now = System.currentTimeMillis();
        tokens += (int) ((now - timestamp) * tokensPerSeconds / 1000);
        if (tokens > capacity) tokens = capacity;
        timestamp = now;
        if (tokens < 1f) return false;
        tokens--;
        return true;
    }

    /**
     * 与take方法的区别:
     * 1.take动态增加计数,每次收到请求都会根据时间戳计算可以增加token数量
     * 而take1每秒接收的请求数量是固定的
     */
    long time_marker = System.currentTimeMillis();
    int counter = 0;
    public boolean take1(){
        long now = System.currentTimeMillis();
        long pass_time = now - time_marker;
        if(pass_time > 1000){
            counter = 1;
            time_marker = now;
        }else{
            if(counter >= 4)return false;
            ++counter;
        }
        return true;
    }

    public static int randomIndex(int[] rates){
        int total = 0;
        for(int r : rates){
            total += r;
        }
        //在全区间上随机出一个数
        final int rnd = new Random().nextInt(total + 1);
        int tmp = rnd;
        for(int i = 0; i < rates.length; i++){
            if(tmp <= rates[i]){
                return i;
            }else{
                tmp -= rates[i];
            }
        }
        return 0;
    }



    public static void main(String[] args) throws InterruptedException {
        TokenBucket bucket = new TokenBucket(2, TimeUnit.SECONDS);
        Thread.sleep(1000L);
        for (int i = 0; i < 3; i++) {
           System.out.println(bucket.take());
        }
        Thread.sleep(1000L);
        for (int i = 0; i < 8; i++) {
            System.out.println(bucket.take1());
        }
        Thread.sleep(1000L);
        for (int i = 0; i < 8; i++) {
            System.out.println(bucket.take1());
        }
    }

}
