package com.example.hms;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.*;
import java.util.List;

import com.example.hms.controller.HMSDepartmentController;
import com.example.hms.exception.DepartmentExistsException;
import com.example.hms.exception.DepartmentIdNotFoundException;
import com.example.hms.exception.PhysiciansNotFoundException;
import com.example.hms.exception.DepartmentsNotFoundException;
import com.example.hms.model.Department;
import com.example.hms.model.Physician;
import com.example.hms.model.Trained_In;
import com.example.hms.serviceImpl.HMSDepartmentServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class HMSDepartmentMockito {

    @InjectMocks
    private HMSDepartmentController departmentController;

    @Mock
    private HMSDepartmentServiceImpl departmentService;

    private Department department;
    private Physician head;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        head = new Physician(1, "Dr. Smith", "Cardiologist", 123456789);
        department = new Department(1, "Cardiology", head);
    }

    @Test
    public void testCreateDepartment() {
        when(departmentService.createDepartment(any(Department.class))).thenReturn(department);

        ResponseEntity<String> response = departmentController.createNewDepartment(department);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Record Created Successfully", response.getBody());
        verify(departmentService, times(1)).createDepartment(any(Department.class));
    }

    @Test
    public void testCreateDepartmentExistsException() {
        when(departmentService.createDepartment(any(Department.class))).thenThrow(new DepartmentExistsException("Department Exists"));

        Exception exception = assertThrows(DepartmentExistsException.class, () -> {
            departmentController.createNewDepartment(department);
        });

        assertEquals("Department Exists", exception.getMessage());
        verify(departmentService, times(1)).createDepartment(any(Department.class));
    }

    @Test
    public void testGetAllDepartments() {
        List<Department> departments = new ArrayList<>();
        departments.add(department);
        when(departmentService.getAllDepartments()).thenReturn(departments);

        ResponseEntity<List<Department>> response = departmentController.getAllDepartments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(departments, response.getBody());
        verify(departmentService, times(1)).getAllDepartments();
    }

    @Test
    public void testGetDepartmentById() {
        when(departmentService.getDepartmentById(anyInt())).thenReturn(department);

        ResponseEntity<Department> response = departmentController.getDepartmentById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(department, response.getBody());
        verify(departmentService, times(1)).getDepartmentById(anyInt());
    }

    @Test
    public void testGetDepartmentByIdNotFound() {
        when(departmentService.getDepartmentById(anyInt())).thenThrow(new DepartmentIdNotFoundException("Department not found"));

        Exception exception = assertThrows(DepartmentIdNotFoundException.class, () -> {
            departmentController.getDepartmentById(999);
        });

        assertEquals("Department not found", exception.getMessage());
        verify(departmentService, times(1)).getDepartmentById(anyInt());
    }

    @Test
    public void testGetHeadByDeptId() {
        when(departmentService.getHeadByDeptId(anyInt())).thenReturn(head);

        ResponseEntity<Physician> response = departmentController.getHeadByDeptId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(head, response.getBody());
        verify(departmentService, times(1)).getHeadByDeptId(anyInt());
    }

    @Test
    public void testGetHeadByDeptIdNotFound() {
        when(departmentService.getHeadByDeptId(anyInt())).thenThrow(new PhysiciansNotFoundException("Physician not found"));

        Exception exception = assertThrows(PhysiciansNotFoundException.class, () -> {
            departmentController.getHeadByDeptId(999);
        });

        assertEquals("Physician not found", exception.getMessage());
        verify(departmentService, times(1)).getHeadByDeptId(anyInt());
    }

    

    @Test
    public void testFindCertificationsByDeptId() {
        List<Trained_In> trainedIns = new ArrayList<>();
        when(departmentService.findCertificationsByDeptid(anyInt())).thenReturn(trainedIns);

        ResponseEntity<List<Trained_In>> response = departmentController.getHeadCertificationsByDeptId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trainedIns, response.getBody());
        verify(departmentService, times(1)).findCertificationsByDeptid(anyInt());
    }
    @Test
    public void testGetHeadByDeptIDNotFound() {
    	when(departmentService.getHeadByDeptId(anyInt())).thenThrow(new PhysiciansNotFoundException("Physician not found"));
    	Exception exception=assertThrows(PhysiciansNotFoundException.class, ()->{
    		departmentController.getHeadByDeptId(999);
    	});
    	assertEquals("Physician not found",exception.getMessage());
    	verify(departmentService, times(1)).getHeadByDeptId(anyInt());
    }
    @Test
    public void testUpdateHeadByDeptId() {
        when(departmentService.updateHeadByDeptId(anyInt(), any(Physician.class))).thenReturn(department);

        ResponseEntity<Department> response = departmentController.updateHeadByDeptid(department.getDepartmentID(), head);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(department, response.getBody());
        verify(departmentService, times(1)).updateHeadByDeptId(anyInt(), any(Physician.class));
    }

    @Test
    public void testUpdateDeptNameByDeptId() {
        Map<String, String> reqBody = new HashMap<>();
        reqBody.put("name", "Updated Cardiology");
        when(departmentService.updateDepartmentName(anyInt(), anyString())).thenReturn(department);

        ResponseEntity<Department> response = departmentController.updateDeptNameByDeptId(department.getDepartmentID(), reqBody);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(department, response.getBody());
        verify(departmentService, times(1)).updateDepartmentName(anyInt(), anyString());
    }

    @Test
    public void testGetDepartmentsByHead() {
        List<Department> departments = new ArrayList<>();
        departments.add(department);
        when(departmentService.getDepartmentsByHead(anyInt())).thenReturn(departments);

        ResponseEntity<List<Department>> response = departmentController.getDepartmentsByHead(head.getEmployeeID());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(departments, response.getBody());
        verify(departmentService, times(1)).getDepartmentsByHead(anyInt());
    }

    @Test
    public void testGetDeptByPhysicianId() {
        when(departmentService.existsByPhysicianId(anyInt())).thenReturn(true);

        boolean result = departmentController.getDeptByPhysicianId(head.getEmployeeID());

        assertTrue(result);
        verify(departmentService, times(1)).existsByPhysicianId(anyInt());
    }

    @Test
    public void testUpdateHeadByDeptIdNotFound() {
        when(departmentService.updateHeadByDeptId(anyInt(), any(Physician.class))).thenThrow(new DepartmentIdNotFoundException("Department not found"));

        Exception exception = assertThrows(DepartmentIdNotFoundException.class, () -> {
            departmentController.updateHeadByDeptid(999, head);
        });

        assertEquals("Department not found", exception.getMessage());
        verify(departmentService, times(1)).updateHeadByDeptId(anyInt(), any(Physician.class));
    }

    @Test
    public void testGetDepartmentsByHeadNotFound() {
        when(departmentService.getDepartmentsByHead(anyInt())).thenThrow(new DepartmentsNotFoundException("No departments found"));

        Exception exception = assertThrows(DepartmentsNotFoundException.class, () -> {
            departmentController.getDepartmentsByHead(999);
        });

        assertEquals("No departments found", exception.getMessage());
        verify(departmentService, times(1)).getDepartmentsByHead(anyInt());
    }
}
