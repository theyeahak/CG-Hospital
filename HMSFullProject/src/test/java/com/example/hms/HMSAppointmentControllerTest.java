package com.example.hms;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.hms.controller.HMSAppointmentController;
import com.example.hms.model.Appointment;
import com.example.hms.model.Patient;
import com.example.hms.model.Room;
import com.example.hms.service.HMSAppointmentService;


class HMSAppointmentControllerTest {

	@InjectMocks
    private HMSAppointmentController controller;

    @Mock
    private HMSAppointmentService appService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAppointmentDates() {
    	int patientId = 1;
    	List<LocalDateTime> mockDates = Arrays.asList(LocalDateTime.now(),LocalDateTime.now().plusDays(1));
    	when(appService.getAppointmentDatesByPatientId(patientId)).thenReturn(mockDates);
    	ResponseEntity<List<LocalDateTime>> response = controller.getAppointmentDates(patientId);
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals(mockDates, response.getBody());
    	verify(appService).getAppointmentDatesByPatientId(patientId);
    }
    
    
    
    
    
    
    
    

    @Test
    void testGetListOfPatientCheckedByPhysician() {
        int physicianId = 1;
        HashSet<Patient> mockPatients = new HashSet<>(Arrays.asList(new Patient(), new Patient()));

        when(appService.getPatientCheckedByPhysician(physicianId)).thenReturn(mockPatients);

        ResponseEntity<HashSet<Patient>> response = controller.getListOfPatientCheckedByPhysician(physicianId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPatients, response.getBody());
        verify(appService).getPatientCheckedByPhysician(physicianId);
    }

    @Test
    void testGetListOfPatientCheckedPhysicianOnDate() {
        int physicianId = 1;
        LocalDate date = LocalDate.now();
        HashSet<Patient> mockPatients = new HashSet<>(Arrays.asList(new Patient(), new Patient()));

        when(appService.getPatientCheckedByPhysicianOnDate(physicianId, date)).thenReturn(mockPatients);

        ResponseEntity<HashSet<Patient>> response = controller.getListOfPatientCheckedPhysicianOnDate(physicianId, date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPatients, response.getBody());
        verify(appService).getPatientCheckedByPhysicianOnDate(physicianId, date);
    }

    @Test
    void testGetPatientByPhysicianIdAndAppointmentId() {
        int physicianId = 1;
        int patientId = 2;
        Patient mockPatient = new Patient();

        when(appService.getPatientByPatientIdAndPhysicianId(physicianId, patientId)).thenReturn(mockPatient);

        ResponseEntity<Patient> response = controller.getPatientByPhysicianIdAndAppointmentId(physicianId, patientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPatient, response.getBody());
        verify(appService).getPatientByPatientIdAndPhysicianId(physicianId, patientId);
    }

    @Test
    void testGetPatientsByNurse() {
        int nurseId = 1;
        HashSet<Patient> mockPatients = new HashSet<>(Arrays.asList(new Patient(), new Patient()));

        when(appService.getPateientsByNusreId(nurseId)).thenReturn(mockPatients);

        ResponseEntity<HashSet<Patient>> response = controller.getPatientsByNurse(nurseId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPatients, response.getBody());
        verify(appService).getPateientsByNusreId(nurseId);
    }

    @Test
    void testGetPatientByNurseIdAndAppointmentID() {
        int nurseId = 1;
        int patientId = 2;
        Patient mockPatient = new Patient();

        when(appService.getPatientByPatientIdAndNurseId(nurseId, patientId)).thenReturn(mockPatient);

        ResponseEntity<Patient> response = controller.getPatientByNurseIdAndAppointmentID(nurseId, patientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPatient, response.getBody());
        verify(appService).getPatientByPatientIdAndNurseId(nurseId, patientId);
    }

    @Test
    void testGetAllPatientsByNurseAndDate() {
        int nurseId = 1;
        LocalDate date = LocalDate.now();
        HashSet<Patient> mockPatients = new HashSet<>(Arrays.asList(new Patient(), new Patient()));

        when(appService.getPatientsBynurseandDate(nurseId, date)).thenReturn(mockPatients);

        ResponseEntity<HashSet<Patient>> response = controller.getAllPatientsByNurseAndDate(nurseId, date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPatients, response.getBody());
        verify(appService).getPatientsBynurseandDate(nurseId, date);
    }

    @Test
    void testGetExaminationRoom() {
        int patientId = 1;
        String date = "2024-01-01T10:00:00";
        String mockRoom = "Room 101";

        when(appService.getExaminationRoomByPatientIdAndDate(patientId, LocalDate.parse("2024-01-01"))).thenReturn(mockRoom);

        ResponseEntity<String> response = controller.getExaminationRoom(patientId, date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRoom, response.getBody());
        verify(appService).getExaminationRoomByPatientIdAndDate(patientId, LocalDate.parse("2024-01-01"));
    }

    @Test
    void testGetRoomByPhysicianIdOnDate() {
        int physicianId = 1;
        LocalDateTime date = LocalDateTime.now();
        List<Room> mockRooms = Arrays.asList(new Room(), new Room());

        when(appService.getRoomByPhysicianIdOnDate(physicianId, date)).thenReturn(mockRooms);

        ResponseEntity<List<Room>> response = controller.getRoomByPhysicianIdOnDate(physicianId, date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRooms, response.getBody());
        verify(appService).getRoomByPhysicianIdOnDate(physicianId, date);
    }

    @Test
    void testGetAllRoomDetails() {
        int nurseId = 1;
        LocalDateTime date = LocalDateTime.now();
        List<Room> mockRooms = Arrays.asList(new Room(), new Room());

        when(appService.getRoomList(nurseId, date)).thenReturn(mockRooms);

        ResponseEntity<List<Room>> response = controller.getAllRoomDetails(nurseId, date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRooms, response.getBody());
        verify(appService).getRoomList(nurseId, date);
    }

    @Test
    void testUpdateRoomByAppointmentId() {
        int appointmentId = 1;
        Map<String, String> reqBody = Map.of("newExaminationRoom", "Room 101");
        Appointment mockAppointment = new Appointment();

        when(appService.updateExaminationRoom(appointmentId, "Room 101")).thenReturn(mockAppointment);

        ResponseEntity<Appointment> response = controller.updateRoomByAppointmentId(appointmentId, reqBody);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockAppointment, response.getBody());
        verify(appService).updateExaminationRoom(appointmentId, "Room 101");
    }
    @Test
    void testGetExaminationRoom_ValidDate() {
        int patientId = 1;
        String date = "2024-01-01T10:00:00";
        String expectedRoom = "Room 101";

        // Mock the service method
        when(appService.getExaminationRoomByPatientIdAndDate(patientId, LocalDate.parse("2024-01-01"))).thenReturn(expectedRoom);

        // Call the controller method
        ResponseEntity<String> response = controller.getExaminationRoom(patientId, date);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedRoom, response.getBody());
        verify(appService).getExaminationRoomByPatientIdAndDate(patientId, LocalDate.parse("2024-01-01"));
    }

    @Test
    void testGetExaminationRoom_InvalidDateFormat() {
        int patientId = 1;
        String invalidDate = "invalid-date-format";

        // Call the controller method
        ResponseEntity<String> response = controller.getExaminationRoom(patientId, invalidDate);

        // Assertions
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Invalid date format"));
        verify(appService, never()).getExaminationRoomByPatientIdAndDate(anyInt(), any());
    }

    @Test
    void testGetExaminationRoom_ServiceError() {
        int patientId = 1;
        String date = "2024-01-01T10:00:00";

        // Mock the service method to throw an exception
        when(appService.getExaminationRoomByPatientIdAndDate(patientId, LocalDate.parse("2024-01-01")))
                .thenThrow(new RuntimeException("Service error"));

        // Call the controller method
        ResponseEntity<String> response = controller.getExaminationRoom(patientId, date);

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Error fetching data"));
        verify(appService).getExaminationRoomByPatientIdAndDate(patientId, LocalDate.parse("2024-01-01"));
    }
}
