package com.cg.ora.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.ora.model.User;

//This interface extends JpaRepository having parameters of User class and unique key of type integer
@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

	public User findByUserEmailId(String email);
}