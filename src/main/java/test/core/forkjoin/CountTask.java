package test.core.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/1/23
 */
public class CountTask extends RecursiveTask<Integer>{

    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        //分解任务
        boolean canCompute = (end - start) <= 2;
        if(canCompute){
            for(int i = start; i <= end; i++){
                sum += i;
            }
        }else {

            int middle = (start + end)/2;
            CountTask leftTask = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle + 1, end);

            //执行子任务
            leftTask.fork();
            rightTask.fork();

             //获取结果
            Integer left = leftTask.join();
            Integer right = rightTask.join();

            sum = left + right;
        }

        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        CountTask task = new CountTask(1, 4);

        ForkJoinTask<Integer> result = forkJoinPool.submit(task);
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
