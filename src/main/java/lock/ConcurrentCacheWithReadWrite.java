package lock;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by: Ian_Rakhmatullin
 * Date: 05.12.2021
 */
public class ConcurrentCacheWithReadWrite {
    ReadWriteLock lock = new ReentrantReadWriteLock();
    Lock writeLock = lock.writeLock();
    Lock readLock = lock.readLock();
    private final HashMap<String, Integer> cache = new HashMap<>();


    public void put(String key, Integer value) {
        try {
            writeLock.lock();
            cache.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    public void deleteByKey(String key, Integer value) {
        try {
            writeLock.lock();
            cache.remove(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    public Integer getValueByKey(String key){
        try{
            readLock.lock();
            return cache.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public boolean containsKey(String key){
        try{
            readLock.lock();
            return cache.containsKey(key);
        } finally {
            readLock.unlock();
        }
    }
}
