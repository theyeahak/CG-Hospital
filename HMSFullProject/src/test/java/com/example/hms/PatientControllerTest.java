package com.example.hms;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.hms.controller.HMSPatientController;
import com.example.hms.model.Patient;
import com.example.hms.service.HMSPatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PatientControllerTest {


	    @InjectMocks
	    private HMSPatientController patientController;

	    @Mock
	    private HMSPatientService patientService;

	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testGetAllPatients() {
	        // Arrange
	        Patient patient1 = new Patient(1, "John Doe", "12345");
	        Patient patient2 = new Patient(2, "Jane Doe", "67890");
	        List<Patient> patients = Arrays.asList(patient1, patient2);

	        when(patientService.getAllPatients()).thenReturn(patients);

	        // Act
	        ResponseEntity<List<Patient>> response = patientController.getAllPatients();

	        // Assert
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(2, response.getBody().size());
	        verify(patientService, times(1)).getAllPatients();
	    }

	    @Test
	    public void testGetPatientsByPhysicianId() {
	        // Arrange
	        Patient patient1 = new Patient(1, "John Doe", "12345");
	        Patient patient2 = new Patient(2, "Jane Doe", "67890");
	        List<Patient> patients = Arrays.asList(patient1, patient2);

	        when(patientService.getPatientsByPhysicianId(1)).thenReturn(patients);

	        // Act
	        ResponseEntity<List<Patient>> response = patientController.getPatientsByPhysicianId(1);

	        // Assert
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(2, response.getBody().size());
	        verify(patientService, times(1)).getPatientsByPhysicianId(1);
	    }

	    @Test
	    public void testGetPatientsByPhysicianId_NoContent() {
	        // Arrange
	        when(patientService.getPatientsByPhysicianId(1)).thenReturn(List.of());

	        // Act
	        ResponseEntity<List<Patient>> response = patientController.getPatientsByPhysicianId(1);

	        // Assert
	        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	        verify(patientService, times(1)).getPatientsByPhysicianId(1);
	    }

	    @Test
	    public void testGetPatientByPhysicianIdAndPatientId() {
	        // Arrange
	        Patient patient = new Patient(1, "John Doe", "12345");

	        when(patientService.getPatientByPhysicianIdAndPatientId(1, 1)).thenReturn(patient);

	        // Act
	        ResponseEntity<Patient> response = patientController.getPatientByPhysicianIdAndPatientId(1, 1);

	        // Assert
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("John Doe", response.getBody().getName());
	        verify(patientService, times(1)).getPatientByPhysicianIdAndPatientId(1, 1);
	    }

	    @Test
	    public void testGetPatientByPhysicianIdAndPatientId_NotFound() {
	        // Arrange
	        when(patientService.getPatientByPhysicianIdAndPatientId(1, 1)).thenReturn(null);

	        // Act
	        ResponseEntity<Patient> response = patientController.getPatientByPhysicianIdAndPatientId(1, 1);

	        // Assert
	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	        verify(patientService, times(1)).getPatientByPhysicianIdAndPatientId(1, 1);
	    }

	    @Test
	    public void testGetPatientInsuranceByPatientId() {
	        // Arrange
	        int insuranceId = 12345;
	        when(patientService.getPatientInsuranceByPatientId(1)).thenReturn(insuranceId);

	        // Act
	        ResponseEntity<Integer> response = patientController.getPatientInsuranceByPatientId(1);

	        // Assert
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(insuranceId, response.getBody());
	        verify(patientService, times(1)).getPatientInsuranceByPatientId(1);
	    }
	}


