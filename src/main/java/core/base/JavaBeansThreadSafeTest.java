package core.base;

import lombok.Data;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yangqf
 * @version 1.0 2016/11/19
 */
public class JavaBeansThreadSafeTest{
    public static void main(String[] args) throws InterruptedException{
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.DiscardPolicy());
        Random random = new Random();

        Thread.sleep(3000L);
        AtomicLong atomicLong = new AtomicLong();
            Value value = new Value();
        while(true){
            long seq = atomicLong.incrementAndGet();
            int m = random.nextInt();
            value.setX(m);
            int n = random.nextInt();
            value.setY(n);
            value.setZ(m+n);
            value.setSeq(seq);
//            System.out.println("random creat, seq ="+seq+ ", x="+m+" ,y="+n+", z="+value.getZ()+"");
            executor.submit(()->{
                while(true){
                    int x = value.getX();
                    int y = value.getY();
                    if(x+y != value.getZ()){
                        //value在获取值的时候，外面set， 所以java bean 如果对外发布了在其它线程中访问，会导致对象
                        //状态不一致
                        System.out.println("find problem,seq ="+value.getSeq()+" x="+x+" ,y="+y+", z="+value.getZ());
                    }else {
//                        System.out.println("x1 = " + x);
                    }
                }
            });
        }



    }

    @Data
    static class Value{
        private int x;
        private int y;
        private int z;

        private long seq;
    }
}
