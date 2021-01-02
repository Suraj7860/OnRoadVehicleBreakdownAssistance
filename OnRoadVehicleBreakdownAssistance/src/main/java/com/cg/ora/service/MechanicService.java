package com.cg.ora.service;

import java.util.List;

import com.cg.ora.model.Feedback;
import com.cg.ora.model.Mechanic;
import com.cg.ora.model.Service;

public interface MechanicService {
	public Mechanic addMechanic(Mechanic mechanic);

	public List<Service> viewRequest(int mechanicId);

	public List<Feedback> viewFeedback(int mechanicId);

}
