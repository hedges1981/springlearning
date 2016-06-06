/**
 * Created by rowland-hall on 06/06/16
 */
public interface PerObjectThreadPoolExecutor
{
    void execute( Object obj, Runnable runnable );
}
