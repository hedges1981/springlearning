package rewards.internal.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;
import rewards.internal.exception.RewardDataAccessException;


@Aspect
@Component
public class DBExceptionHandlingAspect {
	
	private Logger logger = Logger.getLogger(getClass());

    public DBExceptionHandlingAspect()
    {
        int x =0;
    }


	//	DONE-09 (Optional): Configure this advice method to handle
	//	all exceptions thrown by Repository class methods.
	//	Select the advice type that seems most appropriate.
    //NOTE: how you can get hold of the exception via the throwing = "e" thing.
	@AfterThrowing(value="execution(public * rewards.internal.*.*Repository.*(..))", throwing="e")
	public void implExceptionHandling(RewardDataAccessException e) { 
		logger.info(" Sending an email to Mister Smith : " + e + "\n");
	}
	
	
	//	DONE-10 (Optional): Annotate this class as a Spring-managed bean.
	//	Note that we enabled component scanning in an earlier step.
	//	Save all work, run DBExceptionHandlingAspectTests. It should pass, and 
	//	expected logging output should appear in the console.
	
}
