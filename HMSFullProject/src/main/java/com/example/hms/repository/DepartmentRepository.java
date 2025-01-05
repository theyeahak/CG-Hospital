package com.example.hms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.hms.model.Department;




@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>{
	@Query(value = "SELECT * FROM department WHERE head = (SELECT EmployeeID FROM physician WHERE EmployeeID = :headId)", nativeQuery = true)
    List<Department> findByHeadId(@Param("headId") Integer headId);
	@Query(value = "SELECT COUNT(*) FROM department WHERE head = :physicianId", nativeQuery = true)
    Integer existsByPhysicianId(@Param("physicianId") Integer physicianId);
}
