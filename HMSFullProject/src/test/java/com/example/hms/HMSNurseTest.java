package com.example.hms;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.hms.model.Nurse;
import com.example.hms.repository.NurseRepository;
import jakarta.transaction.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class HMSNurseTest {
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
    private NurseRepository nurseRepo;
    private Nurse n;
 
    @BeforeEach
    public void setup() {
        n = nurseRepo.findById(101).orElse(null);
    }
    
    @Test
    public void testGetAllNurse() {
       String url = "/api/nurse/";
       ResponseEntity<Nurse[]> response = restTemplate.getForEntity(url, Nurse[].class);
       assertEquals(HttpStatus.OK, response.getStatusCode());
       assertNotNull(response.getBody());
       assertTrue(response.getBody().length > 0);
}
	
    @Test
    public void testGetNurseById() {
        String url = "/api/nurse/" + n.getEmployeeID();
        ResponseEntity<Nurse> response = restTemplate.getForEntity(url, Nurse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(n.getName(), response.getBody().getName());
    }
    
    @Test
    public void testGetNursePosition() {
    	String url = "/api/nurse/position/"+ n.getEmployeeID();
    	ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertNotNull(response.getBody());
    	assertTrue(response.getBody().length() > 0);
    	}
    
    @Test
    public void testIsNurseRegistered() {
    	String url = "/api/nurse/registered/"+n.getEmployeeID();
    	ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(true, response.getBody());
        }
//    @Test
//    public void testUpdateNurseRegistrationStatus() throws Exception {
//        String url = "/api/nurse/registered/102" + n.getEmployeeID();
//        Nurse updatedNurse = new Nurse();
//        updatedNurse.setRegistered(false);
//        HttpEntity<Nurse> request = new HttpEntity<>(updatedNurse);
//        ResponseEntity<Nurse> response = restTemplate.exchange(url, HttpMethod.PUT, request, Nurse.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(updatedNurse.getRegistered(), response.getBody().getRegistered());
//    }
}

