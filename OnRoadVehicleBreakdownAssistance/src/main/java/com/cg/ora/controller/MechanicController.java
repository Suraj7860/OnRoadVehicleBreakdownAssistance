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
import org.springframework.web.bind.annotation.RestController;

import com.cg.ora.exception.RequestNotFoundException;
import com.cg.ora.model.Feedback;
import com.cg.ora.model.Mechanic;
import com.cg.ora.model.Service;
import com.cg.ora.service.MechanicService;

@RestController
@RequestMapping("/mechanic")
public class MechanicController {
	@Autowired
	MechanicService mechanicService;
	
	Logger logger= LoggerFactory.getLogger(MechanicController.class);


	@PostMapping("/mechanicRegister")
	public ResponseEntity<Mechanic> addMechanic(@Valid @RequestBody Mechanic mechanic) {
		logger.info("Adding Mechanic");
		Mechanic addMechanic = mechanicService.addMechanic(mechanic);
		if (addMechanic == null) {
			
			return new ResponseEntity("Mechanic not added", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(addMechanic, HttpStatus.OK);
	}

	@GetMapping("/viewFeedback/{mechanicId}")
	public ResponseEntity<List<Feedback>> viewFeedback(@PathVariable("mechanicId") int mechanicId) {
		logger.info("Viewing feedback according to mechanic id ");
		List<Feedback> viewFeedback = mechanicService.viewFeedback(mechanicId);
		if (viewFeedback.isEmpty()) {
			return new ResponseEntity("No Feedback for mechanic ", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(viewFeedback, HttpStatus.OK);
	}

	@GetMapping("/viewRequest/{mechanicId}")

	public ResponseEntity<List<Service>> viewRequest(@PathVariable("mechanicId") int mechanicId) throws RequestNotFoundException{
		logger.info("Viewing Request according to mechanic id");
		List<Service> viewRequest = mechanicService.viewRequest(mechanicId);
		if (viewRequest.isEmpty()) {
			throw new RequestNotFoundException("Request not found with id:" + mechanicId);
		}
		return new ResponseEntity(viewRequest, HttpStatus.OK);
	}

}
