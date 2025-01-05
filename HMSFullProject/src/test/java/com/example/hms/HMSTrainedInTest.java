package com.example.hms;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.hms.exception.NoProceduresFoundException;
import com.example.hms.exception.NoTrainedInsFoundException;
import com.example.hms.exception.PhysicianIdNotFoundException;
import com.example.hms.exception.TrainedInWithThisPhysicianIdNotFoundException;
import com.example.hms.model.Physician;
import com.example.hms.model.Procedures;
import com.example.hms.model.TrainedInId;
import com.example.hms.model.Trained_In;
import com.example.hms.repository.ProceduresRepository;
import com.example.hms.repository.Trained_In_Repository;
import com.example.hms.serviceImpl.HMSTrainedInServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
 
@SpringBootTest
public class HMSTrainedInTest {
 
    @Autowired
    private HMSTrainedInServiceImpl trainedInService;
 
    @Autowired
    private ProceduresRepository proceduresRepository;
 
    @Autowired
    private Trained_In_Repository trainedInRepository;
 
    private Physician physician;
    private Procedures procedure;
    

    @BeforeEach
    public void setUp() {
        // Initialize physician
        physician = new Physician(1, "John Dorian", "Staff Internist", 111111111);

        // Initialize procedure
        procedure = new Procedures(1, "Reverse Rhinopodoplasty", 1500.0);

        // Save procedure to repository
        proceduresRepository.save(procedure);

        // Save Trained_In to repository
        trainedInRepository.save(new Trained_In(
            new TrainedInId(physician.getEmployeeID(), procedure.getCode()),
            physician,
            procedure,
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(10)
        ));
    }
    
    @Test
    public void testGetProceduresWithExpiringCertifications() {
        List<Procedures> procedures = trainedInService.getProceduresWithExpiringCertifications(physician.getEmployeeID());
        System.out.println("Procedures: " + procedures); // Debugging line
        assertNotNull(procedures);
        assertFalse(procedures.isEmpty());
    }
    @Test
    @Transactional // Keep the session open for the duration of this test
    public void testGetTreatmentsByPhysician() {
        List<Procedures> treatments = trainedInService.getTreatmentsByPhysician(physician.getEmployeeID());
        assertNotNull(treatments);
        assertFalse(treatments.isEmpty());
        assertEquals(procedure.getName(), treatments.get(0).getName());
    }
    
 
    @Test
    public void testGetPhysicianByTreatment() {
        List<Physician> physicians = trainedInService.getPhysicianByTreatment(procedure.getCode());
        assertNotNull(physicians);
        assertFalse(physicians.isEmpty());
        assertEquals(physician.getEmployeeID(), physicians.get(0).getEmployeeID());
    }
 
    
    /*@Test
    public void testGetCertifiedProcedures() {
        List<Procedures> certifiedProcedures = trainedInService.getCertifiedProcedures();
        assertNotNull(certifiedProcedures);
        assertFalse(certifiedProcedures.isEmpty());
        assertEquals(procedure.getName(), certifiedProcedures.get(0).getName());
    }*/

 
    @Test
    public void testSaveTrainedIn() {
        Trained_In trainedIn = new Trained_In(new TrainedInId(physician.getEmployeeID(), procedure.getCode()), physician, procedure, LocalDateTime.now(), LocalDateTime.now().plusYears(1));
        Trained_In savedTrainedIn = trainedInService.saveTrainedIn(trainedIn);
        assertNotNull(savedTrainedIn);
    }
 
    @Test
    public void testFindAll() {
        List<Trained_In> trainedIns = trainedInService.findAll();
        assertNotNull(trainedIns);
        assertFalse(trainedIns.isEmpty());
    }

 
    @Test
    public void testUpdateCertificationExpiry() {
        LocalDateTime newExpiryDate = LocalDateTime.now().plusMonths(6);
        Boolean result = trainedInService.updateCertificationExpiry(physician.getEmployeeID(), procedure.getCode(), newExpiryDate);
        assertTrue(result);
    }
 
    @Test
    public void testGetTreatmentsByPhysicianNotFound() {
        assertThrows(TrainedInWithThisPhysicianIdNotFoundException.class, () -> {
            trainedInService.getTreatmentsByPhysician(999); // Non-existing physician ID
        });
    }
 
    @Test
    public void testGetPhysicianByTreatmentNotFound() {
        assertThrows(PhysicianIdNotFoundException.class, () -> {
            trainedInService.getPhysicianByTreatment(999); 
        });
    }
 
    
    @Test
    public void testGetCertifiedProceduresNotFound() {
        // Clear the trained_in repository to ensure no procedures exist
        trainedInRepository.deleteAll(); // Ensure this line is added to clear existing data

        // Now, when we call getCertifiedProcedures, it should throw the exception
        assertThrows(NoProceduresFoundException.class, () -> {
            trainedInService.getCertifiedProcedures();
        });
    }
    @Test
    public void testGetTreatmentsByNonExistingPhysician() {
        assertThrows(TrainedInWithThisPhysicianIdNotFoundException.class, () -> {
            trainedInService.getTreatmentsByPhysician(999); // Non-existing physician ID
        });
    }
    @Test
    public void testGetPhysicianByNonExistingTreatment() {
        assertThrows(PhysicianIdNotFoundException.class, () -> {
            trainedInService.getPhysicianByTreatment(999); // Non-existing treatment code
        });
    }
    @Test
    public void testNoTrainedInsFoundExceptionMessage() {
        // Arrange
        String expectedMessage = "No trained ins found";
        
        // Act
        NoTrainedInsFoundException exception = new NoTrainedInsFoundException(expectedMessage);
        
        // Assert
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testNoTrainedInsFoundExceptionWithoutMessage() {
        // Act
        NoTrainedInsFoundException exception = new NoTrainedInsFoundException();
        
        // Assert
        assertNotNull(exception);
        assertNull(exception.getMessage()); // Default message should be null
    }
    /*@Test
    public void testGetCertifiedProcedures() {
        // Act
        List<Procedures> certifiedProcedures = trainedInService.getCertifiedProcedures();

        // Assert
        assertNotNull(certifiedProcedures);
        assertFalse(certifiedProcedures.isEmpty());
        assertEquals(1, certifiedProcedures.size());
        assertEquals(procedure.getName(), certifiedProcedures.get(0).getName());
    }*/

    @Test
    public void testGetCertifiedProceduresNoProceduresFound() {
        // Clear the trained_in repository to ensure no procedures exist
        trainedInRepository.deleteAll(); // Ensure this line is added to clear existing data

        // Act & Assert
        assertThrows(NoProceduresFoundException.class, () -> {
            trainedInService.getCertifiedProcedures();
        });
    }
}

