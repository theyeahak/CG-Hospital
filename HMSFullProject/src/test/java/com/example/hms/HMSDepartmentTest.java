package com.example.hms;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.hms.exception.DepartmentExistsException;
import com.example.hms.exception.DepartmentIdNotFoundException;
import com.example.hms.exception.PhysiciansNotFoundException;
import com.example.hms.exception.DepartmentsNotFoundException;
import com.example.hms.model.Department;
import com.example.hms.model.Physician;
import com.example.hms.model.Trained_In;
import com.example.hms.repository.DepartmentRepository;
import com.example.hms.repository.PhysicianRepository;
import com.example.hms.repository.Trained_In_Repository;
import com.example.hms.serviceImpl.HMSDepartmentServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class HMSDepartmentTest {
 
    @Autowired
    private HMSDepartmentServiceImpl departmentService;
 
    @Autowired
    private DepartmentRepository departmentRepository;
 
    @Autowired
    private Trained_In_Repository trainedInRepository;
    
    @Autowired
    private PhysicianRepository physicianRepository;
 
    private Department department;
    private Physician head;
 
    @BeforeEach
    public void setUp() {
        head = new Physician(1, "Dr. Smith", "Cardiologist", 123456789);
        department = new Department(1, "Cardiology", head);
        departmentRepository.save(department);
    }

    @Test
    public void testGetAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        assertNotNull(departments);
        assertFalse(departments.isEmpty());
    }
 
    @Test
    public void testGetDepartmentById() {
        Department foundDepartment = departmentService.getDepartmentById(department.getDepartmentID());
        assertNotNull(foundDepartment);
        assertEquals(department.getDepartmentID(), foundDepartment.getDepartmentID());
    }
 
    @Test
    public void testGetHeadByDeptId() {
        Physician headPhysician = departmentService.getHeadByDeptId(department.getDepartmentID());
        assertNotNull(headPhysician);
        assertEquals(head.getEmployeeID(), headPhysician.getEmployeeID());
    }
 
    
 
    @Test
    public void testExistsByPhysicianId() {
        boolean exists = departmentService.existsByPhysicianId(head.getEmployeeID());
        assertTrue(exists);
    }
 
    @Test
    public void testFindCertificationsByDeptid() {
        List<Trained_In> trainedIns = departmentService.findCertificationsByDeptid(department.getDepartmentID());
        assertNotNull(trainedIns);
        assertFalse(trainedIns.isEmpty());
    }
 
    @Test
    public void testUpdateHeadByDeptId() {
        Physician newHead = new Physician(2, "Dr. Johnson", "Neurologist", 987654321);
        departmentService.updateHeadByDeptId(department.getDepartmentID(), newHead);
        Department updatedDepartment = departmentService.getDepartmentById(department.getDepartmentID());
        assertEquals(newHead.getEmployeeID(), updatedDepartment.getHead().getEmployeeID());
    }
 
    @Test
    public void testUpdateDepartmentName() {
        String newName = "Updated Cardiology";
        Department updatedDepartment = departmentService.updateDepartmentName(department.getDepartmentID(), newName);
        assertEquals(newName, updatedDepartment.getName());
    }
 
    @Test
    public void testCreateDepartmentExistsException() {
        assertThrows(DepartmentExistsException.class, () -> {
            departmentService.createDepartment(department); // Existing department
        });
    }
 
    @Test
    public void testGetDepartmentByIdNotFound() {
        assertThrows(DepartmentIdNotFoundException.class, () -> {
            departmentService.getDepartmentById(999); // Non-existing department ID
        });
    }
 
    @Test
    public void testGetDepartmentsByHeadNotFound() {
        assertThrows(DepartmentsNotFoundException.class, () -> {
            departmentService.getDepartmentsByHead(999); // Non-existing head ID
        });
    }
    @Test
    public void testGetDepartmentsByHead() {
    	List<Department> departments=departmentService.getDepartmentsByHead(head.getEmployeeID());
    	assertNotNull(departments);
    	assertFalse(departments.isEmpty());
    }
    
    
    
}


