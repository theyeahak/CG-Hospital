package com.example.hms.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hms.exception.PositionNotFoundForPosition;
import com.example.hms.model.Physician;
import com.example.hms.service.HMSPhysicianService;

@RestController
@Transactional
/**
 * Controller class for managing physician-related operations in the hospital management system.
 * Provides endpoints to get physician details, add a physician, and update their information.
 */
public class HMSPhysicianController {
	@Autowired HMSPhysicianService phyService;
	 private static final Logger logger = LoggerFactory.getLogger(HMSPhysicianController.class);
	 /**
	   * Retrieves a physician by their employee ID.
	   *
	   * @param empid The employee ID of the physician.
	   * @return ResponseEntity containing the physician details and HTTP status 200 (OK).
	   *         If the physician is not found, a 404 status will be returned.
	   */
	// getById()
	@GetMapping("/api/physician/empid/{empid}")
	public ResponseEntity<Physician> getById(@PathVariable int empid) {
		logger.info("Request to get physician by employee ID: {}", empid);
		Physician res = phyService.getPhyById(empid);
		return new ResponseEntity<Physician>(res, HttpStatus.OK);
	}
	

	  /**
	   * Retrieves a physician by their name.
	   *
	   * @param name The name of the physician.
	   * @return ResponseEntity containing the physician details and HTTP status 200 (OK).
	   *         If no physician with that name is found, a 404 status will be returned.
	   */
	@GetMapping("/api/physician/name/{name}")
	public ResponseEntity<Physician> getByName(@PathVariable String name) {
		 logger.info("Request to get physician by name: {}", name);
		Physician res = phyService.getPhyByName(name);
		return new ResponseEntity<Physician>(res, HttpStatus.OK);
	}
	
	 /**
	   * Retrieves a list of physicians by their position.
	   *
	   * @param pos The position of the physician (e.g., "Surgeon").
	   * @return ResponseEntity containing a list of physicians with the specified position
	   *         and HTTP status 200 (OK).
	   *         If no physicians are found for the position, throws a custom exception.
	   */
	@GetMapping("/api/physician/position/{pos}")
	public ResponseEntity<List<Physician>> getByPosition(@PathVariable String pos) {
		 logger.info("Request to get physician by position: {}", pos);
		List<Physician> res = phyService.getPhyByPos(pos);
		if (res.isEmpty()) throw new PositionNotFoundForPosition(pos + " NOt Found!");
		return new ResponseEntity<List<Physician>>(res, HttpStatus.OK);
	}
	
	  /**
	   * Adds a new physician to the system.
	   *
	   * @param p The physician object to be added.
	   * @return ResponseEntity with a success message and HTTP status 200 (OK).
	   */

	@PostMapping("/api/physician/post")
	public ResponseEntity<String> postPhysician(@RequestBody Physician p) {
		  logger.info("Request to add new physician: {}", p.getName());
		phyService.addPhysician(p);
		return new ResponseEntity<String>("Record Created Successfully", HttpStatus.OK);
	}
	

	  /**
	   * Updates the position of a physician by their employee ID.
	   *
	   * @param employeeId The employee ID of the physician whose position is to be updated.
	   * @param reqBody A map containing the new position of the physician.
	   * @return ResponseEntity containing the updated physician and HTTP status 200 (OK).
	   *         If the position is invalid or the physician does not exist, returns 400 or 404.
	   */
	@PutMapping("/update/position/{employeeId}")
	public ResponseEntity<Physician> updatePhysicianPosition(@PathVariable("employeeId") Integer employeeId, @RequestBody Map<String, String> reqBody) {
		  logger.info("Request to update position for physician ID: {} with new position: {}", employeeId);
		String newPosition = reqBody.get("newPosition");
	    if (newPosition == null || newPosition.trim().isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Return 400 if position is invalid
	    }
	    Physician updatedPhysician = phyService.updatePosition(newPosition, employeeId);

	    if (updatedPhysician == null) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if physician not found
	    }
	    return new ResponseEntity<>(updatedPhysician, HttpStatus.OK);
	}
	 /**
	   * Updates the name of a physician by their employee ID.
	   *
	   * @param empid The employee ID of the physician whose name is to be updated.
	   * @param reqBody A map containing the new name of the physician.
	   * @return ResponseEntity containing the updated physician and HTTP status 200 (OK).
	   *         If the new name is invalid or the physician does not exist, returns 400 or 404.
	   */
	@PutMapping("/api/physician/update/{empid}")
	public ResponseEntity<Physician>updatePhysicianName(@PathVariable Integer empid,@RequestBody Map<String,String>reqBody){
		String newName=reqBody.get("newName");
		if(newName==null||newName.trim().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Physician updatedPhysician=phyService.updateName(empid, newName);
		if(updatedPhysician==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(updatedPhysician,HttpStatus.OK);
		
	}
	/**
	   * Updates the SSN of a physician by their employee ID.
	   *
	   * @param empid The employee ID of the physician whose SSN is to be updated.
	   * @param reqBody A map containing the new SSN of the physician.
	   * @return ResponseEntity containing the updated physician and HTTP status 200 (OK).
	   */
	@PutMapping("/api/physician/update/{ssn}/{empid}")
	public ResponseEntity<Physician> updatePhysicianSsn(@PathVariable Integer empid, @RequestBody Map<String, Integer> reqBody) {
		   logger.info("Request to update SSN for physician ID: {} with new SSN: {}", empid);
		Integer newSSN = reqBody.get("newSSN");
		Physician p = phyService.updateSSN(empid, newSSN);
		return new ResponseEntity<Physician>(p, HttpStatus.OK);
	}
	
	
	
}
