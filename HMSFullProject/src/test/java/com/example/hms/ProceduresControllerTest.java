package com.example.hms;

	import com.example.hms.controller.HMSProceduresController;
	import com.example.hms.model.Procedures;
	import com.example.hms.service.HMSProceduresService;
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

	public class ProceduresControllerTest {

	    @InjectMocks
	    private HMSProceduresController proceduresController;

	    @Mock
	    private HMSProceduresService proService;

	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testGetAllProcedures() {
	        // Arrange
	        Procedures procedure1 = new Procedures(1, "Surgery", 5000.00);
	        Procedures procedure2 = new Procedures(2, "X-ray", 300.00);
	        List<Procedures> proceduresList = Arrays.asList(procedure1, procedure2);
	        when(proService.getAllProcedures()).thenReturn(proceduresList);

	        // Act
	        ResponseEntity<List<Procedures>> response = proceduresController.getAllProcedures();

	        // Assert
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(2, response.getBody().size());
	        verify(proService, times(1)).getAllProcedures();
	    }

	    @Test
	    public void testGetProcedureById() {
	        // Arrange
	        Procedures procedure = new Procedures(1, "Surgery", 5000.00);
	        when(proService.getProceduresById(1)).thenReturn(procedure);

	        // Act
	        ResponseEntity<Procedures> response = proceduresController.getProcedureById(1);

	        // Assert
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("Surgery", response.getBody().getName());
	        verify(proService, times(1)).getProceduresById(1);
	    }

	    @Test
	    public void testGetProceduresByName() {
	        // Arrange
	        Procedures procedure = new Procedures(1, "Surgery", 5000.00);
	        when(proService.getProcedureByName("Surgery")).thenReturn(procedure);

	        // Act
	        ResponseEntity<Procedures> response = proceduresController.getProceduresByName("Surgery");

	        // Assert
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("Surgery", response.getBody().getName());
	        verify(proService, times(1)).getProcedureByName("Surgery");
	    }

	}
