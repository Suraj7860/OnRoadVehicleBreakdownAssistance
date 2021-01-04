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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.ora.exception.MechanicNotFoundException;
import com.cg.ora.exception.UserNotFoundException;
import com.cg.ora.model.Feedback;
import com.cg.ora.model.Mechanic;
import com.cg.ora.model.Service;
import com.cg.ora.model.User;
import com.cg.ora.service.UserService;

/**
 * This class implements a controller that simply allows user to give feedback
 * of mechanic,search mechanic, add request,add and update user details from
 * service interface
 * @author saifyn arfia
 * @since 2020-12-30
 */

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**
	 * This is a method used to login with user credentials 
	 * @param email to check if given parameter exists
	 * @param password to check if for given emailid the password is valid 
	 * @return ResponseEntity of type String  
	 */
	
	@GetMapping("/userLogin")
	public ResponseEntity<String> userLogin(@Valid @RequestParam("email") String email,
			@RequestParam("password") String password) throws UserNotFoundException{
		if(userService.getUser(email)) {
			
		logger.info("User Login");
		boolean login = userService.loginUser(email, password);
		if (login) {
			logger.info("User Login Successful");
			return new ResponseEntity<String>("Login Successful", HttpStatus.OK);
		} else {
			logger.info("User Login Failed");
			return new ResponseEntity<String>("Login Failed", HttpStatus.NOT_FOUND);
		}
		}
		else {
			logger.error("User does not exist");
			throw new UserNotFoundException("User not found");
		}
	}
    
	/**
	 * This is a method used to give feedback to the mechanic
	 * @param mechanic id from URL to a specific mechanic to which feedback is to be given
	 * @param user id from URL to given feedback to mechanic
	 * @param feedback object. It contains the feedback data given by user 
	 * @return ResponseEntity of type String
	 */
	
	@PostMapping("/giveFeedback/{mechanicId}/{userId}")
	public ResponseEntity<String> giveFeedback(@Valid @PathVariable("mechanicId") int mechanicId,
			@PathVariable("userId") int userId, @RequestBody Feedback feedback) {
		logger.info("Giving Feedback to mechanic");

		String giveFeedback = userService.giveFeedback(feedback, mechanicId, userId);
		if (giveFeedback == null) {
			return new ResponseEntity<String>("Feedback not added", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(giveFeedback, HttpStatus.OK);
	}
    
	/**
	 * This is a method used to add request to a mechanic
	 * @param Service object This parameter contains the entire request data sent by user to a specific mechanic  
	 * @return ResponseEntity of type String
	 */
	
	@PostMapping("/addRequest")
	public ResponseEntity<String> addRequest(@Valid @RequestBody Service service) {
		logger.info("Adding Request");
		String addRequest = userService.addRequest(service);
		if (addRequest == null) {
			return new ResponseEntity<String>("Service request not sent", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(addRequest, HttpStatus.OK);
	}
    
	/**
	 * This is a method used to register the user
	 * @param User object. It contains the entire user data to be added  
	 * @return ResponseEntity of type String
	 */
	
	@PostMapping("/userRegister")
	public ResponseEntity<String> userRegistration(@Valid @RequestBody User user) {
		logger.info("Adding user");
		User addUser = userService.userRegistration(user);
		if (addUser == null) {
			return new ResponseEntity<String>("User not added", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("User Registered Sucessfully", HttpStatus.OK);
	}
    
	/**
	 * This is a method used to update the user by id
	 * @param User object .This parameter is used to pass the User data for updating the details
	 * @return ResponseEntity of type String
	 * @throws UserNotFoundException
	 */
	
	@PutMapping("/updateUser")
	public ResponseEntity<String> updateUserById(@Valid @RequestBody User user) throws UserNotFoundException {
		logger.info("Update user");
		if (userService.getUserById(user.getUserId()) != null) {
			userService.updateUserById(user);
			return new ResponseEntity<String>("User updated successfully", HttpStatus.OK);
		} else {
			logger.error("User cannot be updated, as id " + user.getUserId() + " not present");
			throw new UserNotFoundException("User could not be updated,as id " + user.getUserId() + " not present");
		}
	}
    
	/**
	 * This is a method to search a mechanic by location and display the list of mechanic
	 * @param location of type String. This parameter is used by user to enter location so it will show the nearby mechanics available  
	 * @return List of Mechanic
	 * @throws MechanicNotFoundException
	 */	
	
	@GetMapping("/searchMechanic")
	public ResponseEntity<List<Mechanic>> searchMechanic(@Valid @RequestParam("location") String location)
			throws MechanicNotFoundException {
		logger.info("Searching Mechanic by location");
		List<Mechanic> mechanicList = userService.searchMechanicByLocation(location);
		if (mechanicList.isEmpty()) {
			throw new MechanicNotFoundException("Mechanic does not exist at " + location + " location");
		}
		return new ResponseEntity<List<Mechanic>>(mechanicList, HttpStatus.OK);
	}

}
