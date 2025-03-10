package com.chat.Chatting.Application.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.chat.Chatting.Application.Entity.ChatDtoMessages;
import com.chat.Chatting.Application.Repository.ChatMessageRepository;
import com.chat.Chatting.Application.Repository.MessageType;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class ChatController {
	    private final ChatMessageRepository chatMessageRepository;
	    public ChatController(ChatMessageRepository chatMessageRepository) {
	    	this.chatMessageRepository = chatMessageRepository;
	    }

	    @MessageMapping("/chat.sendMessage")
	    @SendTo("/topic/public")
	    public ChatDtoMessages sendMessage(@Payload ChatDtoMessages msg) {
	        // Save to database
	        ChatDtoMessages entity =  ChatDtoMessages.builder()
	                .type(msg.getType())
	                .content(msg.getContent())
	                .sender(msg.getSender())
	                .build();
	        chatMessageRepository.save(entity);

	        return msg;
	    }

	@MessageMapping("/chat.addUser")
	@SendTo("/topic/public")
public ChatDtoMessages addUser(@Payload ChatDtoMessages msg,SimpMessageHeaderAccessor headerAccessor) {
		// Add username in web socket session.
		headerAccessor.getSessionAttributes().put("username", msg.getSender());
		return msg;
	}
	
	@MessageMapping("/chat.leave")
	@SendTo("/topic/public")
	public ChatDtoMessages leaveChat(@Payload ChatDtoMessages msg, SimpMessageHeaderAccessor headerAccessor) {
	    headerAccessor.getSessionAttributes().remove("username"); // Remove user from session

	    return ChatDtoMessages.builder()
	            .type(MessageType.LEAVE)
	            .sender(msg.getSender())
	            .build();
	}
}



 
