package com.example.hms.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.hms.model.Procedures;
import com.example.hms.service.HMSProceduresService;

import lombok.AllArgsConstructor;

/**
 * controller class for managing hospital procedures.
 * this class handles API endpoints for creating,updating,retrieving procedures.,.,
 */


@RestController
@Transactional
@AllArgsConstructor
public class HMSProceduresController {

	private HMSProceduresService proService;
	
	private static final Logger logger = LoggerFactory.getLogger(HMSProceduresController.class);
	
//ranjjan
	/**
     * adds a new procedure to hms
     *
     * @param procedure --->the procedure details to be added
     * @return ResponseEntity----> containing the saved procedure and HTTP status code
     */
	
	@PostMapping("/api/procedure")
	public ResponseEntity<Procedures> addProcedure(@RequestBody Procedures procedure) {
	    logger.info("adding a new procedure :)", procedure);
	    Procedures savedProcedure = proService.addProcedure(procedure);
	    logger.info("procedure added successfully with the details given ! :) ", savedProcedure);
	    
	    ResponseEntity<Procedures> response = new ResponseEntity<>(savedProcedure, HttpStatus.CREATED);
	    
	    return response;
	}


	
//ranjjan	
//	created exception
	/**
	 * my put method
     * updates the cost of an existing procedure.
     *
     * @param id      the ID of the procedure to be updated by me
     * @param reqBody a map containing the new cost for the procedure
     * @return ResponseEntity containing the updated procedure and HTTP status code
     */
	
	
	
	@PutMapping("/api/procedure/cost/{id}")
	public ResponseEntity<Procedures> updateProcedureCost(@PathVariable("id") Integer id, @RequestBody Map<String,Double> reqBody)
	{
		logger.info("fetching the relevant procedures to update its cost, where procedureId: {}",id);
		Double newCost=reqBody.get("cost");
        Procedures pro= proService.updateProcedureCost(id, newCost);
        logger.info("successfully updated the procedure cost :) theNewCost: {}",newCost);
        ResponseEntity<Procedures> response=new ResponseEntity<Procedures>(pro, HttpStatus.OK);
        return response;
    }
	
//ranjjan
	 /**
	  * my another put method
     * updates the name of an existing procedure.
     *
     * @param id      the ID of the procedure to update
     * @param reqBody is the map containing the new name for the procedure
     * @return ResponseEntity containing the updated procedure and HTTP status code
     */
	
	
	@PutMapping("/api/procedure/name/{id}")
	public ResponseEntity<Procedures> updateProcedureName(@PathVariable("id") Integer id, @RequestBody Map<String,String> reqBody)
	{
		logger.info("fetching the relevant procedures to update its Name,where procedureId: {}",id);
		String newName=reqBody.get("name");
		Procedures pro=proService.updateProcedureName(id, newName);
		logger.info("successfully updated the procedure name :) theNewName: {}",newName);
		ResponseEntity<Procedures>response= new ResponseEntity<Procedures>(pro,HttpStatus.OK);
		return response;
	}
	
	
	
//	
//	
////	created exception
//	@GetMapping("/cost/{name}")
//	public ResponseEntity<Procedures> getProceduresByName(@PathVariable("name") String procedureName)
//	{
//		Procedures pro=proService.getProcedureByName(procedureName);
//		return new ResponseEntity<Procedures>(pro,HttpStatus.OK);
//	}
//	
//
//	
	/**
	   * Retrieves a list of all procedures available in the system.
	   *
	   * @return ResponseEntity containing a list of procedures and HTTP status 200 (OK).
	   */
	@GetMapping("/api/procedures/")
	public ResponseEntity<List<Procedures>> getAllProcedures()
	{
		   logger.info("Request to get all procedures.");
		List<Procedures> li=proService.getAllProcedures();
		return new ResponseEntity<List<Procedures>>(li,HttpStatus.OK);
	}
	
	 /**
	   * Retrieves a procedure by its ID.
	   *
	   * @param procedureId The ID of the procedure.
	   * @return ResponseEntity containing the procedure details and HTTP status 200 (OK).
	   *         If the procedure is not found, returns HTTP status 404 (NOT FOUND).
	   */
	@GetMapping("/api/procedure/cost/{id}")
	public ResponseEntity<Procedures> getProcedureById(@PathVariable("id") int procedureId)
	{
		  logger.info("Request to get procedure by ID: {}", procedureId);
		Procedures pro=proService.getProceduresById(procedureId);
		return new ResponseEntity<Procedures>(pro,HttpStatus.OK);
	}
	
	/**
	   * Retrieves a procedure by its name.
	   *
	   * @param procedureName The name of the procedure.
	   * @return ResponseEntity containing the procedure details and HTTP status 200 (OK).
	   *         If the procedure is not found, throws an exception.
	   */
	
//	created exception
	@GetMapping("/cost/{name}")
	public ResponseEntity<Procedures> getProceduresByName(@PathVariable("name") String procedureName)
	{
		 logger.info("Request to get procedure by name: {}", procedureName);
		Procedures pro=proService.getProcedureByName(procedureName);
		return new ResponseEntity<Procedures>(pro,HttpStatus.OK);
	}

	
}