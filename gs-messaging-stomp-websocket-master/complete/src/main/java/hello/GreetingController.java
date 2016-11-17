package hello;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {


    @MessageMapping("/hello")//NOTE: this means that if a message is sent to the hello destination, this method is called:
    //NOTE: the above also means that something sent to destination /app/hello over the web socket get sents here, see:
    // 1. where something is sent to /app/hello in app.js ( sendGreeting(..) and
    // 2. note about how this gets rigged up in WebSocketConfig.java
    @SendTo("/topic/greetings")//Means that the returned value is broadcast to the greetings topic and will
    //be gotten by all subscribers.
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }

}
