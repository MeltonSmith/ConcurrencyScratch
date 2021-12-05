package lock;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Simple Reentrant lock
 * Created by: Ian_Rakhmatullin
 * Date: 05.12.2021
 */
public class ConcurrentCache {
    ReentrantLock lock = new ReentrantLock();
    private final HashMap<String, Integer> cache = new HashMap<>();

    public void put(String key, Integer value){
        cache.put(key, value);
    }

    /**
     * In this case, the thread calling tryLock(), will wait for one second and will give up waiting if the lock isn't available.
     */
    public void performTryLockAndPut(String key, Integer value) throws InterruptedException {
        boolean isLockAcquired = lock.tryLock(1, TimeUnit.SECONDS);

        if(isLockAcquired) {
            try {
                put(key, value);
            } finally {
                lock.unlock();
            }
        }
        //...
    }
}
