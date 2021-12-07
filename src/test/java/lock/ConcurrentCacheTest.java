package lock;

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 05.12.2021
 */
public class ConcurrentCacheTest {

    private ConcurrentCacheWithStampedLock cache;
    private static final String KEY_CACHE = "key";
    private static final Integer THREAD_NUMBERS = 4;
    private static final Integer OPERATIONS_PER_THREAD = 200;

    @Before
    public void init(){
        cache = new ConcurrentCacheWithStampedLock();
    }

    @SneakyThrows
    @Test
    public void concurrentPutTest() {

        final var latch = new CountDownLatch(1);

        final Runnable runnable = () -> {
            try {
                latch.await();
                Random random = new Random();
                int randomInt = random.nextInt();
                for (int i = 0; i < OPERATIONS_PER_THREAD; i++)
                    cache.put(KEY_CACHE, randomInt);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        final Thread[] threads = Stream.generate(() -> new Thread(runnable))
                .limit(THREAD_NUMBERS)
                .peek(Thread::start)
                .toArray(Thread[]::new);

        //start
        latch.countDown();

        for (Thread thread : threads) {
            thread.join();
        }

        Assert.assertEquals(1, (long) cache.size());
        LinkedList<Integer> stack = cache.getStackByKey(KEY_CACHE);
        Assert.assertEquals(THREAD_NUMBERS * OPERATIONS_PER_THREAD, stack.size());
        Assert.assertEquals((long) THREAD_NUMBERS, new HashSet<>(stack).size());
    }

}
