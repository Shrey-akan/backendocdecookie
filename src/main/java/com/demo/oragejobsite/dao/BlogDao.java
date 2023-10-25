package com.demo.oragejobsite.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.oragejobsite.entity.Blogs;
@Repository
public interface BlogDao extends JpaRepository<Blogs, Integer>{

}
