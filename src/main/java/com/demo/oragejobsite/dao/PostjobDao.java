package com.demo.oragejobsite.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.oragejobsite.entity.PostJob;


@Repository
public interface PostjobDao extends JpaRepository<PostJob, String>{
	
}

