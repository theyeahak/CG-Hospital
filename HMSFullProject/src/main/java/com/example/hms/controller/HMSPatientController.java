package com.example.hms.controller;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hms.model.Patient;
import com.example.hms.model.Physician;
import com.example.hms.repository.PhysicianRepository;
import com.example.hms.service.HMSPatientService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

/**
 * Controller class responsible for handling patient-related requests.
 * Provides API endpoints for retrieving and managing patient information.
 */
@RestController
@AllArgsConstructor
@Transactional
public class HMSPatientController {

    private static final Logger logger = LoggerFactory.getLogger(HMSPatientController.class);

    private HMSPatientService patientService;
  
    private final PhysicianRepository physicianRepository;

    /**  12
     * Endpoint to add a new patient to the system.
     * 
     * @param patient the patient object containing the information to be added.
     * @return a ResponseEntity with the appropriate status message and HTTP status.
     */
    @PostMapping("api/patient")
    public ResponseEntity<String> addPatient(@RequestBody Patient patient) {
        try {
            logger.info("Adding new patient: {}", patient);
            Physician pcp = patient.getPcp();
            if (pcp != null && pcp.getEmployeeID() != null) {
                logger.info("Fetching physician with ID: {}", pcp.getEmployeeID());
                pcp = physicianRepository.findById(pcp.getEmployeeID())
                        .orElseThrow(() -> new RuntimeException("Physician not found"));
                patient.setPcp(pcp);
            }
            patientService.addPatient(patient);
            logger.info("Patient added successfully: {}", patient);
            return ResponseEntity.status(HttpStatus.CREATED).body("Record Created Successfully");
        } catch (Exception e) {
            logger.error("Error while adding patient: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    /** 13
     * Endpoint to update a patient's address based on their SSN.
     * 
     * @param ssn the SSN of the patient whose address is being updated.
     * @param address the new address to update.
     * @return a ResponseEntity with the status of the address update.
     */
    @PutMapping("api/patient/address/{ssn}")
    public ResponseEntity<String> updateAddress(@PathVariable("ssn") int ssn, @RequestBody Map<String, String> requestBody) {
        logger.info("Received request to update address for patient with SSN: {}", ssn);
        
        String address = requestBody.get("address");
        boolean isUpdated = patientService.updateAddress(ssn, address);
        
        if (isUpdated) {
            logger.info("Address updated successfully for patient with SSN: {}", ssn);
            return ResponseEntity.ok("Address updated successfully");
        } else {
            logger.info("Patient with SSN: {} not found for address update", ssn);
            return ResponseEntity.notFound().build();
        }
    }
    

    /** 14
     * Endpoint to update a patient's phone number based on their SSN.
     * 
     * @param ssn the SSN of the patient whose phone number is being updated.
     * @param phone the new phone number to update.
     * @return a ResponseEntity with the status of the phone number update.
     */
    @PutMapping("api/patient/phone/{ssn}")
    public ResponseEntity<String> updatePhone(@PathVariable("ssn") Integer ssn, @RequestBody Map<String, String> requestBody) {
        logger.info("Received request to update phone number for patient with SSN: {}", ssn);
        
        String phone = requestBody.get("phone");
        String response = patientService.updatePhone(ssn, phone);
        
        logger.info("Phone updated successfully for patient with SSN: {}", ssn);
        return ResponseEntity.ok(response);
    }
    
    
    ////
    /**
	   * Retrieves a list of all patients in the system.
	   * 
	   * @return ResponseEntity containing the list of patients and an HTTP status of 200 (OK).
	   * If no patients are found, returns a status of 404 (Not Found).
	   */
	  @GetMapping("api/patient")
	  public ResponseEntity<List<Patient>> getAllPatients() {
		  logger.info("Request to get all patients received.");
	    List<Patient> li=patientService.getAllPatients();
	    logger.info("Successfully retrieved all patients.");
	        return new ResponseEntity<List<Patient>>(li, HttpStatus.OK);
	  }
	  
	  /**
	   * Retrieves a list of all patients assigned to a specific physician.
	   * 
	   * @param physicianId The ID of the physician whose patients are to be retrieved.
	   * @return ResponseEntity containing the list of patients and an HTTP status of 200 (OK).
	   * If no patients are found, returns a status of 204 (No Content).
	   */
	 
	 @GetMapping("api/patient/{physicianid}")
	   public ResponseEntity<List<Patient>> getPatientsByPhysicianId(@PathVariable("physicianid") int physicianId) {
		 
	     List<Patient> patients = patientService.getPatientsByPhysicianId(physicianId);
	        if (patients.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	       }
	      return new ResponseEntity<>(patients, HttpStatus.OK);
	   }

	  /**
	   * Retrieves a specific patient assigned to a specific physician by their IDs.
	   * 
	   * @param physicianId The ID of the physician to whom the patient is assigned.
	   * @param patientId The ID of the patient.
	   * @return ResponseEntity containing the patient's details and an HTTP status of 200 (OK).
	   * If the patient is not found, returns a status of 404 (Not Found).
	   */
	 @GetMapping("api/patient/{physicianid}/{patientid}")
	   public ResponseEntity<Patient> getPatientByPhysicianIdAndPatientId( @PathVariable("physicianid") int physicianId,@PathVariable("patientid") int patientId) {
		   logger.info("Request to get patients by physician ID: {}", physicianId);
	      Patient patient = patientService.getPatientByPhysicianIdAndPatientId(physicianId, patientId);
	      if (patient != null) {
	          return new ResponseEntity<>(patient, HttpStatus.OK);
	       } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }

	 /**
	   * Retrieves the insurance information of a specific patient by their patient ID.
	   * 
	   * @param patientId The ID of the patient whose insurance information is to be retrieved.
	   * @return ResponseEntity containing the insurance ID of the patient and an HTTP status of 200 (OK).
	   */
	    @GetMapping("api/patient/insurance/{patientid}")
	    public ResponseEntity<Integer> getPatientInsuranceByPatientId(@PathVariable("patientid") int patientId) {
	    	  logger.info("Request to get patient by physician ID: {} and patient ID: {}",  patientId);
	        Integer insuranceId = patientService.getPatientInsuranceByPatientId(patientId);
	        return new ResponseEntity<>(insuranceId, HttpStatus.OK);
	    }
     
}
