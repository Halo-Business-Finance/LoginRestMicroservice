/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.halobusinessfinance.microservice.ldap.repository;

// Importing class
//import lombok.Data;
 
import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

@Entry(base = "ou=users", objectClasses = { "person", "inetOrgPerson", "top" })
public class UserLdap  {
        
    @Id
    private Name dn;
    
    private @Attribute(name = "cn") String username;
    private @Attribute(name = "userPassword") String password;
    private @Attribute(name = "st") String st;
    private @Attribute(name = "uid") String uid;

   
    public UserLdap(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
   

    public Name getDn() {
        return dn;
    }

    public void setDn(Name dn) {
        this.dn = dn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }
    
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return username;
    }

}