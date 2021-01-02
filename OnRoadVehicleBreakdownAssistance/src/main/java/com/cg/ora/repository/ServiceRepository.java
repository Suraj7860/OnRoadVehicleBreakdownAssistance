package com.cg.ora.repository;
import com.cg.ora.model.Service;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
// Service repository interface
@Repository
public interface ServiceRepository extends JpaRepository<Service,Integer>{

	public List<Service> findByMechanicId(int mecId);
	
	public Service getByUserId(int userId);

	public com.cg.ora.model.Service findByUserIdAndMechanicId(int mechanicId, int userId);

//	public Service findByuserIdAndmechanicId(int mechanicId, int userId);

	

}
