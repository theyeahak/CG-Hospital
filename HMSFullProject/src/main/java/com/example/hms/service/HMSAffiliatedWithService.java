package com.example.hms.service;

import java.util.List;
import com.example.hms.model.Affiliated_With;
import com.example.hms.model.Department;
import com.example.hms.model.Physician;

public interface HMSAffiliatedWithService {
	
	public Affiliated_With createAffiliatedWith(Affiliated_With affObj);
	public List<Physician> getPhysiciansByDeptId(Integer deptid);
	public List<Department> getDepartmentsByPhyId(Integer phyId);
	public Integer findPhyCountByDeptId(Integer deptId);
	public boolean findPrimaryAffByPhyId(Integer physicianId);
	public List<Affiliated_With> getall();

}
