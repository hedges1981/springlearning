

import org.junit.Test;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.Assert.assertEquals;

/**
 * Created by rowland-hall on 24/05/16
 */
public class PerObjectThreadPoolExecutorImplTest
{
    @Test
    public void shouldExecuteTasksAsynchronously()
    {
        //-------------------given-----------------------------------------------
        PerObjectThreadPoolExecutorImpl perObjectThreadPoolExecutor = new PerObjectThreadPoolExecutorImpl();
        //set the thread pool size and queue size so that the queue comes into play, but is not filled up:
        perObjectThreadPoolExecutor.setThreadPoolSize( 2 );
        perObjectThreadPoolExecutor.setQueueSize( 10 );
        perObjectThreadPoolExecutor.setMaxNumberOfConcurrentThreads( 999 ); //set a decent number here, not testing this bit.

        final AtomicInteger atomicInteger = new AtomicInteger( 0 );

        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                atomicInteger.incrementAndGet();
                //sleep for a second, to ensure that the queue is exercised:
                GenUtil.sleepForNMilliseconds( 1000 );
            }
        };

        //--------------------------when-----------------------------------------
        for( int i = 0; i<5; i++ )
        {
            perObjectThreadPoolExecutor.execute( "obj", runnable );
        }

        //sleep for 6 seconds, so they all can do their stuff:
        GenUtil.sleepForNMilliseconds( 6000 );

        //------------------------then-------------------------------
        assertEquals( 5, atomicInteger.get() ); //if it ==5 , means that all the threads have run and done their thing.
    }

    @Test(expected = RejectedExecutionException.class )
    public void shouldThrowExceptionIfQueueFillsUp()
    {
        //-------------------given-----------------------------------------------
        PerObjectThreadPoolExecutorImpl perObjectThreadPoolExecutor = new PerObjectThreadPoolExecutorImpl();
        //set the thread pool size and queue size so that the queue comes into play, but is not filled up:
        perObjectThreadPoolExecutor.setThreadPoolSize( 1 );  //minimum pool size, so that we exercise the queue
        perObjectThreadPoolExecutor.setQueueSize( 3 ); // low queue size, so that we can fill it.

        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                //sleep for a second, to ensure that the queue is exercised:
                GenUtil.sleepForNMilliseconds( 1000 );
            }
        };

        //--------------------------when-----------------------------------------
        for( int i = 0; i<5; i++ )
        {
            perObjectThreadPoolExecutor.execute( "obj", runnable );
        }

        //----------------then-----------------------
        // expected exception thrown
    }

    @Test(expected = RejectedExecutionException.class )
    public void shouldThrowExceptionIfMaxNumberOfConcurrentRunningThreadsIsExceeded()
    {
        //-------------------given-----------------------------------------------
        PerObjectThreadPoolExecutorImpl perObjectThreadPoolExecutor = new PerObjectThreadPoolExecutorImpl();
        //set the thread pool size and queue size so that the queue comes into play, but is not filled up:
        perObjectThreadPoolExecutor.setThreadPoolSize( 1000 );  //any old number here,to make sure we are not testing this bit.
        perObjectThreadPoolExecutor.setQueueSize( 3000 ); // any old number here, again want to make sure we don't test this bit.

        int maxNumberOfConcurrentThreads = 4;
        perObjectThreadPoolExecutor.setMaxNumberOfConcurrentThreads( maxNumberOfConcurrentThreads );

        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                //sleep for some time, to ensure that thread allocation gets used up.
                GenUtil.sleepForNMilliseconds( 10000);
            }
        };

        //--------------------------when-----------------------------------------
        for( int i = 0; i<(maxNumberOfConcurrentThreads+1); i++ )
        {
            perObjectThreadPoolExecutor.execute( "obj", runnable );
        }

        //----------------then-----------------------
        // expected exception thrown
    }

    @Test
    public void shouldAllowConcurrentThreadExecutionUpToMaxLimit()
    {
        //-------------------given-----------------------------------------------
        PerObjectThreadPoolExecutorImpl perObjectThreadPoolExecutor = new PerObjectThreadPoolExecutorImpl();
        //set the thread pool size and queue size so that the queue comes into play, but is not filled up:
        perObjectThreadPoolExecutor.setThreadPoolSize( 1000 );  //any old number here,to make sure we are not testing this bit.
        perObjectThreadPoolExecutor.setQueueSize( 3000 ); // any old number here, again want to make sure we don't test this bit.

        int maxNumberOfConcurrentThreads = 4;
        perObjectThreadPoolExecutor.setMaxNumberOfConcurrentThreads( maxNumberOfConcurrentThreads );

        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                //sleep for some time, to ensure that thread allocation gets used up.
                GenUtil.sleepForNMilliseconds( 10000);
            }
        };

        //--------------------------when-----------------------------------------
        for( int i = 0; i<(maxNumberOfConcurrentThreads); i++ )
        {
            perObjectThreadPoolExecutor.execute( "obj", runnable );
        }

        //----------------then-----------------------
        // no exception thrown
    }



}

