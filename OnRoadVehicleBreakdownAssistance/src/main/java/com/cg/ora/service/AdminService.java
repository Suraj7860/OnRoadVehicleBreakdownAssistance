package com.cg.ora.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cg.ora.model.Feedback;
import com.cg.ora.model.Mechanic;
import com.cg.ora.model.User;

//This is an interface used for providing method declaration of AdminServiceImpl class
@Service
public interface AdminService {
	public List<Feedback> viewFeedback();
	public List<User> viewUserDetails();
	public List<Mechanic> viewMechanicDetails();
	public String allowOrBlockMechanic(int mechanicId);
	public boolean loginAdmin(String username, String password);
}
