package lock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.locks.StampedLock;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 05.12.2021
 */
public class ConcurrentCacheWithStampedLock {

    private final Map<String, LinkedList<Integer>> map = new HashMap<>();
    private final StampedLock lock = new StampedLock();

    public void put(String key, Integer value){
        long stamp = lock.writeLock();
        try {
            LinkedList<Integer> integers = map.computeIfAbsent(key, k -> new LinkedList<>());
            if (integers.size() >= 2000){
                integers.removeFirst();
            }
            integers.addLast(value);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    /**
     * peeks the last by the key
     */
    public Integer get(String key) {
        long stamp = lock.readLock();
        try {
            return map.get(key).peekLast();
        } finally {
            lock.unlockRead(stamp);
        }
    }

    public Integer getOptimistically(String key){
        long stamp = lock.tryOptimisticRead();
        System.out.println("Optimistic stamp is " + stamp);
        Integer valueOptimistic = map.get(key).peekLast();


        if (!lock.validate(stamp)){
            stamp = lock.readLock();
            System.out.println("new stamp is ..." + stamp);
            try{
                return map.get(key).peekLast();
            } finally {
                lock.unlock(stamp);
            }
        }

        return valueOptimistic;
    }
}
