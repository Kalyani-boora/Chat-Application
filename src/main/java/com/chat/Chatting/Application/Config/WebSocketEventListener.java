 package com.chat.Chatting.Application.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.chat.Chatting.Application.Entity.ChatDtoMessages;
import com.chat.Chatting.Application.Repository.MessageType;

@Component
public class WebSocketEventListener {
	 private static final Logger log = LoggerFactory.getLogger(WebSocketEventListener.class);

	private final SimpMessageSendingOperations messageOperation;
	 public WebSocketEventListener(SimpMessageSendingOperations messageOperation) {
	        this.messageOperation = messageOperation;
	    }
	 
@EventListener
public void handleWebsocketDisconnectListener(SessionDisconnectEvent event){
	StompHeaderAccessor headerAccessor  = StompHeaderAccessor.wrap(event.getMessage());
 String username = (String) headerAccessor.getSessionAttributes().get("username");
 if(username != null) {
	 log.info("User disconnected :{}",username);
	//ChatMessage 
	 	
	 ChatDtoMessages chatMessage = ChatDtoMessages.builder()
			 .type(MessageType.LEAVE)
			 .sender(username)
			 .build();
	 messageOperation.convertAndSend("/topic/public",chatMessage );
 }
}
}

