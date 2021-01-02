package com.cg.ora.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.ora.model.Mechanic;

// Creating Mechanic Repository

public interface MechanicRepository extends JpaRepository<Mechanic, Integer> {

	public List<Mechanic> getMechanicByLocationIgnoreCase(String location);

	public void deleteByMechanicId(int mechanicId);

	public Mechanic findByMechanicId(int mechanicId);
	

}
