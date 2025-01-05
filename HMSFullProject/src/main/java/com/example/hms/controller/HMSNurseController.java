package com.example.hms.controller;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.hms.model.Nurse;
import com.example.hms.service.HMSNurseService;
/**
* Controller class for managing nurse-related operations in the Hospital Management System.
* Provides REST end points for creating, retrieving, updating, and managing nurse details.
*/
@RestController
public class HMSNurseController {
    private static final Logger logger = LoggerFactory.getLogger(HMSNurseController.class);
    @Autowired
    private HMSNurseService nurseservice;
 
    /**
     * Adds a new nurse record to the database.
     * 
     * @param nurse The nurse object containing details to be saved.
     * @return A ResponseEntity with a success message and HTTP status CREATED.
     */
    //1
    @RequestMapping(value = "api/nurse", method = RequestMethod.POST)
    public ResponseEntity<String> createNurse(@RequestBody Nurse nurse) {
        logger.info("Entering createNurse with nurse details: {}", nurse);
        nurseservice.saveNurse(nurse);
        logger.info("Nurse record created successfully");
        return new ResponseEntity<>("Record Created Successfully", HttpStatus.CREATED);
    }
    /*@RequestMapping(value = "api/nurse", method = RequestMethod.POST)
    public ResponseEntity<String> createNurse(@RequestBody Nurse nurse) {
        if (nurse.getEmployeeID() == null) {
            logger.error("Employee ID is null when creating a nurse.");
        }
        logger.info("Entering createNurse with nurse details: {}", nurse);
        nurseservice.saveNurse(nurse);
        logger.info("Nurse record created successfully");
        return new ResponseEntity<>("Record Created Successfully", HttpStatus.CREATED);
    }*/
 
    /**
     * Retrieves all nurse records from the database.
     * 
     * @return A ResponseEntity containing a list of all nurses and HTTP status OK.
     */
    //2
    @GetMapping("/api/nurse/")
    public ResponseEntity<List<Nurse>> getllNurse() {
        logger.info("Entering getllNurse");
        List<Nurse> li = nurseservice.getAll();
        logger.info("Fetched all nurses, count: {}", li.size());
        return new ResponseEntity<>(li, HttpStatus.OK);
    }
 
    /**
     * Retrieves details of a nurse by their employee ID.
     * 
     * @param empid The employee ID of the nurse to be retrieved.
     * @return A ResponseEntity containing the nurse details and HTTP status OK.
     */
    //3
    @RequestMapping(value = "api/nurse/{empid}", method = RequestMethod.GET)
    public ResponseEntity<Nurse> getNurseById(@PathVariable("empid") int empid) {
        logger.info("Entering getNurseById with empid: {}", empid);
        Nurse nurse = nurseservice.getById(empid);
        logger.info("Fetched nurse details: {}", nurse);
        return new ResponseEntity<>(nurse, HttpStatus.OK);
    }
 
    /**
     * Retrieves the position of a nurse by their employee ID.
     * 
     * @param empid The employee ID of the nurse.
     * @return A ResponseEntity containing the position of the nurse and HTTP status OK.
     */
    //4
    @RequestMapping(value = "api/nurse/position/{empid}", method = RequestMethod.GET)
    public ResponseEntity<String> getPosById(@PathVariable("empid") int empid) {
        logger.info("Entering getPosById with empid: {}", empid);
        String pos = nurseservice.getPosById(empid);
        logger.info("Position for empid {}: {}", empid, pos);
        return new ResponseEntity<>(pos, HttpStatus.OK);
    }
 
    /**
     * Checks if a nurse is registered by their employee ID.
     * 
     * @param empid The employee ID of the nurse.
     * @return A ResponseEntity containing the registration status of the nurse and HTTP status OK.
     */
    //5
    @RequestMapping(value = "api/nurse/registered/{empid}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> getregister(@PathVariable("empid") int empid) {
        logger.info("Entering getregister with empid: {}", empid);
        boolean isRegistered = nurseservice.findRegister(empid);
        logger.info("Registration status for empid {}: {}", empid, isRegistered);
        return new ResponseEntity<>(isRegistered, HttpStatus.OK);
    }
 
    /**
     * Updates the registration status of a nurse by their employee ID.
     * 
     * @param empid The employee ID of the nurse.
     * @param nurse The nurse object containing the updated registration status.
     * @return A ResponseEntity containing the updated nurse details and HTTP status OK.
     */
    //6
    @RequestMapping(value = "api/nurse/registered/{empid}", method = RequestMethod.PUT)
    public ResponseEntity<Nurse> updateNurseRegistrationStatus(@PathVariable("empid") int empid,@RequestBody Nurse nurse) {
    	logger.info("Entering updateNurseRegistrationStatus with empid: {} and registration status: {}", empid, nurse.getRegistered());
        Nurse updatedNurse = nurseservice.updateNurseRegistrationStatus(empid, nurse);
        logger.info("Updated nurse registration status: {}", updatedNurse);
        return new ResponseEntity<>(updatedNurse, HttpStatus.OK);
    }
 
    /**
     * Updates the SSN of a nurse by their employee ID.
     * 
     * @param empid The employee ID of the nurse.
     * @param nurse The nurse object containing the updated SSN.
     * @return A ResponseEntity containing the updated nurse details and HTTP status OK, or NOT_FOUND if the nurse is not found.
     */
    //7
    @RequestMapping(value="api/nurse/ssn/{empid}", method=RequestMethod.PUT)
    public ResponseEntity<Nurse> updateNurseSSN(@PathVariable("empid") int empid,@RequestBody Nurse nurse){
    	Nurse updatedNurse=nurseservice.updateSSN(empid,nurse);
    	return new ResponseEntity<>(updatedNurse, HttpStatus.OK);
    }
}

