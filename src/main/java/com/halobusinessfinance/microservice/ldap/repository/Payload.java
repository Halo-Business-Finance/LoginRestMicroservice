/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.halobusinessfinance.microservice.ldap.repository;

// Importing class
import lombok.Data;


@Data
public class Payload {
    private String access_token;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String userid;
    private String phone;
    
    
    public Payload (String accessToken,
                    String username,
                    String email,
                    String userid,
                    String firstName,
                    String lastName,
                    String phone) {
        this.access_token = accessToken;
        this.userName = username;
        this.email = email;
        this.userid = userid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        
    }
    
}
