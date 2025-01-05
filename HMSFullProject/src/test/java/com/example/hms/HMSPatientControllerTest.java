package com.example.hms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.hms.controller.HMSPatientController;
import com.example.hms.model.Patient;
import com.example.hms.model.Physician;
import com.example.hms.repository.PhysicianRepository;
import com.example.hms.service.HMSPatientService;



class HMSPatientControllerTest {

    @InjectMocks
    private HMSPatientController controller;

    @Mock
    private HMSPatientService patientService;

    @Mock
    private PhysicianRepository physicianRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /** Test for addPatient */
    @Test
    void testAddPatient() {
        // Mock Patient and Physician
        Patient mockPatient = new Patient();
        Physician mockPhysician = new Physician();
        mockPhysician.setEmployeeID(1);
        mockPatient.setPcp(mockPhysician);

        // Mock repository behavior
        when(physicianRepository.findById(mockPhysician.getEmployeeID())).thenReturn(java.util.Optional.of(mockPhysician));

        // Call the controller method
        ResponseEntity<String> response = controller.addPatient(mockPatient);

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Record Created Successfully", response.getBody());

        // Verify interactions
        verify(physicianRepository).findById(mockPhysician.getEmployeeID());
        verify(patientService).addPatient(mockPatient);
    }


    @Test
    void testAddPatient_PhysicianNotFound() {
        Patient mockPatient = new Patient();
        Physician mockPhysician = new Physician();
        mockPhysician.setEmployeeID(1);

        mockPatient.setPcp(mockPhysician);

        when(physicianRepository.findById(mockPhysician.getEmployeeID())).thenReturn(java.util.Optional.empty());

        ResponseEntity<String> response = controller.addPatient(mockPatient);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error: Physician not found", response.getBody());
        verify(physicianRepository).findById(mockPhysician.getEmployeeID());
        verify(patientService, never()).addPatient(mockPatient);
    }

    /** Test for updateAddress */
    @Test
    void testUpdateAddress() {
        int ssn = 123;
        String newAddress = "123 Main St";

        when(patientService.updateAddress(ssn, newAddress)).thenReturn(true);

        ResponseEntity<String> response = controller.updateAddress(ssn, Map.of("address", newAddress));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Address updated successfully", response.getBody());
        verify(patientService).updateAddress(ssn, newAddress);
    }

    @Test
    void testUpdateAddress_NotFound() {
        int ssn = 123;
        String newAddress = "123 Main St";

        when(patientService.updateAddress(ssn, newAddress)).thenReturn(false);

        ResponseEntity<String> response = controller.updateAddress(ssn, Map.of("address", newAddress));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(patientService).updateAddress(ssn, newAddress);
    }

    /** Test for updatePhone */
    @Test
    void testUpdatePhone() {
        int ssn = 123;
        String newPhone = "9876543210";

        when(patientService.updatePhone(ssn, newPhone)).thenReturn("Phone number updated successfully");

        ResponseEntity<String> response = controller.updatePhone(ssn, Map.of("phone", newPhone));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Phone number updated successfully", response.getBody());
        verify(patientService).updatePhone(ssn, newPhone);
    }
}
