package lock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 05.12.2021
 */
public class ConcurrentCacheWithStampedLock {

    private final Map<String, LinkedList<Integer>> cache = new HashMap<>();
    private final StampedLock lock = new StampedLock();

    public void put(String key, Integer value){
        long stamp = lock.writeLock();
        try {
            LinkedList<Integer> integers = cache.computeIfAbsent(key, k -> new LinkedList<>());
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
    public Integer getLastValueByKey(String key) {
        long stamp = lock.readLock();
        try {
            return cache.get(key).peekLast();
        } finally {
            lock.unlockRead(stamp);
        }
    }

    /**
     * peeks the linked list by the key
     */
    public LinkedList<Integer> getStackByKey(String key) {
        long stamp = lock.readLock();
        try {
            return cache.get(key);
        } finally {
            lock.unlockRead(stamp);
        }
    }

    public Integer getOptimistically(String key){
        long stamp = lock.tryOptimisticRead();
        System.out.println("Optimistic stamp is " + stamp);
        Integer valueOptimistic = cache.get(key).peekLast();


        if (!lock.validate(stamp)){
            stamp = lock.readLock();
            System.out.println("new stamp is ..." + stamp);
            try{
                return cache.get(key).peekLast();
            } finally {
                lock.unlock(stamp);
            }
        }

        return valueOptimistic;
    }

    /**
     * peeks the last by the key
     */
    public Integer size() {
        long stamp = lock.tryOptimisticRead();
        int size = cache.size();

        if (!lock.validate(stamp)){
            stamp = lock.readLock();
            try{
                size = cache.size();
            } finally {
                lock.unlock(stamp);
            }
        }

        //no need to unlock optimistic lock
        return size;
    }
}
