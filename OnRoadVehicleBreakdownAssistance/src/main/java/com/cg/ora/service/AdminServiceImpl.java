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

/**
 * This class implements a service interface that simply 
 * consist of repositories for feedback,mechanic and user and contains business logic
 * to display details of feedback,mechanic and user.
 * and provides the mechanism to allow or block mechanic using id.
 * @author prapti suraj
 * @since 2020-12-30
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {
	@Autowired
	FeedbackRepository feedbackRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	MechanicRepository mechanicRepository;
	Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
   
	/**
	 * This is a method used to return feedback details
	 * @return List of Feedback
	 */
	
	@Override
	public List<Feedback> viewFeedback() {
		logger.info("Viewing Feedback");
		return feedbackRepository.findAll();
	}
    
	/**
	 * This is a method used to return user details
	 * @return List of User
	 */
	@Override
	public List<User> viewUserDetails() {
		logger.info("Viewing User Details");
		return userRepository.findAll();
	}
    
	/**
	 * This is a method used to return mechanic details
	 * @return List of Mechanic
	 */
	@Override
	public List<Mechanic> viewMechanicDetails() {
		logger.info("Viewing Mechanic Details");
		return mechanicRepository.findAll();
	}
    
	/**
	 * This is a method used to allow and block mechanic according to ratings
	 * @param mechanic id to specify a mechanic according to id
	 * @return String
	 */
	@Override
	public String allowOrBlockMechanic(int mechanicId) {
		if (mechanicRepository.existsById(mechanicId)) {
			logger.info("Allowing and blocking mechanic");
			Double sumOfRatings = feedbackRepository.sumOfRatings(mechanicId);
			if (sumOfRatings == null) {
				return "Rating doesnt exist for given mechanic id";
			}

			Long countOfRatings = feedbackRepository.countOfRatings(mechanicId);

			double avgRating = sumOfRatings / countOfRatings;

			if (avgRating >= 3) {
				return "Ratings are sufficient. Mechanic is eligible to stay.";
			}

			mechanicRepository.deleteByMechanicId(mechanicId);
			return "Ratings not sufficient. Mechanic blocked.";
		} else {
			return "Mechanic id does not exist";
		}

	}
     
	/**
	 * This is a method used to send admin login credentials 
	 * @param username to check if given parameter exists
	 * @param password to check if for given username the password is valid 
	 * @return boolean
	 */
	
	@Override
	public boolean loginAdmin(String username, String password) {
		if(username.equals("Admin") && password.equals("Admin123")) {
			return true;
		}
		else {
		return false;
		}
	}

}
