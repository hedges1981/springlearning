package utils;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by rowland-hall on 06/06/16
 */
public class PerObjectThreadPoolExecutorImpl
{
    private Map<Object,ThreadPoolExecutor> threadPoolExecutors = new ConcurrentHashMap<Object,ThreadPoolExecutor>(  );
    /**
     * This governs how many threads can be running simultaneously for a given object
     */
    private int threadPoolSize;
    /**
     * How may runnables can be queued up waiting for a thread. Due to the use of an ArrayBlockingQueue,
     * an exception will be thrown if the queue becomes full.
     */
    private int queueSize;

    private int maxNumConcurrentThreads;

    private final AtomicInteger numThreadsCurrentlyRunning = new AtomicInteger(0);

    public void execute( final Object obj, final Runnable runnable )
    {
        if(  numThreadsCurrentlyRunning.incrementAndGet() > maxNumConcurrentThreads )
        {
            numThreadsCurrentlyRunning.decrementAndGet();
            throw new RejectedExecutionException( "Task rejected, as would increase the number of concurrent threads beyond the limit of:"+maxNumConcurrentThreads );
        }
        else
        {
            Runnable thingToActuallyRun = new Runnable()
            {
                @Override
                public void run()
                {
                    runnable.run();
                    numThreadsCurrentlyRunning.decrementAndGet();
                }

            };
            getThreadPoolExecutorForObject( obj ).execute( thingToActuallyRun );
        }

    }

    ThreadPoolExecutor getThreadPoolExecutorForObject( Object obj )
    {
        if( threadPoolExecutors.get( obj ) == null )
        {
            synchronized ( this )
            {
                if( threadPoolExecutors.get( obj ) == null )
                {
                    ArrayBlockingQueue<Runnable> arrayBlockingQueue = new ArrayBlockingQueue<Runnable>( queueSize );
                    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor( threadPoolSize, threadPoolSize, Long.MAX_VALUE, TimeUnit.MILLISECONDS, arrayBlockingQueue );
                    threadPoolExecutors.put( obj, threadPoolExecutor );
                }
            }
        }
        return threadPoolExecutors.get( obj );
    }

    public void setThreadPoolSize( int threadPoolSize )
    {
        this.threadPoolSize = threadPoolSize;
    }

    public void setQueueSize( int queueSize )
    {
        this.queueSize = queueSize;
    }

    public void setMaxNumberOfConcurrentThreads( int max )
    {
        this.maxNumConcurrentThreads = max;
    }
}
