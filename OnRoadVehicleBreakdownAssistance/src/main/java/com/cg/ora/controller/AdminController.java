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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.ora.exception.FeedbackNotFoundException;
import com.cg.ora.exception.MechanicNotFoundException;
import com.cg.ora.exception.UserNotFoundException;
import com.cg.ora.model.Feedback;
import com.cg.ora.model.Mechanic;
import com.cg.ora.model.User;
import com.cg.ora.service.AdminService;

/**
 * The AdminController class implements a controller that simply request for
 * feedback,mechanic and user details from service interface and provides the
 * mechanism to allow or block mechanic using id.
 * @author prapti suraj
 * @since 2020-12-30
 */

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	AdminService adminService;
	
	Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	/**
	 * This is a method used to login with admin credentials 
	 * @param username to check if given parameter exists
	 * @param password to check if for given username the password is valid 
	 * @return ResponseEntity of type String  
	 */
	
	@GetMapping("/login")
	public ResponseEntity<String> adminLogin(@Valid @RequestParam("username") String username,
			@RequestParam("password") String password) {
		logger.info("Admin Login");
		boolean login = adminService.loginAdmin(username, password);
		if (login) {
			logger.info("Admin Login Successful");
			return new ResponseEntity<String>("Login Successful", HttpStatus.OK);
		} else {
			logger.info("Admin Login Failed");
			return new ResponseEntity<String>("Login Failed", HttpStatus.NOT_FOUND);
		}
	}	
	
	/**
	 * This is a method to display the list of feedbacks taken from users 
	 * @param none
	 * @return List of Feedback
	 * @throws FeedbackNotFoundException
	 */
	
	@GetMapping("/viewFeedback")
	public ResponseEntity<List<Feedback>> viewFeedback() throws FeedbackNotFoundException {
		logger.info("Viewing Feedback");

		List<Feedback> viewFeedback = adminService.viewFeedback();
		if (viewFeedback.isEmpty()) {
			logger.error("Feedback Not Found");
			throw new FeedbackNotFoundException("FeedBack not found:");
		}
		return new ResponseEntity<List<Feedback>>(viewFeedback, HttpStatus.OK);
	}
    
	/**
	 * This is a method display the list of mechanic
	 * @param none
	 * @return List of Mechanic
	 * @throws MechanicNotFoundException
	 */
	
	@GetMapping("/viewMechanic")
	public ResponseEntity<List<Mechanic>> viewMechanicDetails() throws MechanicNotFoundException {
		logger.info("Viewing mechanics");

		List<Mechanic> viewMechanic = adminService.viewMechanicDetails();
		if (viewMechanic.isEmpty()) {
			logger.error("Mechanic Not Found");
			throw new MechanicNotFoundException("Mechanic does not exist");
		}
		return new ResponseEntity<List<Mechanic>>(viewMechanic, HttpStatus.OK);
	}
    
	/**
	 * This is a method to display the list of users 
	 * @param none
	 * @return List of Users
	 * @throws UserNotFoundException
	 */
	
	@GetMapping("/viewUser")
	public ResponseEntity<List<User>> viewUserDetails() throws UserNotFoundException {

		logger.info("Viewing Users");

		List<User> viewUser = adminService.viewUserDetails();
		if (viewUser.isEmpty()) {
			logger.error("User Not Found");
			throw new UserNotFoundException("User does not exist");
		}
		return new ResponseEntity<List<User>>(viewUser, HttpStatus.OK);
	}
    
	/**
	 * This is a method to allow or block a mechanic according to ratings given by user in feedback
	 * @param mechanic id from URL. This parameter is used to know whether specific mechanic is blocked or not
	 * @return ResponseEntity of type String
	 * @throws MechanicNotFoundException
	 */
	
	@GetMapping("/allowOrBlockMechanic/{mechanicId}")
	public ResponseEntity<String> allowOrBlockMechanic(@PathVariable("mechanicId") int mechanicId)
			throws MechanicNotFoundException {
		logger.info("Allow and Block method started");
		String allowOrBlockMechanic = adminService.allowOrBlockMechanic(mechanicId);
		if (allowOrBlockMechanic == null) {
			logger.error("Mechanic Not Found");
			throw new MechanicNotFoundException("Mechanic does not exist");
		}
		return new ResponseEntity<String>(allowOrBlockMechanic, HttpStatus.OK);
	}

}
