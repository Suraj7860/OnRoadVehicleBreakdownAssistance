package com.cg.ora.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.cg.ora.exception.FeedbackNotFoundException;
import com.cg.ora.exception.MechanicNotFoundException;
import com.cg.ora.exception.UserNotFoundException;
import com.cg.ora.model.Feedback;
import com.cg.ora.model.Mechanic;
import com.cg.ora.model.User;
import com.cg.ora.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	AdminService adminService;
	
	Logger logger= LoggerFactory.getLogger(AdminController.class);
	@GetMapping("/viewFeedback")
	public ResponseEntity<Feedback> viewFeedback() {
		logger.info("Viewing Feedback");

		List<Feedback> viewFeedback = adminService.viewFeedback();
		if (viewFeedback.isEmpty()) {
			throw new FeedbackNotFoundException("FeedBack not found:");
		}
		return new ResponseEntity(viewFeedback, HttpStatus.OK);
	}
	
	@GetMapping("/viewMechanic")
	public ResponseEntity<Mechanic> viewMechanicDetails() throws MechanicNotFoundException {
		logger.info("Viewing mechanics");

		List<Mechanic> viewMechanic = adminService.viewMechanicDetails();
		if (viewMechanic.isEmpty()) {
			throw new MechanicNotFoundException("Mechanic does not exist");
		}
		return new ResponseEntity(viewMechanic, HttpStatus.OK);
	}
	
	@GetMapping("/viewUser")
	public ResponseEntity<User> viewUserDetails() {

		logger.info("Viewing Users");

		List<User> viewUser = adminService.viewUserDetails();
		if (viewUser.isEmpty()) {
			throw new UserNotFoundException("User does not exist");
		}
		return new ResponseEntity(viewUser, HttpStatus.OK);
	}
	
	@GetMapping("/allowOrBlockMechanic/{mechanicId}")
	public ResponseEntity<String> allowOrBlockMechanic(@PathVariable ("mechanicId") int mechanicId) {
		logger.info("Allow and Block method started");
	    String allowOrBlockMechanic = adminService.allowOrBlockMechanic(mechanicId);
		if (allowOrBlockMechanic == null) {
			return new ResponseEntity<String>("No mechanic found with mechanicId", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(allowOrBlockMechanic, HttpStatus.OK);
	}
	
	
	
}
