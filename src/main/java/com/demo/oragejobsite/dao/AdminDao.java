package com.demo.oragejobsite.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.oragejobsite.entity.Admin;

@Repository
public interface AdminDao extends JpaRepository<Admin, String>{

	Admin findByAdminMail(String adminMail);

}