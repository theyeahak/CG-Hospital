package com.example.hms.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import com.example.hms.exception.AlreadyFoundException;
import com.example.hms.exception.ExaminationRoomNotFoundException;
import com.example.hms.exception.FieldsNotFoundException;
import com.example.hms.exception.NoAppointmentFoundForId;
import com.example.hms.exception.NoNurseFoundByPatientIdException;
import com.example.hms.exception.NurseNotFoundException;
import com.example.hms.model.Nurse;
import com.example.hms.model.Physician;
import com.example.hms.model.Appointment;
import com.example.hms.model.Patient;
import com.example.hms.model.Room;

public interface HMSAppointmentService {
    
	//ranjjan
			public List<Appointment> getAllAppointments();
			public void postApp(Appointment a) throws AlreadyFoundException, FieldsNotFoundException;
			public List<Appointment> getAppointmentsByStartDate(LocalDateTime startDate);
			public Patient getPatientByAppointment(int appointmentid) throws NoAppointmentFoundForId;
			public Physician getPhysicianByAppointment(int appointmentid);
			public Nurse getNurseByAppointmentId(Integer appointmentId) throws  NurseNotFoundException;
			public String getExamintionRoomByappId(int id) throws ExaminationRoomNotFoundException ;
			//ranjjan
			List<Physician> getPhysiciansByPatientId(int patientId);
			public List<Physician> getPhysicianByPatientIdAndDate(String patientId, LocalDateTime date);
			public List<Nurse> getNursesByPatientId(int patientId)throws NoNurseFoundByPatientIdException;
		    public List<Nurse> getNursesByPatientIdAndDate(String patientId, LocalDateTime date);
	
	public List<LocalDateTime> getAppointmentDatesByPatientId(int patientId);
    public HashSet<Patient> getPatientCheckedByPhysician(int physicianid);
    public HashSet<Patient> getPatientCheckedByPhysicianOnDate(int physicianid, LocalDate date);
    public Patient getPatientByPatientIdAndPhysicianId(int physicianid, int patientid);
    public HashSet<Patient> getPateientsByNusreId(int nurseid);
    public Patient getPatientByPatientIdAndNurseId(int nurseid, int patientid);
    public String getExaminationRoomByPatientIdAndDate(int patientId, LocalDate date);
    public List<Room> getRoomByPhysicianIdOnDate(int physicianid, LocalDateTime date);
    public List<Room> getRoomList(int nurseId, LocalDateTime date);
    public Appointment updateExaminationRoom(Integer appointmentid, String newExaminationRoom);
	public HashSet<Patient> getPatientsBynurseandDate(int nurseid,LocalDate starto);
	
}
	
	
	
