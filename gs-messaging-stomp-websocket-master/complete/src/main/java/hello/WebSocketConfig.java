package hello;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        //NOTE: this builds a simple in-momory broker... will broker messages to cleints whenever a message is sent
        //to a destination prefixed with /topic
        config.enableSimpleBroker("/topic");

        //NOTE: this means that the /app prefix is for all messages that are bound for controller methods annotated with
        //@MessageMapping...... e.g. see GreetingController.greeting(HelloMessage message), this method is mapped to the
        //destination /app/hello
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //this defines the actual web-socket, see app.js for where it is referenced:
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }

}