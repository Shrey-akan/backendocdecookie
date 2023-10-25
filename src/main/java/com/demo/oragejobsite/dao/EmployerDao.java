package com.demo.oragejobsite.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.oragejobsite.entity.Employer;
import com.demo.oragejobsite.entity.User;

@Repository
public interface EmployerDao extends JpaRepository<Employer, String>{

	Optional<Employer> findByEmpid(String empid);
	Employer findByEmpmailid(String empmailid);
}
