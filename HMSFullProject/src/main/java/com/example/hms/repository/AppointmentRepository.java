package com.example.hms.repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hms.model.Nurse;
import com.example.hms.model.Appointment;
import com.example.hms.model.Room;



@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{
	
	//	
	@Query(
		value = "select * from appointment where physician = :phyId;",
		nativeQuery = true
	)
	public List<Appointment> findByPhysician(@Param("phyId") int phyId);
	
	//
	@Query(
			value = "SELECT * FROM appointment WHERE physician = :phyId AND (starto >= :date and endo <= :date);",
			nativeQuery = true
			)
	public List<Appointment> findByPhysicianAndDate(@Param("phyId") int phyId, @Param("date") LocalDateTime date);
		  
    //
	List<Appointment> findByPatientSsn(int patientId);
		  
	 //
	Optional<Appointment> findByPatientSsnAndStartBetween(int patientId, LocalDateTime startOfDay, LocalDateTime endOfDay);

	 //
	@Query(value="select *from appointment where PrepNurse=:nurseId",nativeQuery = true)
	List<Appointment> findByPrepNurse(@Param("nurseId") Integer nurseid);
		 
	// 
    @Query(value = "SELECT * FROM Appointment WHERE PrepNurse = ?1 AND Starto = ?2",nativeQuery = true)
	public List<Appointment>  getPatientsByNurseAndDate(int nurseId, LocalDateTime startDate);
      
//9//
    @Query(value = "SELECT * FROM room " +
              "WHERE (BlockCode, BlockFloor) IN (" +
              "    SELECT BlockCode, BlockFloor " +
              "    FROM on_call " +
              "    WHERE Nurse IN (" +
              "        SELECT PrepNurse FROM appointment " +
              "        WHERE PhysicianId = :physicianid AND DATE(Starto) = DATE(:date)" +
              "    )" +
              ");", nativeQuery = true)
      List<Room> getRoomByPhyIdOnDate(@Param("physicianid") int physicianid, @Param("date") LocalDateTime date);
    
    /**
	 * Repository interface for managing Appointment entities.
	 */

	/**
	 * finds the appointments by their start date and time.
	 * 
	 * @param startDate start date and time of the appointment.
	 * @return A list of appointments that start at the specified time.
	 */

	// ranjjan
	public List<Appointment> findByStart(LocalDateTime startDate);

	/**
	 * finds appointments by patientid and date.
	 * 
	 * @param patientId SSN (soc Security number) of the patient.
	 * @param date--->  date to search within.
	 * @return A list of appointments matching the criteria.
	 */

	// ranjjan
	/**
	 * @Query: Annotation used in Spring Data JPA to define a custom JPQL (Java
	 *         Persistence Query Language) query. This allows you to specify a query
	 *         explicitly instead of relying on method naming conventions.
	 */
	@Query("SELECT a FROM Appointment a WHERE a.patient.ssn = :patientId AND a.start <= :date AND a.end >= :date")

	List<Appointment> findByPatientIdAndDate(@Param("patientId") String patientId, @Param("date") LocalDateTime date);

	/**
	 * finds distinct nurses assigned to a patient during a specific date range.
	 * same like the above only change is that i am selecting only the a.prepnurse,
	 * nurses
	 * 
	 * @param patientid SSN of the patient.
	 * @param date      date to search within.
	 * @return a list of distinct nurses associated with the patient and date.
	 */

	// ranjjan
	@Query("SELECT DISTINCT a.prepNurse FROM Appointment a WHERE a.patient.ssn = :patientId AND a.start <= :date AND a.end >= :date")

	List<Nurse> findNursesByPatientIdAndDate(@Param("patientId") String patientId, @Param("date") LocalDateTime date);

	// ranjjan
	@Query("SELECT a.prepNurse FROM Appointment a WHERE a.patient.id = :patientId")

	List<Nurse> findNursesByPatientId(@Param("patientId") int patientId);

	// ranjjan
	/**
	 * Positional Parameter:
	 * 
	 * ?1 represents the first parameter passed to the method where this query is
	 * used. The number after the ? indicates the position of the parameter in the
	 * method's argument list.
	 * 
	 * @param id
	 * @return string
	 */

	@Query("SELECT a.examinationRoom FROM Appointment a WHERE appointmentID=?1")

	public String getExaminationRoomBYappID(int id);

}
