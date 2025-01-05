package com.example.hms;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.hms.controller.HMSTrainedInController;
import com.example.hms.exception.NoProceduresFoundException;
import com.example.hms.exception.PhysicianIdNotFoundException;
import com.example.hms.model.Physician;
import com.example.hms.model.Procedures;
import com.example.hms.model.Trained_In;
import com.example.hms.service.HMSTrainedInService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class HMSTrainedInMockito {

    @InjectMocks
    private HMSTrainedInController trainedInController;

    @Mock
    private HMSTrainedInService trainedInService;

    private Physician physician;
    private Procedures procedure;
    private Trained_In trainedIn;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        physician = new Physician(1, "Dr. Smith", "Cardiologist", 123456789);
        procedure = new Procedures(1, "Reverse Rhinopodoplasty", 1500.0);
        trainedIn = new Trained_In(null, physician, procedure, LocalDateTime.now(), LocalDateTime.now().plusYears(1));
    }

    @Test
    public void testGetTreatmentsByPhysician() {
        List<Procedures> procedures = new ArrayList<>();
        procedures.add(procedure);
        when(trainedInService.getTreatmentsByPhysician(anyInt())).thenReturn(procedures);

        ResponseEntity<List<Procedures>> response = trainedInController.getTreatmentsByPhysician(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(procedures, response.getBody());
        verify(trainedInService, times(1)).getTreatmentsByPhysician(anyInt());
    }

    @Test
    public void testGetPhysiciansByTreatment() {
        List<Physician> physicians = new ArrayList<>();
        physicians.add(physician);
        when(trainedInService.getPhysicianByTreatment(anyInt())).thenReturn(physicians);

        ResponseEntity<List<Physician>> response = trainedInController.getPhysiciansByTreatment(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(physicians, response.getBody());
        verify(trainedInService, times(1)).getPhysicianByTreatment(anyInt());
    }

    @Test
    public void testGetCertifiedProcedures() {
        List<Procedures> procedures = new ArrayList<>();
        procedures.add(procedure);
        when(trainedInService.getCertifiedProcedures()).thenReturn(procedures);

        ResponseEntity<List<Procedures>> response = trainedInController.getCertifiedProcedures();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(procedures, response.getBody());
        verify(trainedInService, times(1)).getCertifiedProcedures();
    }

    @Test
    public void testGetExpiringCertificates() {
        List<Procedures> procedures = new ArrayList<>();
        procedures.add(procedure);
        when(trainedInService.getProceduresWithExpiringCertifications(anyInt())).thenReturn(procedures);

        ResponseEntity<List<Procedures>> response = trainedInController.getExpiringCertificates(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(procedures, response.getBody());
        verify(trainedInService, times(1)).getProceduresWithExpiringCertifications(anyInt());
    }

    @Test
    public void testCreateTrainedIn() {
        when(trainedInService.saveTrainedIn(any(Trained_In.class))).thenReturn(trainedIn);

        ResponseEntity<String> response = trainedInController.createTrainedIn(trainedIn);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Record Created Successfully", response.getBody());
        verify(trainedInService, times(1)).saveTrainedIn(any(Trained_In.class));
    }

    @Test
    public void testGetAll() {
        List<Trained_In> trainedIns = new ArrayList<>();
        trainedIns.add(trainedIn);
        when(trainedInService.findAll()).thenReturn(trainedIns);

        ResponseEntity<List<Trained_In>> response = trainedInController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trainedIns, response.getBody());
        verify(trainedInService, times(1)).findAll();
    }

    @Test
    public void testUpdateCertificationExpiry() {
        Map<String, LocalDateTime> reqBody = new HashMap<>();
        reqBody.put("newCertificationExpiry", LocalDateTime.now().plusYears(1));
        when(trainedInService.updateCertificationExpiry(anyInt(), anyInt(), any(LocalDateTime.class))).thenReturn(true);

        ResponseEntity<Boolean> response = trainedInController.updateCertificationExpiry(1, 1, reqBody);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
        verify(trainedInService, times(1)).updateCertificationExpiry(anyInt(), anyInt(), any(LocalDateTime.class));
    }
    @Test
    public void testCreateTrainedInSuccess() {
        when(trainedInService.saveTrainedIn(any(Trained_In.class))).thenReturn(trainedIn);

        ResponseEntity<String> response = trainedInController.createTrainedIn(trainedIn);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Record Created Successfully", response.getBody());
        verify(trainedInService, times(1)).saveTrainedIn(any(Trained_In.class));
    }

    @Test
    public void testCreateTrainedInPhysicianIdNotFound() {
        trainedIn.setPhysician(null); // Simulate missing physician

        Exception exception = assertThrows(PhysicianIdNotFoundException.class, () -> {
            trainedInController.createTrainedIn(trainedIn);
        });

        assertEquals("Physician ID is not found.", exception.getMessage());
        verify(trainedInService, never()).saveTrainedIn(any(Trained_In.class));
    }

    @Test
    public void testCreateTrainedInNoProceduresFound() {
        trainedIn.setTreatment(null); // Simulate missing treatment

        Exception exception = assertThrows(NoProceduresFoundException.class, () -> {
            trainedInController.createTrainedIn(trainedIn);
        });

        assertEquals("Treatment code is not found.", exception.getMessage());
        verify(trainedInService, never()).saveTrainedIn(any(Trained_In.class));
    }
    
}
