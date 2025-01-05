package com.example.hms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class HMSPatientServiceTest {
	
	
	    @Autowired
	    private TestRestTemplate restTemplate;
//	    @Test
//	    public void testGetAllPatients() {
//	        String url = "/api/patient";
//
//	        // Execute GET request and expect a JSON array response as a string
//	        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//
//	        // Assert that the response status code is 200 OK
//	        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//	        // Parse the JSON response body into an array of Patient objects
//	        ObjectMapper mapper = new ObjectMapper();
//	        try {
//	            Patient[] patientsArray = mapper.readValue(response.getBody(), Patient[].class);
//	            
//	            // Convert the array into a List for easier assertions
//	            List<Patient> patientsList = Arrays.asList(patientsArray);
//
//	            // Assert that the list is not null and contains elements
//	            assertNotNull(patientsList);
//	            assertFalse(patientsList.isEmpty(), "The patients list should not be empty");
//
//	        } catch (JsonProcessingException e) {
//	            // Fail the test if JSON processing fails
//	            fail("Failed to parse JSON response: " + e.getMessage());
//	        }
//	    }



	   
	   
 

	   



}
