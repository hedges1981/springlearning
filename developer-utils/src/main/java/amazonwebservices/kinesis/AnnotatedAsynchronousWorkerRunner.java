package amazonwebservices.kinesis;

import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import org.springframework.scheduling.annotation.Async;

/**
 * Note the use of @Async, will run the worker asynchronously in a Spring context.
 *
 * Created by rowland-hall on 10/03/15
 */
public class AnnotatedAsynchronousWorkerRunner implements WorkerRunner
{
   // private static final Logger LOGGER = Logger.getLogger( AnnotatedAsynchronousWorkerRunner.class );

    @Async
    public void runWorker( Worker worker )
    {
        Thread.currentThread().setName( "Thread for kinesis stream worker:"+worker );
      //  LOGGER.info("Setting worker: "+worker +" off to run in thread named: " + Thread.currentThread().getName() );
        worker.run();
    }
}
