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
import com.halobusinessfinance.microservice.twilio.TwilioClient;
import com.twilio.rest.api.v2010.account.Message;
import java.util.HashMap;
import java.util.Map;
import javax.naming.directory.DirContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    Map<String, String> userMap;

    public static void main(String[] args) {
        SpringApplication.run(RestSpringController.class, args);
    }

    @PostMapping(path = "/auth/request-for-code", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> requestForCode(@RequestBody User user) {
        Map<String, String> responseMap = new HashMap<>();
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
        //populate user account information
        TwilioClient t = new TwilioClient(user);

        //call twilio java api to send code
        Message message = t.sendCode();
        responseMap = new HashMap<>();
        if (message != null) {
            responseMap.put("isSuccess", "true");
            responseMap.put("message", message.getAccountSid());
            responseMap.put("message", message.getSid());
        } else {
            responseMap.put("isSuccess", "false");
            responseMap.put("reason", "error sending code");
        }

        return ResponseEntity.ok(responseMap);
    }

    @PostMapping(path = "/auth/token", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> authToken(@RequestBody User user) {
        System.out.println("authToken - user = " + user.getUsername() + " code - " + user.getCode());
        Map<String, Object> response = new HashMap<>();
        //get code - user.code
        Map<String, Object> twilioMap = TwilioClient.twilioResultMap;
        //compare code
        User twilioUser = (User) twilioMap.get(user.getUsername());

        String code = twilioUser.getCode();
        if (code.equals(user.getCode())) {
            Message message = (Message) twilioUser.getMessage();
            //were be good on the code
            //contunue
            /*LdapClient ldapclient = new LdapClient();
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
             */
            String access_token = message.getSid();
            Payload payload = new Payload(access_token,
                    twilioUser.getDisplayName(),
                    twilioUser.getEmail(),
                    twilioUser.getUsername(),
                    twilioUser.getFirstName(),
                    twilioUser.getLastName(),
                    twilioUser.getPhoneNumber());

            user.setPayload(payload);

            response.put("isSuccess", "true");
            response.put("Payload", user.getPayload());

        } else {
            response = new HashMap<>();
            response.put("isError", "true");
            response.put("reason", "error code did not match");

        }

        return ResponseEntity.ok(response);
    }

}
