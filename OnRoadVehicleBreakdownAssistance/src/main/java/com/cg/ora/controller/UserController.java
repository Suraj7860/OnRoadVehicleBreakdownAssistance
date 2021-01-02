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
import com.cg.ora.model.Feedback;
import com.cg.ora.model.Mechanic;
import com.cg.ora.model.Service;
import com.cg.ora.model.User;
import com.cg.ora.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	Logger logger= LoggerFactory.getLogger(UserController.class);

	@PostMapping("/giveFeedback/{mechanicId}/{userId}")
	public ResponseEntity<String> giveFeedback(@Valid  @PathVariable("mechanicId") int mechanicId,@PathVariable("userId") int userId, @RequestBody Feedback feedback) {
		logger.info("Giving Feedback to mechanic");

		String giveFeedback=userService.giveFeedback(feedback,mechanicId,userId);
		if (giveFeedback == null) {
			return new ResponseEntity("Feedback not added", HttpStatus.NOT_FOUND);
		}	
		return new ResponseEntity(giveFeedback, HttpStatus.OK);
}
	
	@PostMapping("/addRequest")
	public ResponseEntity<String> addRequest(@Valid @RequestBody Service service) {
		logger.info("Adding Request");
	    String addRequest = userService.addRequest(service);
		if (addRequest == null) {
			return new ResponseEntity("Service request not sent", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(addRequest, HttpStatus.OK);
	}
	
	@PostMapping("/userRegister")
	public ResponseEntity<User> userRegistration(@RequestBody User user) {
		logger.info("Adding user");
	    User addUser=userService.userRegistration(user);
		if (addUser == null) {
			return new ResponseEntity("User not added", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(addUser, HttpStatus.OK);
	}
	
	@PutMapping("/updateUser")
	public ResponseEntity updateUserById(@RequestBody User user) {
		logger.info("Updating User by id");
		User userId = userService.updateUserById(user);
		if (userId == null) {
			return new ResponseEntity("User Not Updated", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity("User Updated", HttpStatus.OK);
	}
	
	@GetMapping("/searchMechanic")
	public ResponseEntity searchMechanic(@RequestParam("location") String location) throws MechanicNotFoundException{		
		logger.info("Searching Mechanic by location");
		List<Mechanic> mechanicList=userService.searchMechanicByLocation(location);
		if (mechanicList.isEmpty()) {
			throw new MechanicNotFoundException("Mechanic does not exist at "+location+" location");
		}
		return new ResponseEntity(mechanicList, HttpStatus.OK);
	}
	

}
