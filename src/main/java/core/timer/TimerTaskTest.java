package core.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/4/15.
 */
public class TimerTaskTest {
    public static void main(String[] args) {
        //创建TimerThread(Thread线程子类),并启动线程,while(true)中调度任务
        //TimerThread包含一个TaskQueue,队列为优先队列,使用二叉堆实现,任务下一次执行时间作为优先条件
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            long lastExecuteTime = System.currentTimeMillis();
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);//费时操作,执行时间大于间隔时间,单线程需要等到线程返回后再执行任务
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lastExecuteTime = System.currentTimeMillis();
                System.out.println("i am timer task, i am working!!!, last run time = " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(lastExecuteTime)));
            }
        };

//          timer.schedule(task, 5000,5000);//任务调度基于java Object.wait(time)利用虚拟机的线程调度来
//        timer.schedule(task, 5000,5000);//会判断任务状态,抛出异常
        timer.scheduleAtFixedRate(task, 2000, 2000);

    }
}
