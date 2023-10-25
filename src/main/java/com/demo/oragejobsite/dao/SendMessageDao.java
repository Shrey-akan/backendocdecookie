package com.demo.oragejobsite.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.oragejobsite.entity.SendMessage;


@Repository
public interface SendMessageDao extends JpaRepository<SendMessage, Long>{

}
