/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.halobusinessfinance.microservice.controller;

import com.halobusinessfinance.microservice.ldap.repository.User;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@ExtendWith(MockitoExtension.class)

/**
 *
 * @author root
 */
public class RestSpringControllerTest {
    
    public RestSpringControllerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of main method, of class RestSpringController.
     */
   // @Test
    public void testMain() {
        System.out.println("main");
        String[] args = {"mike","dude"};
        RestSpringController.main(args);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of requestForCode method, of class RestSpringController.
     */
    
   /* @Mock
    private RestSpringController restController;

    @InjectMocks
    private ResponseEntity requestForCode;
*/

  //  @Test
  /*  public void testRequestForCode() {
       
        RestSpringController restController = new RestSpringController();
        User user = new User("mike","dude");
        user.setAccountSID("12345");
        user.setAccountToken("4567");
        
        Map<String, String> responseMap = new HashMap<>();
        
        responseMap.put("isSuccess", "true");
        responseMap.put("accountSID", user.getAccountSID());
        responseMap.put("accountToken", user.getAccountToken());
        responseMap.put("telephoneNumber", user.getTelephoneNumber());

               
       
        ResponseEntity<Object> responseEntity = restController.requestForCode(user);
// Verify the response entity
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseMap, responseEntity.getBody());
    }
    
*/
 
}
