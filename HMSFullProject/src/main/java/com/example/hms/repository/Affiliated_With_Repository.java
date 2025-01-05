package com.example.hms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hms.model.AffiliatedWithId;
import com.example.hms.model.Affiliated_With;



@Repository
public interface Affiliated_With_Repository extends JpaRepository<Affiliated_With, AffiliatedWithId>{
	@Query(value="select Physician from affiliated_with where Department= :deptid", nativeQuery = true)
	public List<Integer> findByDepartment(@Param("deptid") Integer departmentId);
	
	@Query(value="select *from  affiliated_with where Physician= :phyid", nativeQuery = true)
	public List<Affiliated_With> findByPhysician(@Param("phyid") Integer physicianId);
	
    @Query(value="select count(Physician) from affiliated_with where Department=:deptid", nativeQuery = true)
    public Integer findPhyCountByDeptId(@Param("deptid") Integer deptid);
    
    @Query(value="select count(PrimaryAffiliation) from affiliated_with where Physician=( select EmployeeId from physician where EmployeeID=:physicianid)", nativeQuery = true)
    public List<Integer> findPrimaryAffByPhyId(@Param("physicianid") Integer physicianid);
}
