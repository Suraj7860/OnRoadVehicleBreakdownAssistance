package com.cg.ora.controller;

import java.util.List;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.ora.exception.FeedbackNotFoundException;
import com.cg.ora.exception.MechanicNotFoundException;
import com.cg.ora.exception.RequestNotFoundException;
import com.cg.ora.model.Feedback;
import com.cg.ora.model.Mechanic;
import com.cg.ora.model.Service;
import com.cg.ora.service.MechanicService;

/**
 * This class implements a controller that simply allows mechanic to register ,
 * view feedback according to mechanic id and view request from service
 * interface
 * @author ashwini rushikesh
 * @since 2020-12-30
 */
@RestController
@RequestMapping("/mechanic")
public class MechanicController {
	@Autowired
	MechanicService mechanicService;
	
	Logger logger= LoggerFactory.getLogger(MechanicController.class);
	

	/**
	 * This is a method used to login with mechanic credentials 
	 * @param email to check if given parameter exists
	 * @param password to check if for given email the password is valid 
	 * @return ResponseEntity of type String  
	 * @throws MechanicNotFoundException
	 */
	
	@GetMapping("/mechanicLogin")
	public ResponseEntity<String> mechanicLogin(@Valid @RequestParam("email") String email,
			@RequestParam("password") String password) throws MechanicNotFoundException{
		if(mechanicService.getMechanic(email)) {
			
		logger.info("Mechanic Login");
		boolean login = mechanicService.loginMechanic(email, password);
		if (login) {
			logger.info("Mechanic Login Successful");
			return new ResponseEntity<String>("Login Successful", HttpStatus.OK);
		} else {
			logger.info("Mechanic Login Failed");
			return new ResponseEntity<String>("Login Failed", HttpStatus.NOT_FOUND);
		}
		}
		else {
			logger.error("Mechanic does not exist");
			throw new MechanicNotFoundException("Mechanic not found");
		}
	}	
   
	/**
	 * This is a method used to register the mechanic
	 * @param Mechanic object . This parameter will contain the entire data about the new mechanic to be added
	 * @return ResponseEntity of type String
	 */
	
	@PostMapping("/mechanicRegister")
	public ResponseEntity<String> addMechanic(@Valid @RequestBody Mechanic mechanic) {
		logger.info("Adding Mechanic");
		Mechanic addMechanic = mechanicService.addMechanic(mechanic);
		if (addMechanic == null) {
			
			return new ResponseEntity<String>("Mechanic not registered", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Mechanic Registered Sucessfully", HttpStatus.OK);
	}
    
	
	/**
	 * This is a method to display the list of feedbacks taken from users according to mechanic id
	 * @param mechanic id from URL. This parameter will help to view feedback according to specific mechanic id
	 * @return List of Feedback
	 * @throws FeedbackNotFoundException
	 */
	@GetMapping("/viewFeedback/{mechanicId}")
	public ResponseEntity<List<Feedback>> viewFeedback(@Valid @PathVariable("mechanicId") int mechanicId) throws FeedbackNotFoundException {
		logger.info("Viewing feedback according to mechanic id ");
		List<Feedback> viewFeedback = mechanicService.viewFeedback(mechanicId);
		if (viewFeedback.isEmpty()) {
			logger.error("Feedback does not exist for the specific mechanic");
			throw new FeedbackNotFoundException("Feedback not found for the given mechanic id");
		}
		return new ResponseEntity<List<Feedback>>(viewFeedback, HttpStatus.OK);
	}
    
	/**
	 * This is a method to display the list of request from users to the specific mechanic 
	 * @param mechanic id from URL. This parameter will help us to view request sent to a specific mechanic
	 * @return List of Request
	 * @throws RequestNotFoundException
	 */
	
	@GetMapping("/viewRequest/{mechanicId}")

	public ResponseEntity<List<Service>> viewRequest(@PathVariable("mechanicId") int mechanicId) throws RequestNotFoundException{
		logger.info("Viewing Request according to mechanic id");
		List<Service> viewRequest = mechanicService.viewRequest(mechanicId);
		if (viewRequest.isEmpty()) {
			logger.error("Request not found for specific mechanic");
			throw new RequestNotFoundException("Request not found with id:" + mechanicId);
		}
		return new ResponseEntity<List<Service>>(viewRequest, HttpStatus.OK);
	}

}
