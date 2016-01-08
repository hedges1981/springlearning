package rewards.jms.client;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import rewards.Dining;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.List;

/**
 * A batch processor that sends dining event notifications via JMS.
 */
public class JmsDiningBatchProcessor implements DiningBatchProcessor {

	// DONE-03: Provide a JmsTemplate field.
	//	Add a setter or constructor to allow it to be set via dependency injection.

    private JmsTemplate jmsTemplate;

    public void setJmsTemplate( JmsTemplate jmsTemplate )
    {
        this.jmsTemplate = jmsTemplate;
    }

    public void processBatch(List<Dining> batch) {
		//	DONE-04: Loop through each Dining instance,
		//	sending each one using the JmsTemplate.

        for( final Dining dining: batch )
        {
            jmsTemplate.send( new MessageCreator()
            {
                @Override
                public Message createMessage( Session session ) throws JMSException
                {
                    return session.createObjectMessage( dining );
                }
            } );
        }
	}
}
