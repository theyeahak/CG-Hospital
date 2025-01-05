package com.example.hms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.example.hms.controller.HMSProceduresController;
import com.example.hms.model.Procedures;
import com.example.hms.service.HMSProceduresService;
@SpringBootTest
public class HMSProceduresControllerTest
{
	@InjectMocks
    private HMSProceduresController controller;

    @Mock
    private HMSProceduresService proService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProcedureSuccess() {
        Procedures procedure = new Procedures();
        procedure.setCode(1);
        procedure.setName("Procedure1");
        procedure.setCost(100.0);

        when(proService.addProcedure(procedure)).thenReturn(procedure);

        ResponseEntity<Procedures> response = controller.addProcedure(procedure);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(procedure, response.getBody());
        //verify(proService, times(1)).addProcedure(procedure);
    }

    @Test
    void testUpdateProcedureCostSuccess() {
        Integer id = 1;
        Double newCost = 200.0;

        Procedures updatedProcedure = new Procedures();
        updatedProcedure.setCode(id);
        updatedProcedure.setCost(newCost);

        Map<String, Double> requestBody = new HashMap<>();
        requestBody.put("cost", newCost);

        when(proService.updateProcedureCost(id, newCost)).thenReturn(updatedProcedure);

        ResponseEntity<Procedures> response = controller.updateProcedureCost(id, requestBody);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedProcedure, response.getBody());
       // verify(proService, times(1)).updateProcedureCost(id, newCost);
    }

    @Test
    void testUpdateProcedureNameSuccess() {
        Integer id = 1;
        String newName = "UpdatedProcedure";

        Procedures updatedProcedure = new Procedures();
        updatedProcedure.setCode(id);
        updatedProcedure.setName(newName);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", newName);

        when(proService.updateProcedureName(id, newName)).thenReturn(updatedProcedure);

        ResponseEntity<Procedures> response = controller.updateProcedureName(id, requestBody);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedProcedure, response.getBody());
        //verify(proService, times(1)).updateProcedureName(id, newName);
    }

    @Test
    void testGetAllProceduresSuccess() {
        Procedures procedure1 = new Procedures();
        procedure1.setCode(1);
        procedure1.setName("Procedure1");
        procedure1.setCost(100.0);

        Procedures procedure2 = new Procedures();
        procedure2.setCode(2);
        procedure2.setName("Procedure2");
        procedure2.setCost(150.0);

        List<Procedures> mockProcedures = Arrays.asList(procedure1, procedure2);

        when(proService.getAllProcedures()).thenReturn(mockProcedures);

        ResponseEntity<List<Procedures>> response = controller.getAllProcedures();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        //verify(proService, times(1)).getAllProcedures();
    }
}
