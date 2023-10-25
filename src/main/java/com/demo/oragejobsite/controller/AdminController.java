package com.demo.oragejobsite.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.oragejobsite.dao.AdminDao;
import com.demo.oragejobsite.entity.Admin;
import com.demo.oragejobsite.entity.DirectConntact;
import com.demo.oragejobsite.service.AdminService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class AdminController {
	@Autowired
	private AdminDao admindao;
	  private AdminService adminService;

	    @Autowired
	    public AdminController(AdminService adminService) {
	        this.adminService = adminService;
	    }
	    @CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/insertadmin")
	public ResponseEntity<Object> insertadmin(@RequestBody Admin admin) {
	    try {
	        // Generate a random UUID as a string
	        String randomString = UUID.randomUUID().toString().replaceAll("-", "");

	        // Set the randomString as the adminId for the Admin
	        admin.setAdminId(randomString);


	        // Save the admin
	        Admin savedAdmin = admindao.save(admin);

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
	@GetMapping("/fetchadmin")
	public ResponseEntity<List<Admin>> fetchadmin() {
	    try {
	        List<Admin> admindata = admindao.findAll();
	        return ResponseEntity.status(HttpStatus.OK).body(admindata);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
	    @CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/admin/logincheck")
    public ResponseEntity<?> adminLoginCheck(@RequestBody Admin admin, HttpServletResponse response) {
        String adminMail = admin.getAdminMail();
        String adminPass = admin.getAdminPass();

        Admin authenticatedAdmin = adminService.authenticateAdmin(adminMail, adminPass);

        if (authenticatedAdmin != null) {
            // Create and set cookies here
            Cookie adminCookie = new Cookie("admin", adminMail);
            adminCookie.setMaxAge(3600); // Cookie expires in 1 hour (adjust as needed)
            adminCookie.setPath("/"); // Set the path to match your frontend
            response.addCookie(adminCookie);
            System.out.println("Login Sucess");
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid admin credentials");
        }
    }

}
