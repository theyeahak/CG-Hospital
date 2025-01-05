package com.example.hms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.example.hms.model.Procedures;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HMSProcedureTest {

	 @Autowired
	 private TestRestTemplate restTemplate;
	  
	   @Test
	   public void testGetAllProcedures() throws JSONException
	   {
		   String url="/api/procedures/";
		   String strProcedures="""
					[
					{
						"code": 1 
					},
					{
						"code": 2
					},
					{
						"code": 3
					},
					{
		   				"code": 4
		   			},
		   			{
						"code": 5 
					},
					{
						"code": 6
					},
					{
						"code": 7
					}
				]
			""";
		   ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		  JSONAssert.assertEquals(strProcedures, response.getBody(),false);
}
	   
	   
	   @Test
	    public void testProcedureCostById() {
	        Integer Id = 1; 
	        ResponseEntity<Procedures> response = restTemplate.getForEntity("/api/procedure/cost/{id}", Procedures.class, Id);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	    }
	   @Test
		public void testUpdateProcedureName() throws JSONException {
		    String url = "/api/procedure/name/6";
		    String JSONbody = """
		    {

		    	"name": "foot surgery"

		    }
		    """;
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    HttpEntity<String> request = new HttpEntity<>(JSONbody, headers);
			ResponseEntity<String> response = restTemplate.exchange(url,HttpMethod.PUT, request, String.class);
		}
	   
	
}
