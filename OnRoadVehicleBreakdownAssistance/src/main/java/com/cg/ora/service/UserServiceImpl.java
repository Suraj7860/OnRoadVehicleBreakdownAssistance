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

	@Override
	public User userRegistration(User user) {
		logger.info("User Registration");
		User userAdd = userRepository.save(user);
		return userAdd;
	}

	@Override
	public User updateUserById(User user) {
		logger.info("Updating Users by id");
		userRepository.saveAndFlush(user);
		return user;
	}

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

	@Override
	public List<Mechanic> searchMechanicByLocation(String location) {
		logger.info("Searching mechanic through location");
		return mechanicRepository.getMechanicByLocationIgnoreCase(location);
	}

	@Override
	public com.cg.ora.model.Service checkServiceExist(int userId) {
		logger.info("Checking if service exist");
		return serviceRepository.getByUserId(userId);
		
	}

}
