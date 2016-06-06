package amazonwebservices.kinesis;

import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;

/**
 * Created by rowland-hall on 10/03/15
 */
public interface WorkerRunner
{
    void runWorker( Worker worker );
}
