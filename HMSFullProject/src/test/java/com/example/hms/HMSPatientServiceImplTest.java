package com.example.hms;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.hms.model.Patient;
import com.example.hms.model.Physician;
import com.example.hms.repository.PatientRepository;
import com.example.hms.service.HMSPatientService;


@SpringBootTest
@Transactional
public class HMSPatientServiceImplTest {

    @Autowired
    private HMSPatientService patientService;
    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    public void setUp() {
        // Setup initial data for testing if needed
    }

    @Test
    public void testGetAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        assertNotNull(patients);
        assertTrue(patients.size() > 0);
    }

    @Test
    public void testGetPatientsByPhysicianId() {
        List<Patient> patients = patientService.getPatientsByPhysicianId(1);
        assertNotNull(patients);
        assertTrue(patients.size() > 0);
    }

    @Test
    public void testGetPatientByPhysicianIdAndPatientId() {
        Patient patient = patientService.getPatientByPhysicianIdAndPatientId(1, 100000001);
        assertNotNull(patient);
        assertEquals(100000001, patient.getSsn());
    }

    @Test
    public void testGetPatientInsuranceByPatientId() {
        int insuranceId = patientService.getPatientInsuranceByPatientId(100000001);
        assertEquals(68476213, insuranceId);
    }

    @Test
    public void testUpdateAddress() {
        boolean isUpdated = patientService.updateAddress(100000001, "456 Elm St");
        assertTrue(isUpdated);
    }

    @Test
    public void testUpdatePhone() {
        String response = patientService.updatePhone(100000001, "555-5678");
        assertEquals("Phone updated successfully", response);
    }

    @Test
    public void testGetPhysiciansByPatientId() {
        List<Physician> physicians = patientService.getPhysiciansByPatientId(100000001);
        assertNotNull(physicians);
        assertTrue(physicians.size() > 0);
    }

    @Test
    public void testGetPatientByNonExistentId() {
        Patient patient = patientService.getPatientByPhysicianIdAndPatientId(1, 999999999);
        assertNull(patient);
    }
    @Test
    void testAddPatient() {
        // Arrange
        Patient mockPatient = new Patient();
        mockPatient.setSsn(123);
        mockPatient.setName("John Doe");
        mockPatient.setAddress("123 Main St");
        mockPatient.setPhone("555-1234");
        mockPatient.setInsuranceID(456);

        // Act
        Patient result = patientService.addPatient(mockPatient);

        // Assert
        assertNotNull(result);
        assertEquals(mockPatient.getSsn(), result.getSsn());
        assertEquals(mockPatient.getName(), result.getName());
        assertEquals(mockPatient.getAddress(), result.getAddress());
        assertEquals(mockPatient.getPhone(), result.getPhone());
        assertEquals(mockPatient.getInsuranceID(), result.getInsuranceID());

        // Verify that the patient is saved in the repository
        Patient savedPatient = patientRepository.findById(mockPatient.getSsn()).orElse(null);
        assertNotNull(savedPatient);
        assertEquals(mockPatient.getName(), savedPatient.getName());
    }
}