/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.halobusinessfinance.microservice.controller;

/**
 *
 * @author root
 */
import com.halobusinessfinance.microservice.ldap.config.LdapConfig;
import com.halobusinessfinance.microservice.ldap.data.client.LdapClient;
import com.halobusinessfinance.microservice.ldap.repository.Payload;
import com.halobusinessfinance.microservice.ldap.repository.User;
import java.util.HashMap;
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
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@CrossOrigin(origins = "*")
public class RestSpringController {

    @Autowired
    private Environment env;

    //@Autowired
    //private LdapContextSource contextSource;

    private DirContext context;

    Map<String, String> responseMap;
    Map<String, String> userMap;

    public static void main(String[] args) {
        SpringApplication.run(RestSpringController.class, args);
    }

    @PostMapping(path = "/auth/request-for-code", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> requestForCode(@RequestBody User user) {
        //get account sid and account token
        LdapClient ldapclient = new LdapClient();
        
        LdapConfig config = new LdapConfig();
        LdapContextSource contextSource = config.contextSource(env);

        String path = "cn="
                + user.getUsername()
                + ",ou=users,"
                + env.getRequiredProperty("ldap.partitionSuffix");
        
        
        context = ldapclient.authenticate(path, user.getPassword(), contextSource);//authenticate the user first, 
        
        path = "cn=" + env.getRequiredProperty("ldap.principal") + ","
                + env.getRequiredProperty("ldap.partitionSuffix");

        StringBuilder searchFilterCN = new StringBuilder();
        searchFilterCN.append("cn=");
        searchFilterCN.append(user.getUsername());
        user = ldapclient.search(path,
                env.getRequiredProperty("ldap.password"),
                searchFilterCN.toString(),
                contextSource,
                env.getRequiredProperty("ldap.partitionSuffix"));
        
        
        //call twilio java api to send code
        /*
        responseMap.put("accountSID", user.getAccountSID());
        responseMap.put("accountToken", user.getAccountToken());
        telephonenumber
        */
        
        Map<String, String> responseMap = new HashMap<>();

        responseMap.put("isSuccess", "true");
        //responseMap.put("accountSID", user.getAccountSID());
        //responseMap.put("accountToken", user.getAccountToken());

        return ResponseEntity.ok(responseMap);
    }

    @PostMapping(path = "/auth/token", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> token(@RequestBody User user) {
        LdapClient ldapclient = new LdapClient();
        LdapConfig config = new LdapConfig();
        LdapContextSource contextSource = config.contextSource(env);
        //ldap
        String path = "cn=" + env.getRequiredProperty("ldap.principal") + ","
                + env.getRequiredProperty("ldap.partitionSuffix");

        StringBuilder searchFilterCN = new StringBuilder();
        searchFilterCN.append("cn=");
        searchFilterCN.append(user.getUsername());
        user = ldapclient.search(path,
                env.getRequiredProperty("ldap.password"),
                searchFilterCN.toString(),
                contextSource,
                env.getRequiredProperty("ldap.partitionSuffix"));
        
       
        /*Payload payload = new Payload("eW91ciB0b2tlbiBpcyBhIHRva2VuIGZvciBhIGJhc2U2NCBlbmNvZGVkIHRva2VuLg==",
                                      "Michael Ritchson",
                                      "michaelr@halobusinessfinance.com",
                                      "michaelr",
                                      "Michael",
                                      "Ritchson",
                                      "661-770-5021");
        String accessToken,
                    String username,
                    String email,
                    String userid,
                    String firstName,
                    String lastName,
                    String phone
         */
         //call Twilio - authenticate send code
         Payload payload = new Payload("eW91ciB0b2tlbiBpcyBhIHRva2VuIGZvciBhIGJhc2U2NCBlbmNvZGVkIHRva2VuLg==",
                                      user.getDisplayName(),
                                      user.getEmail(),
                                      user.getUsername(),
                                      user.getFirstName(),
                                      user.getLastName(),
                                      user.getTelephoneNumber());


        user.setPayload(payload);

        Map<String, Object> responseMap = new HashMap<>();

        responseMap.put("isSuccess", "true");
        responseMap.put("Payload", user.getPayload());

        return ResponseEntity.ok(responseMap);
    }

}
