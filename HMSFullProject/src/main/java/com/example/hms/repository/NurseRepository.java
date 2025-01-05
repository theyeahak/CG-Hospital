package com.example.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hms.model.Nurse;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Integer>{
	@Query("SELECT n.position FROM Nurse n WHERE n.employeeID= ?1")

	  public String findPosById(int id);

	 @Query("SELECT n.registered FROM Nurse n WHERE employeeID=?1")

	  public boolean aboutRegister(int id);

	 @Query(

			 value="SELECT * FROM Nurse  WHERE employeeID = (SELECT prepNurse FROM Appointment  WHERE appointmentID = :appointmentID)"

			 , nativeQuery = true)

     public Nurse  getNurseByappId(@Param("appointmentID") Integer appointmentID );

}
