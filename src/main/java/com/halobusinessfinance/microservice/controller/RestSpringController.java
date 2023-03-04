/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.halobusinessfinance.microservice.controller;

/**
 *
 * @author root
 */
import com.halobusinessfinance.microservice.ldap.data.client.LdapClient;
import static com.halobusinessfinance.microservice.ldap.data.client.LdapClient.digestSHA;
import com.halobusinessfinance.microservice.ldap.repository.User;
import com.halobusinessfinance.microservice.ldap.repository.UserLdap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@CrossOrigin(origins = "*")
public abstract class RestSpringController {

   
    @Autowired
    private Environment env;

    @Autowired
    private ContextSource contextSource;

    private DirContext context;

    public static void main(String[] args) {
        SpringApplication.run(RestSpringController.class, args);
    }
    
    private DirContext authenticate(String path, String password) {
        context = contextSource.getContext(path, password);//dc=example,dc=org

        return context;
    }

    @PostMapping(path = "/auth/request-for-code", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> requestForCode(@RequestBody User user) {
        //get account sid and account token
        String path =  "cn="
                + user.getUsername()
                + ",ou=users,"
                + env.getRequiredProperty("ldap.partitionSuffix");
        context = authenticate(path, user.getPassword());//authenticate the user first, 
        
        
        path =  "cn=" + env.getRequiredProperty("ldap.principal") + "," 
                + env.getRequiredProperty("ldap.partitionSuffix");
        context = authenticate(path, env.getRequiredProperty("ldap.password"));//authenticate the pricipal to perform ldap tasks
      
        String searchFilter = "cn=michaelr";
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(new String[]{"uid", "employeeNumber","telephoneNumber"});
        try {
            NamingEnumeration<SearchResult> results = context.search("ou=users," +
                 env.getRequiredProperty("ldap.partitionSuffix"), searchFilter, controls);
            while (results.hasMoreElements()) {
                SearchResult result = (SearchResult) results.nextElement();
                Attributes attributes = result.getAttributes();
                user.setAccountSID(attributes.get("uid").get().toString());
                user.setAccountToken(attributes.get("employeeNumber").get().toString());
                user.setTelephoneNumber(attributes.get("telephoneNumber").get().toString());

            }
        } catch (NamingException ex) {
            Logger.getLogger(RestSpringController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                context.close();
            } catch (NamingException ex) {
                Logger.getLogger(RestSpringController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //create api call to twilio to send accountSID, AccountToken, telephoneNumber
        
       //return JSON
        Map<String, String> responseMap = new HashMap<>();
        
        responseMap.put("isSuccess", "true");
        responseMap.put("accountSID", user.getAccountSID());
        responseMap.put("accountToken", user.getAccountToken());
        responseMap.put("telephoneNumber", user.getTelephoneNumber());

        return ResponseEntity.ok(responseMap);
    }

}
