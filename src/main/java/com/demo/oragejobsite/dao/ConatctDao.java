package com.demo.oragejobsite.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.oragejobsite.entity.Contact;

@Repository
public interface ConatctDao extends JpaRepository<Contact, Integer>{

}
