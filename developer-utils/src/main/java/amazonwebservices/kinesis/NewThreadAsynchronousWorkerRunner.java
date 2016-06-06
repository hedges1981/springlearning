package amazonwebservices.kinesis;

import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;

/**
 * Runs a worker asynchronously, by explicitly setting off a new thread.
 *
 * Created by rowland-hall on 12/03/15
 */
public class NewThreadAsynchronousWorkerRunner implements WorkerRunner
{
    @Override
    public void runWorker( Worker worker )
    {
        Thread thread = new Thread( worker );
        thread.setName( "Worker running thread" );
        thread.start();
    }
}
