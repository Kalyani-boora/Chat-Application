 
package com.chat.Chatting.Application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.Chatting.Application.Entity.ChatDtoMessages;

public interface ChatMessageRepository  extends JpaRepository<ChatDtoMessages, Long>  {

}
