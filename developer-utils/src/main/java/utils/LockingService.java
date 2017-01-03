package utils;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by rhall on 29/12/2016.
 */
@Service
public class LockingService {

    //TODO: need some kind of size management on this to prevent it busting memory over time.
    private Map<Object, ReentrantLock> locks = new ConcurrentHashMap<>();

    public boolean lockWithTimeout(Object objectToLock, long timeoutSecs) {

        long currentThreadId = Thread.currentThread().getId();

        Lock lock = getLock(objectToLock);
        try {
            boolean success = lock.tryLock(timeoutSecs, TimeUnit.SECONDS);
             return success;

        } catch (InterruptedException e) {
            throw new IllegalStateException( "Exception acquiring lock, this shouldn't have happened, someone has done something very daft in the code", e);
        }
    }

    public boolean lockNoWait(Object objectToLock ){
        return lockWithTimeout( objectToLock, 0 );
    }

    public boolean lockWaitIndefinitely(Object objectToLock) {
        return lockWithTimeout(objectToLock, Long.MAX_VALUE);
    }

    public void unlockObject(Object objectToLock ){
        getLock( objectToLock).unlock();
        }

    public void executeWithLockAndTimeout(Runnable thingToRun, Object thingToLock, long timeoutSecs) throws CouldntGetLockException {

        boolean hasLock = false;
        try {
            hasLock = lockWithTimeout(thingToLock, timeoutSecs);

            if (hasLock) {
                thingToRun.run();
            } else {
                throw new CouldntGetLockException("Couldn't get lock on object: " + thingToLock + " in timeout of:" + timeoutSecs);
            }
        } finally {
            if( hasLock ){
                unlockObject(thingToLock);
            }

        }
    }

    public void executeWithLockNoWait(Runnable thingToRun, Object thingToLock ) throws CouldntGetLockException {
        executeWithLockAndTimeout( thingToRun, thingToLock, 0);
    }

    public boolean isLocked( Object o ){
        return getLock(o).isLocked();
    }

    public class CouldntGetLockException extends Exception {

        public CouldntGetLockException( String message ){
            super( message );
        }

    }

    private ReentrantLock getLock(Object objectToLock) {

        ReentrantLock lock = locks.get(objectToLock);

        //standard double null checking pattern to ensure thread safety with minimum synchronisation bottleneck
        if (lock == null) {
            synchronized (locks) {
                lock = locks.get(objectToLock);
                if (lock == null) {
                    lock = new ReentrantLock();
                    locks.put( objectToLock, lock );
                }
            }
        }

        return lock;
    }
}
