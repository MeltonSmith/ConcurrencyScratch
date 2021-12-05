package forkjoin;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 29.11.2021
 */
@Slf4j
public class ForkJoinPoolTest {

    @Test
    public void testForSubmitToForkJoin() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        var collect = new Random()
                .ints(1, 1000)
                .limit(10000)
                .boxed()
                .collect(Collectors.toList());

        ForkJoinTask<Integer> submit = forkJoinPool.submit(new CustomRecursiveTask(collect));

        System.out.println("processing");
        while (!submit.isDone()){
            Thread.sleep(1000);

            log.info(String.valueOf(forkJoinPool.getQueuedTaskCount()));
        }

        System.out.println(submit.get());


    }


    /**
     * blocking invocation
     */
    @Test
    public void testForInvoke() {
        ForkJoinPool forkJoinPool = new ForkJoinPool(2);
        var collect = new Random()
                .ints(1, 1000)
                .limit(100000)
                .boxed()
                .collect(Collectors.toList());

        Integer result = forkJoinPool.invoke(new CustomRecursiveTask(collect));
        System.out.println(result);
    }



}
