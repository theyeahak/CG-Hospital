package com.example.hms.service;

import java.util.List;

import com.example.hms.model.Department;
import com.example.hms.model.Physician;
import com.example.hms.model.Trained_In;



public interface HMSDepartmentService {
	
	
	public Department createDepartment(Department dept);
	public List<Department> getAllDepartments();
	public Department getDepartmentById(Integer departmentId);
	public Physician getHeadByDeptId(Integer departmentId);
	public List<Trained_In> findCertificationsByDeptid(Integer departmentId);
	public List<Department> getDepartmentsByHead(Integer head);
	public boolean existsByPhysicianId(Integer physicianId);
	public Department updateHeadByDeptId(Integer departmentId, Physician updatedHead);
	public Department updateDepartmentName(Integer departmentId, String newDepartmentName);
}
