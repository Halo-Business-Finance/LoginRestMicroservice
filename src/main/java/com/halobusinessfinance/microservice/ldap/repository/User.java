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
import lombok.Data;


@Data
public class User {
    private String username;
    private String password;
    private String accountSID;
    private String accountToken;
    private String telephoneNumber;
    private String email;
    private String code;
    private Payload payload;
    private String firstName;
    private String lastName;
    private String userid;
    private String displayName;
    
    
    public User() {
        
    }
    
    
     public User(String username, String password, String code) {
        this.username = username;
        this.password = password;
        
    }
     
     public Payload getPayload() {
         return payload;
     }
     
     public void setPayload(Payload payload) {
         this.payload = payload;
     }
    
    
    
}
