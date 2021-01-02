package com.cg.ora.service;

import java.util.List;
import com.cg.ora.model.Feedback;
import com.cg.ora.model.Mechanic;
import com.cg.ora.model.User;

public interface AdminService {
	public List<Feedback> viewFeedback();
	public List<User> viewUserDetails();
	public List<Mechanic> viewMechanicDetails();
	public String allowOrBlockMechanic(int mechanicId);
}
