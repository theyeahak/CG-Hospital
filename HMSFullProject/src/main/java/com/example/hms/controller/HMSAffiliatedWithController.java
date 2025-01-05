package com.example.hms.controller;
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

import org.springframework.web.bind.annotation.RestController;

import com.example.hms.model.Affiliated_With;

import com.example.hms.model.Department;

import com.example.hms.model.Physician;

import com.example.hms.serviceImpl.HMSAffiliatedWithServiceImpl;
 
/**

* REST Controller for managing affiliations between physicians and departments.

* This controller provides end points for creating affiliations, retrieving physicians

* by department, and managing department affiliations for physicians.

*/

@RestController

public class HMSAffiliatedWithController {

	private static final Logger logger = LoggerFactory.getLogger(HMSAffiliatedWithController.class);

	@Autowired

	private HMSAffiliatedWithServiceImpl affService;

	/**

     * Creates a new affiliation between a physician and a department.

     * 

     * @param aff the Affiliated_With entity to be created

     * @return ResponseEntity indicating the result of the operation

     */

	//1

	@PostMapping("/api/affiliated_with/post")

	public ResponseEntity<String> createAffilatedWith(@RequestBody Affiliated_With aff)

	{

		logger.info("Creating a new affiliation: {}", aff);

		affService.createAffiliatedWith(aff);

		logger.info("Affiliation created successfully: {}", aff);

		return new ResponseEntity<String>("Record Created Successfully", HttpStatus.CREATED);

	}

	 /**

     * Retrieves a list of physicians affiliated with a specific department.

     * 

     * @param departmentId the ID of the department to search for affiliated physicians

     * @return ResponseEntity containing a list of affiliated physicians

     */

	//Searching for list of physician affiliated with particular department

	//2

	@GetMapping("/api/affiliated_with/physicians/{deptid}")

	public ResponseEntity<List<Physician>> getPhysiciansByDeptId(@PathVariable("deptid") Integer departmentId){

		logger.info("Fetching physicians affiliated with department ID {}", departmentId);

		List<Physician> phylist=affService.getPhysiciansByDeptId(departmentId);

		logger.info("Found {} physicians affiliated with department ID {}", phylist.size(), departmentId);

		return new ResponseEntity<List<Physician>>(phylist, HttpStatus.OK);

	}

	/**

     * Retrieves a list of departments associated with a specific physician.

     * 

     * @param physicianId the ID of the physician to search for affiliated departments

     * @return ResponseEntity containing a list of affiliated departments

     */

	//Searching for list of department for a physician

	//3

	@GetMapping("/api/affiliated_with/department/{physicianid}")

	public ResponseEntity<List<Department>> getDepartmenstByPhyId(@PathVariable("physicianid") Integer physicianId)

	{

		logger.info("Fetching departments affiliated with physician ID {}", physicianId);

		List<Department> dept=affService.getDepartmentsByPhyId(physicianId);

		logger.info("Found {} departments affiliated with physician ID {}", dept.size(), physicianId);

		return new ResponseEntity<List<Department>>(dept, HttpStatus.OK);

	}

	 /**

     * Counts the total number of physicians affiliated with a specific department.

     * 

     * @param departmentId the ID of the department to count affiliated physicians

     * @return ResponseEntity containing the count of affiliated physicians

     */

	//Count total number of physician in a department

	//4

	@GetMapping("/api/affiliated_with/countphysician/{deptid}")

	public ResponseEntity<Integer> findPhyCountByDeptId(@PathVariable("deptid") Integer departmentId)

	{

		Integer i1=affService.findPhyCountByDeptId(departmentId);

		return new ResponseEntity<Integer>(i1, HttpStatus.OK);

	}

	/**

     * Checks if a physician has a primary affiliation.

     * 

     * @param physicianId the ID of the physician to check for primary affiliation

     * @return true if the physician has a primary affiliation, false otherwise

     */

	//Search the primary affiliation of physician

	//5

	@GetMapping("/api/affiliated_with/primary/{physicianid}/")

	public ResponseEntity<Boolean> findPrimaryAffByPhyId(@PathVariable("physicianid") Integer physicianId) {

        logger.info("Checking primary affiliation for physician ID {}", physicianId);

        boolean hasPrimaryAffiliation = affService.findPrimaryAffByPhyId(physicianId);

        logger.info("Physician ID {} has primary affiliation: {}", physicianId, hasPrimaryAffiliation);

        return new ResponseEntity<>(hasPrimaryAffiliation, HttpStatus.OK);

    }

	/**

     * Retrieves all affiliations in the system.

     * 

     * @return ResponseEntity containing a list of all affiliations

     */

	//6

	@GetMapping("/api/getaffiliatedAll")

	public ResponseEntity<List<Affiliated_With>> getAllAffiliations() {

        logger.info("Fetching all affiliations");

        List<Affiliated_With> affiliations = affService.getall();

        logger.info("Retrieved {} affiliations", affiliations.size());

        return new ResponseEntity<>(affiliations, HttpStatus.OK);

    }

}
 
