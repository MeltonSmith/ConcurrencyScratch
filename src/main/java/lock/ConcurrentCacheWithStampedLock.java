package lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 05.12.2021
 */
public class ConcurrentCacheWithStampedLock {

    private final Map<String,Integer> map = new HashMap<>();
    private StampedLock lock = new StampedLock();

    public void put(String key, Integer value){
        long stamp = lock.writeLock();
        try {
            map.put(key, value);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public Integer get(String key) {
        long stamp = lock.readLock();
        try {
            return map.get(key);
        } finally {
            lock.unlockRead(stamp);
        }
    }

    public Integer readOptimistically(String key){
        long stamp = lock.tryOptimisticRead();
        System.out.println("Optimistic stamp is " + stamp);
        Integer valueOptimistic = map.get(key);


        if (!lock.validate(stamp)){
            stamp = lock.readLock();
            System.out.println("new stamp is ..." + stamp);
            try{
                return map.get(key);
            } finally {
                lock.unlock(stamp);
            }
        }

        return valueOptimistic;
    }
}
