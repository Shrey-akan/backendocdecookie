package com.demo.oragejobsite.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.demo.oragejobsite.dao.BlogDao;
import com.demo.oragejobsite.entity.Blogs;
import com.demo.oragejobsite.service.BlogService;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class BlogController {

	
	 private BlogService blogService;

	    @Autowired
	    public BlogController(BlogService blogService) {
	        this.blogService = blogService;
	    }
   

//	@CrossOrigin(origins = "http://localhost:4200")
//	@GetMapping("/getAllBlogs")
//    public ResponseEntity<List<Blogs>> getAllBlogs() {
//        List<Blogs> blogs = blogRepository.findAll();
//        return ResponseEntity.ok(blogs);
//    }

	    @CrossOrigin(origins="http://localhost:4200")
    @PostMapping("/createBlog")
    public ResponseEntity<?> createBlog(@RequestBody Blogs blog) {
        // Delegate the saving of the blog to the service layer
		Blogs savedBlog = blogService.createBlog(blog);

		return ResponseEntity.ok(savedBlog);
    }
//    @GetMapping("/image/{id}")
//    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
//        Blogs blog = blogRepository.findById(id).orElse(null);
//
//        if (blog != null && blog.getBlogImg() != null) {
//            return ResponseEntity.ok(blog.getBlogImg());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    // Other methods for updating and deleting blogs as needed
}

