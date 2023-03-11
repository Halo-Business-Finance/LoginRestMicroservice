/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.halobusinessfinance.microservice.ldap.repository;

/**
 *
 * @author root
 */

// Importing class
import com.twilio.rest.api.v2010.account.Message;
import lombok.Data;


@Data
public class User {
    private String username;
    private String password;
    private String accountSID;
    private String authToken;
    private String twilioNumber;
    private String phoneNumber;
    private String email;
    private String code;
    private Payload payload;
    private String firstName;
    private String lastName;
    private String userid;
    private String displayName;
    private Message message;
    
    
    public User() {
        
    }
    
    
     public User(String username, 
                 String password, 
                 String code) {
        this.username = username;
        this.password = password;
        
    }
     
     public User(String displayName, 
                 String email,
                 String firstName,
                 String lastName,
                 String phoneNumber) {
        this.displayName = displayName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        
    }
     
     public Payload getPayload() {
         return payload;
     }
     
     public void setPayload(Payload payload) {
         this.payload = payload;
     }
    
    
    
}
