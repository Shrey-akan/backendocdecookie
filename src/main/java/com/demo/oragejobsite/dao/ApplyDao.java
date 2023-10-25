package com.demo.oragejobsite.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.oragejobsite.entity.ApplyJob;

@Repository
public interface ApplyDao extends JpaRepository<ApplyJob, String>{
	 // Define a custom method to find an ApplyJob entity by juid
    ApplyJob findByJuid(String juid);

}

