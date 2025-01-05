package com.example.hms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hms.model.TrainedInId;
import com.example.hms.model.Trained_In;



@Repository
public interface Trained_In_Repository extends JpaRepository<Trained_In, TrainedInId> {
//	@Query(
//			value = "select * from trained_in where physician = :phyId and Treatment = :procId;",
//			nativeQuery = true
//	)
//	public Trained_In findByPhysicianAndTreatment(int phyId, int procId);
	
	@Query(value = "select * from trained_in where physician = :phyId;", nativeQuery = true)
	public List<Trained_In> getByPhysician(@Param("phyId") int phyId);
	
	@Query(value="select * from trained_in where Physician=:physicianId", nativeQuery = true)
	  List<Trained_In> findByPhysician(@Param("physicianId") Integer physicianId);
	  List<Trained_In> findByTreatmentCode(Integer treatmentcode);
	  List<Trained_In> findDistinctByTreatmentIsNotNull();
	  @Query(value="SELECT * FROM trained_in WHERE certificationExpires BETWEEN CURDATE() AND CURDATE() + INTERVAL 30 DAY AND Physician= :phyid;",nativeQuery=true)
	  List<Trained_In> findProceduresWithExpiringCertifications(@Param("phyid") Integer physicianId );
	
	  Optional<Trained_In> findById(TrainedInId id);
		@Query(
				value = "select * from trained_in where physician = :phyId and Treatment = :procId;",
				nativeQuery = true
		)
		public Trained_In findByPhysicianAndTreatment(@Param("phyId")int phyId,@Param("procId") int procId);
}
