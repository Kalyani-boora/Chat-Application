package com.chat.Chatting.Application.Entity;

import com.chat.Chatting.Application.Repository.MessageType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "chat_messages")
public class ChatDtoMessages {
	
	 @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	   
	
	 @Enumerated(EnumType.STRING)
    private MessageType type;
    private String content;
    private String sender;

    // Getters and Setters
    public MessageType getType() { 
    	return type; 
    	}
    public void setType(MessageType type) {
    	this.type = type; 
    	}
    public String getContent() { 
    	return content; 
    	}
    public void setContent(String content) { 
    	this.content = content; 
    	}
    public String getSender() {
    	return sender; 
    	}
    public void setSender(String sender) { 
    	this.sender = sender;
    	}

    // Constructors
    public ChatDtoMessages() {}

    public ChatDtoMessages(MessageType type, String content, String sender) {
        this.type = type;
        this.content = content;
        this.sender = sender;
    }

    // Corrected Builder Class
    public static class Builder {
        private MessageType type;
        private String content;
        private String sender;

        public Builder type(MessageType type) {
            this.type = type;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder sender(String sender) {
            this.sender = sender;
            return this;
        }

        public ChatDtoMessages build() {
            return new ChatDtoMessages(type, content, sender);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
} 
