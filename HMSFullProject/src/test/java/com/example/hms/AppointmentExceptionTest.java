package com.example.hms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.hms.exception.*;

public class AppointmentExceptionTest {

    @Test
    public void testAlreadyFoundException() {
        String message = "Appointment already exists";
        AlreadyFoundException exception = new AlreadyFoundException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testExaminationRoomNotFoundException() {
        String message = "No examination room found";
        ExaminationRoomNotFoundException exception = new ExaminationRoomNotFoundException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testFieldsNotFoundException() {
        String message = "Required fields are missing";
        FieldsNotFoundException exception = new FieldsNotFoundException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testNoAppointmentFoundForId() {
        String message = "No appointment found for this ID";
        NoAppointmentFoundForId exception = new NoAppointmentFoundForId(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testNoAppointmentFoundForPhysicianId() {
        String message = "No appointment found for this physician ID";
        NoAppointmentFoundForPhysicianId exception = new NoAppointmentFoundForPhysicianId(message);
        assertEquals(message, exception.getMessage());
    }
    
    @Test
    public void testNurseNotFoundException() {
        String message = " Nurse Not found ";
        NoNurseFoundByPatientIdException exception = new NoNurseFoundByPatientIdException(message);
        assertEquals(message, exception.getMessage());
    }
    
    
    

    @Test
    public void testNoNurseFoundByPatientIdException() {
        String message = "No nurse found for this patient ID";
        NoNurseFoundByPatientIdException exception = new NoNurseFoundByPatientIdException(message);
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testPatientIdNotFoundException() {
        String message = "Patient ID not found";
        PatientIdNotFoundException exception = new PatientIdNotFoundException(message);
        assertEquals(message, exception.getMessage());
    }


    @Test
    public void testProcedureIdNotFoundException() {
        String message = "Procedure ID not found";
        ProcedureIdNotFoundException exception = new ProcedureIdNotFoundException(message);
        assertEquals(message, exception.getMessage());
    }


}