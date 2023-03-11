/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.halobusinessfinance.microservice.controller;

import com.halobusinessfinance.microservice.ldap.repository.User;
import com.halobusinessfinance.microservice.twilio.TwilioClient;
import com.twilio.rest.api.v2010.account.Message;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Michael Ritchson
 */


public final class RestControllerResponses {
    
    private static Map<String, String> responseMap;
    
    
    public static final Map<String, String> processAuthenticationToTwilio(User user) {
        responseMap = new HashMap<>();

        TwilioClient t = new TwilioClient(user);

        //call twilio java api to send code
        Message message = t.sendCode();
        //responseMap = new HashMap<>();
        if (message != null) {
            responseMap.put("isSuccess", "true");
            responseMap.put("message", message.getAccountSid());
            responseMap.put("message", message.getSid());

        }
        return responseMap;
    }

    public static final Map<String, String> failedLdapAuthentication() {
        responseMap = new HashMap<>();

        responseMap.put("isError", "true");
        responseMap.put("reason", "invalid userid / pasword");
        return responseMap;
    }

}
