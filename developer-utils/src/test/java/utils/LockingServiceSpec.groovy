package utils

import spock.lang.Specification

/**
 * Created by rhall on 29/12/2016.
 */
class LockingServiceSpec extends Specification {

    long event1 = 1l
    long event2 = 2l

    boolean otherThreadShouldReleaseLock

    def setup(){
        otherThreadShouldReleaseLock = false;
    }

    def "The same thread should be able to get the lock on an event multiple times"() {

        given: "an event locking service for the test"
        LockingService lockingService = new LockingService();

        when: "event1 is locked multiple times in succession by the same thread"
        boolean hasLock1 = lockingService.lockNoWait(event1);
        boolean hasLock2 = lockingService.lockNoWait(event1);
        boolean hasLock3 = lockingService.lockNoWait(event1);

        then: " all attempts to get the lock have succeeded"
        hasLock1 && hasLock2 && hasLock3

        and:" the service reports the event as locked"
        lockingService.isLocked(event1)
    }

    def "If one thread has the lock on an event, then it should stop other threads from getting the lock for that event only, until it is unlocked"() {

        given: " a event locking service for the test"
        LockingService lockingService = new LockingService()
        and: "an event that is locked by another thread"
        lockEventInNewThread( lockingService, event1 )
        //wait a second, to avoid race condition between this thread and the other one:
        Thread.sleep( 1000 )
        when:" this thread tries to lock the event"
        boolean thisThreadHasLock = lockingService.lockNoWait( event1 )
        then:" the attempt should fail"
        !thisThreadHasLock
        when:" this thread tries to lock a different event"
        thisThreadHasLock = lockingService.lockNoWait( event2 )
        then:" the attempt should succeed "
        thisThreadHasLock
        when:" the other thread releases the lock on its event"
        otherThreadShouldReleaseLock = true
        //wait a second, to avoid race condition between this thread and the other one:
        Thread.sleep( 1000 )
        thisThreadHasLock = lockingService.lockNoWait( event1 )
        then:"this thread should now have the lock"
        thisThreadHasLock
    }



    def "Locking with timeout should be successful if another thread releases the lock within the timeout"() {

        given: " a event locking service for the test"
        LockingService lockingService = new LockingService()
        and: " a lock holding time to work with"
        int lockHoldingTimeSecs = 2
        and: "an event that is locked by another thread for the specified time"
        lockEventInNewThreadForSpecifiedTime(lockingService, event1, lockHoldingTimeSecs)
        when: " this thread tries to get the lock on the event with a timeout that is > the time the other thread is holding the lock for"
        boolean thisThreadHasLock = lockingService.lockWithTimeout(event1, lockHoldingTimeSecs + 5)
        then: "the attempt should succeed"
        thisThreadHasLock
    }

    def "Locking with timeout should fail if timeout is exceeded"(){

        given: " a event locking service for the test"
        LockingService lockingService = new LockingService()
        and: "an event that is locked by another thread"
        lockEventInNewThread( lockingService, event1 )
        //wait a second, to avoid race condition between this thread and the other one:
        Thread.sleep( 1000 )
        when:"this thread tries to lock the event with a time out"
        int timeOutInSeconds = 2
        long timeBeforeAttempt = new Date().getTime()
        boolean thisThreadHasLock = lockingService.lockWithTimeout( event1, timeOutInSeconds )
        long timeAfterAttempt = new Date().getTime()
        then:"the attempt should fail"
        !thisThreadHasLock
        and:" it should have taken at least as long as the time out specified for the failure to register"
        (timeAfterAttempt - timeBeforeAttempt) / 1000 >= timeOutInSeconds
    }


    def "Should execute with lock correctly when the subject object is locked by another thread"(){
        
        given:" Something to execute which can be tested"
        boolean thingExecuted = false
        Runnable runnable = new Runnable() {
            @Override
            void run() {
                thingExecuted = true
            }
        }
        and:"A locking service for this test"
        LockingService lockingService = new LockingService()

        when:" the thing is executed "
        lockingService.executeWithLockNoWait( runnable, event1)
        then:"the thing should have been executed"
        thingExecuted

        when:" the thing is executed with no wait but the event is locked"
        lockEventInNewThread( lockingService, event1 )
        thingExecuted = false
        //pause for a second to avoid a race condition between the two threads
        Thread.sleep(1000)
        assert(lockingService.isLocked(event1))
        lockingService.executeWithLockNoWait( runnable , event1)
        then:" an exception should get thrown"
        thrown( LockingService.CouldntGetLockException )
        and:" the thing should not have been executed"
        !thingExecuted


        when:" the thing is executed with a wait, which is exceeded "
        thingExecuted = false
        int timeOutInSeconds = 2
        long timeBeforeAttempt = new Date().getTime()
        assert(lockingService.isLocked(event1))
        lockingService.executeWithLockAndTimeout( runnable, event1, timeOutInSeconds )
        then:" exception is thrown"
        thrown( LockingService.CouldntGetLockException )
        long timeAfterAttempt = new Date().getTime()
        and:" the thing should not have been executed"
        !thingExecuted
        and:" it should have taken at least as long as the time out specified for the failure to register"
        (timeAfterAttempt - timeBeforeAttempt) / 1000 >= timeOutInSeconds

        when:" thing thing is exceuted with a lock on a different object to the one that is currently locked"
        thingExecuted = false
        lockingService.executeWithLockNoWait( runnable, event2)
        then:"it should successfully execute"
        thingExecuted
    }

    void " should execute with lock correctly when another thread holds the lock but releases it before timeout is exceeded"(){

        given:" Something to execute which can be tested"
        boolean thingExecuted = false
        Runnable runnable = new Runnable() {
            @Override
            void run() {
                thingExecuted = true
            }
        }
        and:"A locking service for this test"
        LockingService lockingService = new LockingService()
        and:" a test time for the test"
        int testLockTime = 2
        and:"the event is locked for a given time"
        lockEventInNewThreadForSpecifiedTime( lockingService, event1, testLockTime )
        when:" the thing is executed with a timeout that exceeds the other threads lock time"
        lockingService.executeWithLockAndTimeout( runnable, event1, testLockTime+2 )
        then: " the thing should have been successfully executed"
        thingExecuted
    }


    void lockEventInNewThread(LockingService lockingService, long eventId) {

        Runnable runnable = new Runnable() {
            @Override
            void run() {
                lockingService.lockNoWait(eventId)

                while( true ){
                    if(otherThreadShouldReleaseLock){
                        lockingService.unlockObject( eventId)
                        println("Other thread has unlocked event")
                        break
                    }
                }
            }
        }
        new Thread(runnable).start()
    }

    void lockEventInNewThreadForSpecifiedTime(LockingService lockingService, long eventId, int lockTimeSeconds) {

        Runnable runnable = new Runnable() {
            @Override
            void run() {
                long startTime = new Date().getTime()
                lockingService.lockNoWait(eventId)

                while( true ){
                   long now = new Date().getTime()
                    if( (now - startTime)/1000 >= lockTimeSeconds ){
                        lockingService.unlockObject( eventId )
                        println("Other thread has unlocked event")
                        break
                    }
                }
            }
        }
        new Thread(runnable).start()
    }


}
