package com.chat.Chatting.Application.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.Chatting.Application.Entity.ChatDtoMessages;
import com.chat.Chatting.Application.Repository.ChatMessageRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatHistoryController {
	
    private final ChatMessageRepository chatMessageRepository ;
    public ChatHistoryController(ChatMessageRepository chatMessageRepository) {
    	this.chatMessageRepository = chatMessageRepository;
    }

    @GetMapping("/history")
    public List<ChatDtoMessages> getChatHistory() {
        return chatMessageRepository.findAll();
    }
} 
