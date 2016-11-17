package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by rhall on 17/11/2016.
 */
@Service
public class WebSocketPushingService implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private SimpMessagingTemplate template;

    public void sendMessageDownTopic(String thingToSend, @DestinationVariable int id) {

        this.template.convertAndSend("/topic/testTopic/" + id, thingToSend);

    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                int x =0;
                while( true ){

                    try {
                        sendMessageDownTopic( "hello topic 1!, this is your "+x+" th message", 1);
                        Thread.sleep(3000);
                        sendMessageDownTopic( "hello topic 2!, this is your "+x+" th message", 2);
                        x ++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        new Thread( runnable ).start();
    }
}
