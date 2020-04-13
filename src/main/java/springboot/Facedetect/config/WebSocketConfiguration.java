package springboot.Facedetect.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

@Configuration  
@EnableWebSocketMessageBroker 
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {  
  
	private Logger log = LoggerFactory.getLogger(this.getClass());
	 @Override
	    public void configureMessageBroker(MessageBrokerRegistry config) {
	        config.enableSimpleBroker("/topic","/user");
	        config.setApplicationDestinationPrefixes("/app");
	        //点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
	        //registry.setUserDestinationPrefix("/user/");
	 }

	    @Override
	    public void registerStompEndpoints(StompEndpointRegistry registry) {
	        registry.addEndpoint("/stompwebsocket").withSockJS();
	    }
	    
	    @Override
		public void configureWebSocketTransport(final WebSocketTransportRegistration registration) {
			registration.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
				@Override
				public WebSocketHandler decorate(final WebSocketHandler handler) {
					return new WebSocketHandlerDecorator(handler) {
						@Override
						public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
							String username = session.getPrincipal().getName();
							log.info("online: " + username);
							super.afterConnectionEstablished(session);
						}

						@Override
						public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
								throws Exception {
							String username = session.getPrincipal().getName();
							log.info("offline: " + username);
							super.afterConnectionClosed(session, closeStatus);
						}
					};
				}
			});
		}
  
}  