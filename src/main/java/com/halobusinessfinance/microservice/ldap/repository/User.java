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

/*
String accountSID = attributes.get("uid").get().toString();
                String accountToken = attributes.get("employeeNumber").get().toString();
                String telephoneNumber = attributes.get("telephoneNumber").get().toString();
*/


@Data
public class User {
    private String username;
    private String password;
    private String accountSID;
    private String accountToken;
    private String telephoneNumber;
    
    
    
     public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    
    
}
