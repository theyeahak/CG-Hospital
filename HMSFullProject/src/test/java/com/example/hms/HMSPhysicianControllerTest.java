package com.example.hms;


	import com.example.hms.controller.HMSPhysicianController;
import com.example.hms.exception.PositionNotFoundForPosition;
	import com.example.hms.model.Physician;
	import com.example.hms.service.HMSPhysicianService;
	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;
	import org.mockito.InjectMocks;
	import org.mockito.Mock;
	import org.mockito.MockitoAnnotations;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;

	import java.util.Arrays;
	import java.util.List;
	import java.util.Map;

	import static org.mockito.Mockito.*;
	import static org.junit.jupiter.api.Assertions.*;

	public class HMSPhysicianControllerTest {

	    @InjectMocks
	    private HMSPhysicianController physicianController;

	    @Mock
	    private HMSPhysicianService phyService;

	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testGetById() {
	        // Arrange
	        Physician physician = new Physician(1, "Dr. John", "Cardiologist", null, null, null, null, null, null, null, null);
	        when(phyService.getPhyById(1)).thenReturn(physician);

	        // Act
	        ResponseEntity<Physician> response = physicianController.getById(1);

	        // Assert
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("Dr. John", response.getBody().getName());
	        verify(phyService, times(1)).getPhyById(1);
	    }

	    @Test
	    public void testGetByName() {
	        // Arrange
	        Physician physician = new Physician(1, "Dr. John", "Cardiologist", null, null, null, null, null, null, null, null);
	        when(phyService.getPhyByName("Dr. John")).thenReturn(physician);

	        // Act
	        ResponseEntity<Physician> response = physicianController.getByName("Dr. John");

	        // Assert
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("Dr. John", response.getBody().getName());
	        verify(phyService, times(1)).getPhyByName("Dr. John");
	    }

	    @Test
	    public void testGetByPosition() {
	        // Arrange
	        Physician physician1 = new Physician(1, "Dr. John", "Cardiologist", null, null, null, null, null, null, null, null);
	        Physician physician2 = new Physician(2, "Dr. Jane", "Cardiologist", null, null, null, null, null, null, null, null);
	        List<Physician> physicians = Arrays.asList(physician1, physician2);

	        when(phyService.getPhyByPos("Cardiologist")).thenReturn(physicians);

	        // Act
	        ResponseEntity<List<Physician>> response = physicianController.getByPosition("Cardiologist");

	        // Assert
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(2, response.getBody().size());
	        verify(phyService, times(1)).getPhyByPos("Cardiologist");
	    }

	    @Test
	    public void testGetByPositionNotFound() {
	        // Arrange
	        when(phyService.getPhyByPos("Neurologist")).thenReturn(List.of());

	        // Act & Assert
	        Exception exception = assertThrows(PositionNotFoundForPosition.class, () -> {
	            physicianController.getByPosition("Neurologist");
	        });
	        assertEquals("Neurologist NOt Found!", exception.getMessage());
	        verify(phyService, times(1)).getPhyByPos("Neurologist");
	    }

	    @Test
	    public void testPostPhysician() {
	        // Arrange
	        Physician physician = new Physician(1, "Dr. John", "Cardiologist", null, null, null, null, null, null, null, null);
	        doNothing().when(phyService).addPhysician(physician);

	        // Act
	        ResponseEntity<String> response = physicianController.postPhysician(physician);

	        // Assert
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("Record Created Successfully", response.getBody());
	        verify(phyService, times(1)).addPhysician(physician);
	    }

	    

	    @Test
	    public void testUpdatePhysicianPositionBadRequest() {
	        // Arrange
	        Map<String, String> requestBody = Map.of("newPosition", "");

	        // Act
	        ResponseEntity<Physician> response = physicianController.updatePhysicianPosition(1, requestBody);

	        // Assert
	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        verify(phyService, times(0)).updatePosition(anyString(), anyInt());
	    }

	    @Test
	    public void testUpdatePhysicianPositionNotFound() {
	        // Arrange
	        Map<String, String> requestBody = Map.of("newPosition", "Surgeon");
	        when(phyService.updatePosition("Surgeon", 999)).thenReturn(null);

	        // Act
	        ResponseEntity<Physician> response = physicianController.updatePhysicianPosition(999, requestBody);

	        // Assert
	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	        verify(phyService, times(1)).updatePosition("Surgeon", 999);
	    }


	    @Test
	    public void testUpdatePhysicianNameBadRequest() {
	        // Arrange
	        Map<String, String> requestBody = Map.of("newName", "");

	        // Act
	        ResponseEntity<Physician> response = physicianController.updatePhysicianName(1, requestBody);

	        // Assert
	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        verify(phyService, times(0)).updateName(anyInt(), anyString());
	    }

	    @Test
	    public void testUpdatePhysicianNameNotFound() {
	        // Arrange
	        Map<String, String> requestBody = Map.of("newName", "Dr. Smith");
	        when(phyService.updateName(1, "Dr. Smith")).thenReturn(null);

	        // Act
	        ResponseEntity<Physician> response = physicianController.updatePhysicianName(1, requestBody);

	        // Assert
	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	        verify(phyService, times(1)).updateName(1, "Dr. Smith");
	    }

	}
