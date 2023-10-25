package com.demo.oragejobsite.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.oragejobsite.entity.DirectConntact;

@Repository
public interface Contactfrontdao extends JpaRepository<DirectConntact, Integer>{

}
