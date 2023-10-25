package com.demo.oragejobsite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.oragejobsite.dao.SendMessageDao;
import com.demo.oragejobsite.entity.SendMessage;

@Service
public class MessageService {

    private final SendMessageDao messageRepository;

    @Autowired
    public MessageService(SendMessageDao messageRepository) {
        this.messageRepository = messageRepository;
    }

    public SendMessage saveMessage(SendMessage message) {
        // Implement logic to save the message to the database
        // You can use the messageRepository to save the message
        return messageRepository.save(message);
    }
    
    
    public List<SendMessage> getAllMessages() {
        // Implement logic to retrieve all messages from your data source (e.g., database)
        List<SendMessage> messages = messageRepository.findAll(); // Assuming you have a method like findAll() in your SendMessageDao
        return messages;
    }
}
