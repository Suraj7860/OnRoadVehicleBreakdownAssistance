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
import com.cg.ora.repository.UserRepository;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{
	@Autowired
	FeedbackRepository feedbackRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MechanicRepository mechanicRepository;
	Logger logger= LoggerFactory.getLogger(AdminServiceImpl.class);

	@Override
	public List<Feedback> viewFeedback() {
		logger.info("Viewing Feedback");
		return feedbackRepository.findAll();
    }


	@Override
	public List<User> viewUserDetails() {
		logger.info("Viewing User Details");
		return userRepository.findAll();
	}


	@Override
	public List<Mechanic> viewMechanicDetails() {
		logger.info("Viewing Mechanic Details");
		return mechanicRepository.findAll();
	}


	@Override
	public String allowOrBlockMechanic(int mechanicId) {
		logger.info("Allowing and blocking mechanic");
		Double sumOfRatings = feedbackRepository.sumOfRatings(mechanicId);
		if(sumOfRatings==null) {
			return "Rating doesnt exist for given mechanic id";
		}
		

		Long countOfRatings = feedbackRepository.countOfRatings(mechanicId);
		
	    double avgRating=sumOfRatings/countOfRatings;
		
		if(avgRating>=3)
		{
			return "Ratings are sufficient. Mechanic is eligible to stay.";
		}
	
		mechanicRepository.deleteByMechanicId(mechanicId);
		return "Ratings not sufficient. Mechanic blocked.";
	
}
	

	
	
}
