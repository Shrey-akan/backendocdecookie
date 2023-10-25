package com.demo.oragejobsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.oragejobsite.dao.ConatctDao;
import com.demo.oragejobsite.entity.Contact;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class ContactController {
	@Autowired
	private ConatctDao cd;
	
	
	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/insertcontact")
	public ResponseEntity<Boolean> insertcontact(@RequestBody Contact contact) {
	    try {
	        Contact savedContact = cd.save(contact);
	        // If saving is successful, return true
	        return ResponseEntity.status(HttpStatus.CREATED).body(true);
	    } catch (DataAccessException e) {
	        // Handle database-related exceptions (e.g., constraint violations)
	        e.printStackTrace();
	        // If an error occurs, return false
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
	    } catch (Exception e) {
	        // Handle any other exceptions that may occur
	        e.printStackTrace();
	        // If an error occurs, return false
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
	    }
	}

	
	
	@CrossOrigin(origins="http://localhost:4200")
	@GetMapping("/fetchcontact")
	public ResponseEntity<List<Contact>> fetchcontact() {
	    try {
	        List<Contact> contacts = cd.findAll();
	        return ResponseEntity.ok(contacts);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

}
