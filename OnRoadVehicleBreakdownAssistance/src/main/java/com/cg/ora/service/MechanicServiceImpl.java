package com.cg.ora.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.ora.model.Feedback;
import com.cg.ora.model.Mechanic;
import com.cg.ora.repository.FeedbackRepository;
import com.cg.ora.repository.MechanicRepository;
import com.cg.ora.repository.ServiceRepository;


/**
 * The MechanicServiceImpl class implements a service interface that simply 
 * consist of repositories for feedback,mechanic and service.
 * It contains business logic for adding mechanics and 
 * displaying details of available requests and feedback given by client.
 * @author ashwini rushikesh
 * @since 2020-12-30
 */
@Service
@Transactional
public class MechanicServiceImpl implements MechanicService {
	@Autowired
	MechanicRepository mechanicRepo;     //created MechanichRepository Objects
	@Autowired
	ServiceRepository serviceRepo;
	@Autowired
	FeedbackRepository feedbackRepo;
	
	Logger logger= LoggerFactory.getLogger(MechanicServiceImpl.class);
	
	/**
	 * This is a method used to add mechanic
	 * @param Mechanic object. It contains entire details about mechanic
	 * @return Mechanic object
	 */
	@Override
	public Mechanic addMechanic(Mechanic mechanic)
	{
		logger.info("Adding Mechanic");
		return mechanicRepo.save(mechanic);
	}
	
	/**
	 * This is a method used to send service details
	 * @param mechanic id. This parameter helps to find data according to specific mechanic 
	 * @return List of Service
	 */
	@Override
	public List<com.cg.ora.model.Service> viewRequest(int mechanicId) {
		logger.info("Viewing Request");
		List<com.cg.ora.model.Service> list=serviceRepo.findByMechanicId(mechanicId);
		return list;
	}
	
	/**
	 * This is a method used to return feedback details
	 * @param mechanic id. This parameter helps to find feedback data according to specific mechanic 
	 * @return List of Feedback
	 */
	@Override
	public List<Feedback> viewFeedback(int mecId) {
		logger.info("Viewing feedback by mechanic id");
		 Mechanic m=mechanicRepo.getOne(mecId);
		 List<Feedback> list=feedbackRepo.findByMechanic(m);
		return list;
	}
	
	  
	/**
    * This is a method used to send mechanic login credentials 
    * @param email to check if given parameter exists
    * @param password to check if for given email the password is valid 
    * @return boolean
    */
	@Override
	public boolean loginMechanic(String email, String password) {
		Mechanic mechanic=mechanicRepo.findByMechanicEmailId(email);
		if(email.equals(mechanic.getMechanicEmailId()) && password.equals(mechanic.getMechanicPassword())) {
			return true;
		}
		else 
		{
		return false;
		}
	}

	/**
	 * This method is used to get mechanic with email id
	 * @param email to check if mechanic exists with the given parameter
	 * @return boolean
	 */
	
	@Override
	public boolean getMechanic(String email) {
		if(mechanicRepo.findByMechanicEmailId(email) !=null) {
			return true;
		}
		else {
		return false;
		}
	}

}
