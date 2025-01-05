package com.example.hms.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

import com.example.hms.model.Department;
import com.example.hms.model.Physician;
import com.example.hms.model.Trained_In;
import com.example.hms.serviceImpl.HMSDepartmentServiceImpl;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Controller for managing Department-related APIs.
 * Provides endpoints for creating, retrieving, updating, and managing departments and their relationships.
 */
@RestController
@Transactional
@AllArgsConstructor
@Data
public class HMSDepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(HMSDepartmentController.class);

    private final HMSDepartmentServiceImpl deptService;

    /**
     * Creates a new department.
     * @param dept The department object to be created.
     * @return ResponseEntity with a success message and HTTP status CREATED.
     */
    @PostMapping("/api/department")
    public ResponseEntity<String> createNewDepartment(@RequestBody Department dept) {
        logger.info("Creating new department: {}", dept);
        Department dep = deptService.createDepartment(dept);
        return new ResponseEntity<>("Record Created Successfully", HttpStatus.CREATED);
    }

    /**
     * Retrieves all departments.
     * @return ResponseEntity containing the list of all departments and HTTP status OK.
     */
    @GetMapping("/api/department/")
    public ResponseEntity<List<Department>> getAllDepartments() {
        logger.info("Fetching all departments");
        List<Department> li = deptService.getAllDepartments();
        return new ResponseEntity<>(li, HttpStatus.OK);
    }

    /**
     * Retrieves a department by its ID.
     * @param deptid The ID of the department.
     * @return ResponseEntity containing the department object and HTTP status OK.
     */
    @RequestMapping(value = "/api/department/{deptid}", method = RequestMethod.GET)
    public ResponseEntity<Department> getDepartmentById(@PathVariable("deptid") Integer deptid) {
        logger.info("Fetching department with ID: {}", deptid);
        Department dept = deptService.getDepartmentById(deptid);
        return new ResponseEntity<>(dept, HttpStatus.OK);
    }

    /**
     * Retrieves the head of a department by department ID.
     * @param deptid The ID of the department.
     * @return ResponseEntity containing the head of the department and HTTP status OK.
     */
    @GetMapping("/api/department/head/{deptid}")
    public ResponseEntity<Physician> getHeadByDeptId(@PathVariable("deptid") Integer deptid) {
        logger.info("Fetching head of department with ID: {}", deptid);
        Physician p1 = deptService.getHeadByDeptId(deptid);
        return new ResponseEntity<>(p1, HttpStatus.OK);
    }

    /**
     * Retrieves certifications of the head of a department by department ID.
     * @param departmentId The ID of the department.
     * @return ResponseEntity containing the list of certifications and HTTP status OK.
     */
    @GetMapping("/api/department/headcertification/{deptid}")
    public ResponseEntity<List<Trained_In>> getHeadCertificationsByDeptId(@PathVariable("deptid") Integer departmentId) {
        logger.info("Fetching certifications of head for department ID: {}", departmentId);
        List<Trained_In> trainedList = deptService.findCertificationsByDeptid(departmentId);
        return new ResponseEntity<>(trainedList, HttpStatus.OK);
    }

    /**
     * Retrieves departments managed by a specific head.
     * @param head The ID of the head (physician).
     * @return ResponseEntity containing the list of departments and HTTP status OK.
     */
    

    /**
     * Checks if a physician is the head of any department.
     * @param physicianId The ID of the physician.
     * @return true if the physician is the head of any department, false otherwise.
     */
    @GetMapping("/api/department/check/{physicianid}")
    public boolean getDeptByPhysicianId(@PathVariable("physicianid") Integer physicianId) {
        logger.info("Checking if physician ID: {} is head of any department", physicianId);
        return deptService.existsByPhysicianId(physicianId);
    }

    /**
     * Updates the head of a department.
     * @param departmentId The ID of the department.
     * @param updatedHead The updated head (physician).
     * @return ResponseEntity containing the updated department object and HTTP status OK.
     */
    @PutMapping("/api/department/update/headid/{deptid}")
    public ResponseEntity<Department> updateHeadByDeptid(@PathVariable("deptid") Integer departmentId, @RequestBody Physician updatedHead) {
        logger.info("Updating head of department ID: {} with new head: {}", departmentId, updatedHead);
        Department updatedDept = deptService.updateHeadByDeptId(departmentId, updatedHead);
        return new ResponseEntity<>(updatedDept, HttpStatus.OK);
    }

    /**
     * Updates the name of a department.
     * @param departmentId The ID of the department.
     * @param reqBody1 Request body containing the new department name.
     * @return ResponseEntity containing the updated department object and HTTP status OK.
     */
    @PutMapping("/api/department/update/deptname/{deptid}")
    public ResponseEntity<Department> updateDeptNameByDeptId(@PathVariable("deptid") Integer departmentId, @RequestBody Map<String, String> reqBody1) {
        String newDepartmentName = reqBody1.get("name");
        logger.info("Updating name of department ID: {} to new name: {}", departmentId, newDepartmentName);
        Department Dept = deptService.updateDepartmentName(departmentId, newDepartmentName);
        return new ResponseEntity<>(Dept, HttpStatus.OK);
    }
    @GetMapping("api/departmentsByHead/{head}")
    public ResponseEntity<List<Department>> getDepartmentsByHead(@PathVariable("head") Integer head){
    	List<Department> departments=deptService.getDepartmentsByHead(head);
    	return new ResponseEntity<>(departments, HttpStatus.OK);
    }
}
