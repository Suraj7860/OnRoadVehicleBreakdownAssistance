package com.cg.ora.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cg.ora.model.Feedback;
import com.cg.ora.model.Mechanic;
import com.cg.ora.model.User;
import com.cg.ora.repository.FeedbackRepository;
import com.cg.ora.repository.MechanicRepository;
import com.cg.ora.repository.ServiceRepository;
import com.cg.ora.repository.UserRepository;

/**
 * The UserServiceImpl class implements a service interface that simply 
 * consist of repositories for feedback,mechanic and user.
 * It contains business logic to give feedback,register and update user by id.
 * It also allows user to search mechanic according to location and 
 * check whether required service is available or not
 * @author arfia saifyn
 * @since 2020-12-30
 */
@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	FeedbackRepository feedbackRepository;

	@Autowired
	MechanicRepository mechanicRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ServiceRepository serviceRepository;
	
	Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    
	/**
	 * This is a method used to add feedback
	 * @param mechanic id to a specific mechanic to which feedback is to be given
	 * @param user id to give feedback to mechanic
	 * @param feedback object. It contains the feedback data given by user 
	 * @return String
	 */
	@Override
	public String giveFeedback(Feedback feedback,int mechanicId,int userId) {
		logger.info("Giving Feedback");
		Mechanic mechanic=mechanicRepository.getOne(mechanicId);
		com.cg.ora.model.Service service= serviceRepository.findByUserIdAndMechanicId(userId,mechanicId);
		if(service== null)
		{
			return "Service for given user and mechanic doesnt exist";
		}
	
		Feedback feed= feedbackRepository.findByUserIdAndMechanic(userId,mechanic);
		if(feed!=null) {
			return "Feedback Already Exists"; 
		}
		
		Feedback feedback1=new Feedback();
		feedback1.setUserId(userId);
		feedback1.setFeedback(feedback.getFeedback());
		feedback1.setRatings(feedback.getRatings());
		feedback1.setMechanic(mechanic);
		feedback1.setService(service);
		feedbackRepository.save(feedback1);
		return "Feedback Added";
		
	}
    
	/**
	 * This is a method used to add user
	 * @param User object. It contains all the User data to be added 
	 * @return User object
	 */
	@Override
	public User userRegistration(User user) {
		logger.info("User Registration");
		User userAdd = userRepository.save(user);
		return userAdd;
	}
    
	/**
	 * This is a method used to update user 
	 * @param User object. It contains all user data to be updated 
	 * @return User object
	 */
	@Override
	public User updateUserById(User user) {
		logger.info("Updating Users by id");
		userRepository.saveAndFlush(user);	
		return user;
	}
    
	/**
	 * This is a method used to add request to a specific mechanic
	 * @param Service object. It contains all request data sent by user 
	 * @return String
	 */
	@Override
	public String addRequest(com.cg.ora.model.Service service) {
		logger.info("Adding request");
        boolean userinfo=userRepository.existsById(service.getUserId());
        if(userinfo==false)
        {
        	return "User does not exists. Please register first";
        }
        
        boolean mechanicinfo=mechanicRepository.existsById(service.getMechanicId());
        if(mechanicinfo==false)
        {
        	return "Mechanic does not exists. Please register first";
        }
        
        serviceRepository.save(service);
        return "Service requested successfully";
	}
    
	/**
	 * This is a method used to search mechanic by location
	 * @param location the user sends his location so as to get the list of nearby mechanics
	 * @return Mechanic object
	 */
	@Override
	public List<Mechanic> searchMechanicByLocation(String location) {
		logger.info("Searching mechanic through location");
		return mechanicRepository.getMechanicByLocationIgnoreCase(location);
	}
    
	/**
	 * This is a method used to check if service exist by specific user using userId
	 * @param userId to check if a service request is sent by specified user
	 * @return Service object
	 */
	@Override
	public com.cg.ora.model.Service checkServiceExist(int userId) {
		logger.info("Checking if service exist");
		return serviceRepository.getByUserId(userId);
		
	}
	
	/**
	 * This is a method used to check if user exist by specific userId
	 * @param userId to check with user exists with given id
	 * @return User object
	 */
	@Override
	public User getUserById(int id) {
		logger.info("getUserById()");
		if (userRepository.findById(id).isPresent()) {
			return userRepository.getOne(id);
		} else {
			return null;
		}
	}
    
	  
	/**
    * This is a method used to send user login credentials 
    * @param email to check if given parameter exists
	* @param password to check if for given email the password is valid 
	* @return boolean
	*/
	@Override
	public boolean loginUser(String email, String password) {
		User user=userRepository.findByUserEmailId(email);
		if(email.equals(user.getUserEmailId()) && password.equals(user.getUserPassword())) {
			return true;
		}
		else 
		{
		return false;
		}
	}
    
	/**
	 * This method is used to get user with email id
	 * @param email to check if user exists with the given parameter
	 * @return boolean
	 */
	
	@Override
	public boolean getUser(String email) {
		if(userRepository.findByUserEmailId(email) !=null) {
			return true;
		}
		else {
		return false;
		}
	}

}
