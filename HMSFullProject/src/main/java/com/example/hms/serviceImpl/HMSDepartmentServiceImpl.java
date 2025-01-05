package com.example.hms.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.hms.exception.DepartmentExistsException;
import com.example.hms.exception.DepartmentIdNotFoundException;
import com.example.hms.exception.PhysiciansNotFoundException;
import com.example.hms.model.Department;
import com.example.hms.model.Physician;
import com.example.hms.model.Trained_In;
import com.example.hms.repository.DepartmentRepository;
import com.example.hms.repository.Trained_In_Repository;
import com.example.hms.service.HMSDepartmentService;
import com.example.hms.exception.DepartmentsNotFoundException;
import com.example.hms.exception.NoTrainedInsFoundException;

import lombok.AllArgsConstructor;

/**
 * Implementation of the HMSDepartmentService interface.
 * Provides services related to managing departments, their heads, and trained-in certifications.
 */
@Service
@AllArgsConstructor
public class HMSDepartmentServiceImpl implements HMSDepartmentService {

    private static final Logger logger = LoggerFactory.getLogger(HMSDepartmentServiceImpl.class);

    private final DepartmentRepository deptRepo;
    private final Trained_In_Repository trainedInRepo;

    /**
     * Creates a new department.
     * @param dept The department to be created.
     * @return The saved department.
     * @throws DepartmentExistsException If a department with the same ID already exists.
     */
    @Override
    public Department createDepartment(Department dept) {
        int id = dept.getDepartmentID();
        if (deptRepo.existsById(id)) {
            logger.error("Department creation failed. Department already exists with ID: {}", id);
            throw new DepartmentExistsException("Department exists for this ID " + id);
        }
        logger.info("Creating department with ID: {}", id);
        return deptRepo.save(dept);
    }

    /**
     * Retrieves all departments.
     * @return A list of all departments.
     */
    @Override
    public List<Department> getAllDepartments() {
        logger.info("Fetching all departments");
        return deptRepo.findAll();
    }

    /**
     * Retrieves a department by its ID.
     * @param departmentId The ID of the department.
     * @return The department with the given ID.
     * @throws DepartmentIdNotFoundException If no department is found with the given ID.
     */
    @Override
    public Department getDepartmentById(Integer departmentId) {
        logger.info("Fetching department with ID: {}", departmentId);
        Optional<Department> dept = deptRepo.findById(departmentId);
        if (dept.isEmpty()) {
            logger.error("Department not found with ID: {}", departmentId);
            throw new DepartmentIdNotFoundException("Department ID " + departmentId + " not found.");
        }
        return dept.get();
    }

    /**
     * Retrieves the head of a department by its ID.
     * @param departmentId The ID of the department.
     * @return The head of the department.
     * @throws PhysiciansNotFoundException If no head is assigned to the department.
     */
    @Override
    public Physician getHeadByDeptId(Integer departmentId) {
        logger.info("Fetching head of department with ID: {}", departmentId);
        Department dept = deptRepo.findById(departmentId).orElseThrow(() -> 
            new DepartmentIdNotFoundException("Department ID " + departmentId + " not found.")
        );
        if (dept.getHead() == null) {
            logger.error("No head found for department with ID: {}", departmentId);
            throw new PhysiciansNotFoundException("Physician not found.");
        }
        return dept.getHead();
    }

    /**
     * Retrieves departments led by a specific head.
     * @param head The head's ID.
     * @return A list of departments led by the specified head.
     * @throws DepartmentsNotFoundException If no departments are found for the head.
     */
    //

    /**
     * Checks if a department exists for a given physician ID.
     * @param physicianId The physician's ID.
     * @return true if a department exists, false otherwise.
     */
    @Override
    public boolean existsByPhysicianId(Integer physicianId) {
        logger.info("Checking if department exists for physician ID: {}", physicianId);
        return deptRepo.existsByPhysicianId(physicianId) > 0;
    }

    /**
     * Retrieves certifications for a department's head by department ID.
     * @param departmentId The department ID.
     * @return A list of certifications.
     * @throws NoTrainedInsFoundException If no certifications are found.
     */
    @Override
    public List<Trained_In> findCertificationsByDeptid(Integer departmentId) {
        logger.info("Fetching certifications for department ID: {}", departmentId);
        Department dept = deptRepo.findById(departmentId).orElseThrow(() -> 
            new DepartmentIdNotFoundException("Department ID " + departmentId + " not found.")
        );
        Integer id = dept.getHead().getEmployeeID();
        List<Trained_In> trainedList = trainedInRepo.getByPhysician(id);
        if (trainedList.isEmpty()) {
            logger.error("No certifications found for physician ID: {}", id);
            throw new NoTrainedInsFoundException("No Trained_In found for physician ID: " + id);
        }
        return trainedList;
    }

    /**
     * Updates the head of a department.
     * @param departmentId The department ID.
     * @param updatedHead The new head to be assigned.
     * @return The updated department.
     */
    @Override
    public Department updateHeadByDeptId(Integer departmentId, Physician updatedHead) {
        logger.info("Updating head for department ID: {}", departmentId);
        Department existingDept = deptRepo.findById(departmentId).orElseThrow(() -> 
            new DepartmentIdNotFoundException("Department ID " + departmentId + " not found.")
        );
        existingDept.setHead(updatedHead);
        return deptRepo.save(existingDept);
    }

    /**
     * Updates the name of a department.
     * @param departmentId The department ID.
     * @param newDepartmentName The new name for the department.
     * @return The updated department.
     */
    @Override
    public Department updateDepartmentName(Integer departmentId, String newDepartmentName) {
        logger.info("Updating name for department ID: {}", departmentId);
        Department dept = deptRepo.findById(departmentId).orElseThrow(() -> 
            new DepartmentIdNotFoundException("Department ID " + departmentId + " not found.")
        );
        dept.setName(newDepartmentName);
        return deptRepo.save(dept);
    }

	@Override
	public List<Department> getDepartmentsByHead(Integer head) {
		logger.info("Fetching departments led by head ID: {}",head);
		List<Department> deptList=deptRepo.findByHeadId(head);
		if(deptList.isEmpty()) {
			logger.error("No departments found for head ID:",head);
			throw new DepartmentsNotFoundException("No departments for head ID:"+head);
		}
		return deptList;
	}
}
