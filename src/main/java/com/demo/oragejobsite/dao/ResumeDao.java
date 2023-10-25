package com.demo.oragejobsite.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.oragejobsite.entity.ResumeBuilder;

@Repository
public interface ResumeDao extends JpaRepository<ResumeBuilder, Integer>{

}
