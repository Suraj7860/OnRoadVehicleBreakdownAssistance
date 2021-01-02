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
	
	@Override
	public Mechanic addMechanic(Mechanic mechanic)
	{
		logger.info("Adding Mechanic");
		return mechanicRepo.save(mechanic);
		
		
	}
	@Override
	public List<com.cg.ora.model.Service> viewRequest(int mechanicId) {
		logger.info("Viewing Request");
		List<com.cg.ora.model.Service> list=serviceRepo.findByMechanicId(mechanicId);
		return list;
	}
	
	@Override
	public List<Feedback> viewFeedback(int mecId) {
		logger.info("Viewing feedback by mechanic id");
		 Mechanic m=mechanicRepo.getOne(mecId);
		 List<Feedback> list=feedbackRepo.findByMechanic(m);
		return list;
	}

}
