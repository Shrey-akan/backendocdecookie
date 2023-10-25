package com.demo.oragejobsite.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.oragejobsite.dao.PostjobDao;
import com.demo.oragejobsite.entity.PostJob;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class PostjobController {
	@Autowired
	private PostjobDao pjd;
	
	
	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/jobpostinsert")
	public ResponseEntity<String> jobpostinsert(@RequestBody PostJob pj) {
	    try {
	    	 String randomString = UUID.randomUUID().toString();

		        // Remove hyphens and special symbols
		        randomString = randomString.replaceAll("-", "");

		        // Set the randomString as the Juid for the ApplyJob
		        pj.setJobid(randomString);
	    	
	    	
	        PostJob savedPostJob = pjd.save(pj);
	        // If saving is successful, return a success message
	        return ResponseEntity.status(HttpStatus.CREATED).body("Job post saved successfully");
	    } catch (DataAccessException e) {
	        // Handle database-related exceptions (e.g., constraint violations)
	        e.printStackTrace();
	        // Return an error message
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error occurred: " + e.getMessage());
	    } catch (Exception e) {
	        // Handle any other exceptions that may occur
	        e.printStackTrace();
	        // Return an error message
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request: " + e.getMessage());
	    }
	}

	
	@CrossOrigin(origins="http://localhost:4200")
	@GetMapping("/fetchjobpost")
	public ResponseEntity<List<PostJob>> fetchjobpost() {
	    try {
	        List<PostJob> jobPosts = pjd.findAll();
	        return ResponseEntity.ok(jobPosts);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
	 
	@CrossOrigin(origins="http://localhost:4200")
    @PutMapping("/jobpostupdate/{jobid}")
    public ResponseEntity<Object> jobpostupdate(@PathVariable String jobid, @RequestBody PostJob updatedJob) {
        try {
            // Find the existing job post by jobid
            Optional<PostJob> existingJob = pjd.findById(jobid);

            if (existingJob.isPresent()) {
                PostJob currentJob = existingJob.get();
                
                // Update the fields of the existing job post with the new values
                currentJob.setJobtitle(updatedJob.getJobtitle());
                currentJob.setCompanyforthisjob(updatedJob.getCompanyforthisjob());
                currentJob.setNumberofopening(updatedJob.getNumberofopening());
                currentJob.setLocationjob(updatedJob.getLocationjob());
                currentJob.setJobtype(updatedJob.getJobtype());
                currentJob.setSchedulejob(updatedJob.getSchedulejob());
                currentJob.setPayjob(updatedJob.getPayjob());
                currentJob.setPayjobsup(updatedJob.getPayjobsup());
                currentJob.setDescriptiondata(updatedJob.getDescriptiondata());
       

                // Save the updated job post
                pjd.save(currentJob);

                // If the update is successful, return a success message
                return ResponseEntity.status(HttpStatus.OK).body(currentJob);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
            }
        } catch (DataAccessException e) {
            // Handle database-related exceptions (e.g., constraint violations)
            e.printStackTrace();
            // Return an error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database error occurred: " + e.getMessage());
        } catch (Exception e) {
            // Handle any other exceptions that may occur
            e.printStackTrace();
            // Return an error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request: " + e.getMessage());
        }
    }
	
}

