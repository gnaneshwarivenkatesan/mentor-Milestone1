package com.example.mentorOnDemand.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.mentorOnDemand.model.Mentor;
import com.example.mentorOnDemand.model.User;

public interface UserDao extends JpaRepository<User, Integer> {
	  
	List<User> findByemail(String name);
	
	@Query("select c.name,c.phoneNumber,c.linkedIn,c.technology from Mentor c where c.technology LIKE %:letter%")
	public List<Mentor> findBytechnology(@Param(value="letter") String letter);
	
}
