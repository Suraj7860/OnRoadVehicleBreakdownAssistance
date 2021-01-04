package com.cg.ora.service;

import java.util.List;

import com.cg.ora.model.Feedback;
import com.cg.ora.model.Mechanic;
import com.cg.ora.model.Service;
import com.cg.ora.model.User;
//This is an interface used for providing method declaration of UserServiceImpl class
public interface UserService {

	public String giveFeedback(Feedback feedback,int mechanicId,int userId);

	public User userRegistration(User user);

	public User updateUserById(User user);

	public String addRequest(Service service);
	
	public List<Mechanic> searchMechanicByLocation(String location);
	
	public Service checkServiceExist(int userId);

	public User getUserById(int id);

	public boolean loginUser(String email, String password);

	public boolean getUser(String email);

}
