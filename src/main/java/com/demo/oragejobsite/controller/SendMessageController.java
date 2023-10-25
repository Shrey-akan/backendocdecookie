package com.demo.oragejobsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.oragejobsite.dao.SendMessageDao;
import com.demo.oragejobsite.entity.SendMessage;
import com.demo.oragejobsite.service.MessageService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class SendMessageController {
	@Autowired
	private SendMessageDao sendmessage;
	
	
	
	 private final MessageService messageService;

    @Autowired
    public SendMessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @CrossOrigin(origins="http://localhost:4200")
	    @PostMapping("/send")
	    public ResponseEntity<SendMessage> sendMessage(@RequestBody SendMessage message) {
	        // Call the service to save the message to the database
	    	SendMessage savedMessage = messageService.saveMessage(message);
	
	        // Return a response with the saved message and a status code
	        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
	    }
	    
    @CrossOrigin(origins="http://localhost:4200")
	    @GetMapping("/fetchMessages")
	    public ResponseEntity<List<SendMessage>> fetchMessages() {
	        List<SendMessage> messages = messageService.getAllMessages();
	        return new ResponseEntity<>(messages, HttpStatus.OK);
	    }

	    
	    
	
}
