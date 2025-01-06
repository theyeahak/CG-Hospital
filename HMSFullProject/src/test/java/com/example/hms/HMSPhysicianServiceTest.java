package com.example.hms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.hms.model.Physician;
import com.example.hms.repository.PhysicianRepository;

import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class HMSPhysicianServiceTest {
	@Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PhysicianRepository phyRepo;

    private Physician p;
    private Physician pfail;

    @BeforeEach
    public void setup() {
        p = phyRepo.findById(1).orElse(null);
        
        pfail = new Physician();
        pfail.setEmployeeID(120);
        pfail.setName("fail name");
        pfail.setPosition("no position");
        pfail.setSsn(98765432);
    }
//	
//    @Test
//    public void testGetByName() {
//        String url = "/api/physician/name/" + p.getName();
//        ResponseEntity<Physician> response = restTemplate.getForEntity(url, Physician.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(p.getName(), response.getBody().getName());
//    }
//    
//    @Test
//    public void testGetByNameFail() {
//    	String url = "/api/physician/name/" + pfail.getName();
//    	ResponseEntity<Physician> response = restTemplate.getForEntity(url, Physician.class);
//
//    	assertEquals(null, response.getBody());
//    }
    
//    @Test
//    public void testGetByPosition() {
//        String url = "/api/physician/position/" + p.getPosition();
//        ResponseEntity<Physician[]> response = restTemplate.getForEntity(url, Physician[].class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertTrue(response.getBody().length > 0);
//        assertEquals(p.getPosition(), response.getBody()[0].getPosition());
//    }
    
    @Test
    public void testGetByPositionfail() {
    	String url = "/api/physician/position/" + pfail.getPosition();
    	ResponseEntity<Physician> response = restTemplate.getForEntity(url, Physician.class);
    	
    	assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    public void testPostPhysician() {
        Physician newPhysician = new Physician();
        newPhysician.setEmployeeID(701);
        newPhysician.setName("Dr. Jane");
        newPhysician.setPosition("Cardiologist");
        newPhysician.setSsn(987654321);

        HttpEntity<Physician> request = new HttpEntity<>(newPhysician);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/physician/post", request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Record Created Successfully", response.getBody());
    }
    
    @Test
    @DisplayName("Update Physisican Name")
    public void teatUpdatePhysicianName() {
    	int empId=1;
    	String newName="AboorvaaSri";
    	String url=UriComponentsBuilder.fromPath("/api/physician/update/{empid")
    			.buildAndExpand(empId)
    			.toUriString();
    	Map<String,String>requestBody=new HashMap<>();
    	requestBody.put("newName",newName);
    	HttpEntity<Map<String,String>>request=new HttpEntity<>(requestBody);
    	ResponseEntity<Physician>response=restTemplate.exchange(url, HttpMethod.PUT,request,Physician.class);
    	
    }
    

}
