package com.example.hms.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hms.exception.PhysicianIdNotFound;
import com.example.hms.model.Physician;
import com.example.hms.repository.PhysicianRepository;
import com.example.hms.service.HMSPhysicianService;

/**
 * Service implementation for managing physician-related operations in the Hospital Management System.
 * This service provides methods for retrieving physicians by ID, name, or position,
 * updating physician details (e.g., name, position, SSN), and adding new physicians.
 */
@Service
public class HMSPhysicianServiceImpl implements HMSPhysicianService{
	private static final Logger logger = LoggerFactory.getLogger(HMSPhysicianServiceImpl.class);
	@Autowired PhysicianRepository phyRepo;
	
	
	  /**
     * Retrieves a physician by their employee ID.
     * If the physician with the given ID is not found, an exception is thrown.
     *
     * @param empid The employee ID of the physician.
     * @return The physician with the specified employee ID.
     * @throws PhysicianIdNotFound If no physician with the specified employee ID is found.
     */
	
	@Override
	public Physician getPhyById(int empid) {
		  logger.info("Fetching physician with employee ID: {}", empid);
		Physician p = phyRepo.findById(empid).orElse(null);
		if (p == null) throw new PhysicianIdNotFound(empid + " Not Found!");
		return p;
	}
	
	
	 /**
     * Retrieves a physician by their name.
     * 
     * @param name The name of the physician.
     * @return The physician with the specified name, or null if not found.
     */
	
	@Override
	public Physician getPhyByName(String name) {
		 logger.info("Fetching physician with name: {}", name);
		return phyRepo.findByName(name);
	}
	

    /**
     * Retrieves a list of physicians based on their position.
     * 
     * @param pos The position of the physicians.
     * @return A list of physicians with the specified position.
     */
	
	@Override
	public List<Physician> getPhyByPos(String pos) {
		return phyRepo.findByPosition(pos);
	}

	 /**
     * Adds a new physician to the system.
     * 
     * @param p The physician object to be added.
     */
	
	@Override
	public void addPhysician(Physician p) {
		 logger.info("Adding new physician: {}", p);
		phyRepo.save(p);
	}
	
	/**
     * Updates the position of an existing physician based on their employee ID.
     * 
     * @param position The new position to be assigned to the physician.
     * @param employeeId The employee ID of the physician whose position is to be updated.
     * @return The updated physician object.
     */
	
	@Override
	public Physician updatePosition(String position, Integer employeeId) {
		Physician p = phyRepo.findById(employeeId).get();
		p.setPosition(position);
		phyRepo.save(p);
		return p;
	}
	 /**
     * Updates the name of an existing physician based on their employee ID.
     * 
     * @param empid The employee ID of the physician whose name is to be updated.
     * @param newName The new name for the physician.
     * @return The updated physician object.
     */
	@Override
	public Physician updateName(Integer empid,String newName) {
		Physician p=phyRepo.findById(empid).get();
		p.setName(newName);
		return phyRepo.save(p);
	}
	
	/**
     * Updates the SSN of an existing physician based on their employee ID.
     * 
     * @param empid The employee ID of the physician whose SSN is to be updated.
     * @param newSSN The new SSN for the physician.
     * @return The updated physician object.
     */
	
	@Override
	public Physician updateSSN(Integer empid, Integer newSSN) {
		Physician p = phyRepo.findById(empid).get();
		p.setSsn(newSSN);
		return phyRepo.save(p);
	}
	
	/**
     * Retrieves a list of all physicians in the system.
     * 
     * @return A list of all physicians.
     */
	
	public List<Physician> getAll() {
		return phyRepo.findAll();
	}
	
	 /**
     * Saves a physician object to the database. 
     * Used for both adding a new physician and updating an existing one.
     * 
     * @param physician The physician object to be saved.
     */
	
	public void savePhysician(Physician physician) {
		phyRepo.save(physician);
	}

}
