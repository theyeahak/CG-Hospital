package com.example.hms.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.hms.model.Room;


@Repository
public interface RoomRepository extends JpaRepository<Room, Integer>{
	@Query(value = "SELECT *FROM room WHERE (BlockCode, BlockFloor) IN (SELECT BlockCode, BlockFloor FROM on_call WHERE Nurse IN (\r\n"

			+ "SELECT PrepNurse FROM appointment WHERE physician = :phyId AND Starto = :date));", nativeQuery = true)

	public List<Room> getRoomByPhyIdOnDate(@Param("phyId") int phyId, @Param("date") LocalDateTime date);
	@Query(value = "SELECT *FROM room WHERE (BlockCode, BlockFloor) IN (SELECT BlockCode, BlockFloor FROM on_call WHERE Nurse IN (\r\n"

			+ "SELECT PrepNurse FROM appointment WHERE PrepNurse = :nurseId AND Starto = :date));", nativeQuery = true)

	public List<Room> getRoomByNurseIdOnDate(@Param("nurseId") int nurseId, @Param("date") LocalDateTime date);
 
}
