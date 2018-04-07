package hytstrix;

import com.netflix.hystrix.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by yangqifan on 2018/3/3.
 */
public class CommandHelloWorld extends HystrixCommand<String>{


    private final String name;

    public CommandHelloWorld(String name) {
//        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("SystemX"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("PrimaryCommand"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("PrimaryCommand"))
                .andCommandPropertiesDefaults(
                        // we default to a 600ms timeout for primary
                        HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(600)));
        this.name = name;
    }

    @Override
    protected String run() {
        try {
            Thread.sleep(960);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello " + name + "!";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CommandHelloWorld helloWorld = new CommandHelloWorld("yang");
//        System.out.println(helloWorld.execute());
        Future<String> future = helloWorld.queue();
        System.out.println(future.get());
    }
}

