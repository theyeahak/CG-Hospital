package com.example.hms;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.example.hms.exception.PatientNotFoundException;
import com.example.hms.exception.RoomNotFoundException;
import com.example.hms.exception.NoAppointmentFoundForPhysicianId;
import com.example.hms.model.Appointment;
import com.example.hms.model.Nurse;
import com.example.hms.model.Patient;
import com.example.hms.model.Physician;
import com.example.hms.model.Room;
import com.example.hms.repository.NurseRepository;
import com.example.hms.repository.PatientRepository;
import com.example.hms.repository.RoomRepository;
import com.example.hms.repository.AppointmentRepository;
import com.example.hms.serviceImpl.HMSAppointmentServiceImpl;
 
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HMSAppointmentServiceImplTest {
 
    @Autowired
    private HMSAppointmentServiceImpl service;
 
    @Autowired
    private TestRestTemplate restTemplate;
 
    // Mock data
    private Appointment appointment;
    private Physician physician;
    private Patient patient;
    private Nurse nurse;
    private Room room;
    @Autowired
    private NurseRepository nurseRepository;
 
    @Autowired
    private PatientRepository patientRepository;
 
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private RoomRepository roomRepo;
 
 
    @BeforeEach
    public void setUp() {
        physician = new Physician();
        physician.setEmployeeID(101);
 
        patient = new Patient();
        patient.setSsn(201);
        nurse = new Nurse();
        nurse.setEmployeeID(301);
 
        room = new Room();
        room.setRoomNumber(401);
 
        appointment = new Appointment();
        appointment.setAppointmentID(501);
        appointment.setPhysician(physician);
        appointment.setPatient(patient);
        appointment.setPrepNurse(nurse);
        appointment.setStart(LocalDateTime.now());
        appointment.setExaminationRoom("ER101");
    }

    @Test
    public void testGetPatientCheckedByPhysician_NotFound() {
        assertThrows(NoAppointmentFoundForPhysicianId.class, () -> {
            service.getPatientCheckedByPhysician(999);
        });
    }
 
    @Test
    public void testGetPatientByPatientIdAndPhysicianId_NotFound() {
        assertNull(service.getPatientByPatientIdAndPhysicianId(999, patient.getSsn()));
    }

    // Test getExaminationRoomByPatientIdAndDate
    @Test
    public void testGetExaminationRoomByPatientIdAndDate() {
        String room = service.getExaminationRoomByPatientIdAndDate(patient.getSsn(), LocalDate.now());
        assertNotNull(room);
    }
 
    @Test
    public void testGetExaminationRoomByPatientIdAndDate_NotFound() {
        String room = service.getExaminationRoomByPatientIdAndDate(999, LocalDate.now());
        assertEquals("No appointment found for the given date.", room);
    }
 

    @Test
    public void testGetPatientByPatientIdAndNurseId_NotFound() {
        assertThrows(PatientNotFoundException.class, () -> {
            service.getPatientByPatientIdAndNurseId(999, 999);
        });
    }

 
    @Test
    public void testGetRoomList_NotFound() {
        assertThrows(RoomNotFoundException.class, () -> {
            service.getRoomList(999, LocalDateTime.now());
        });
    }

 
    @Test
    public void testGetPatientsByNurseAndDate_NotFound() {
        assertThrows(PatientNotFoundException.class, () -> {
            service.getPatientsBynurseandDate(999, LocalDate.now());
        });
    }
}
