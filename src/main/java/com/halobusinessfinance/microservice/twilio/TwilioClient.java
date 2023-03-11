/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.halobusinessfinance.microservice.twilio;

import com.halobusinessfinance.microservice.ldap.repository.User;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.util.HashMap;
import java.util.Map;
 // Importing class
import lombok.Data;


 
/**
 *
 * @author root
 */
@Data


public class TwilioClient {
     // Your Account Sid and Auth Token from twilio.com/console
  public static final String ACCOUNT_SID = "ACc61ec866f325701c3a9e1d53d04cd4e0";
  public static final String AUTH_TOKEN = "0cf3392bae4cf2ed25b7abeb719fe5c0";
  public static final String TWILIO_NUMBER = "+19496766900";
  public static Map <String, Object> twilioResultMap = new HashMap<>();
  
  private User user;
  
  public TwilioClient (User user) {
      this.user = user;
      
  }

  public static void main(String[] args) {
        
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    // Generate a random verification code
    String code = String.valueOf((int) (Math.random() * 8999) + 1000);

    // Send the code to the user's phone number
    Message message = Message.creator(
            new PhoneNumber("8474069749"),
            new PhoneNumber(TWILIO_NUMBER),
            "Your verification code is " + code)
        .create();
    
    

    System.out.println(message.getSid());
  }
  
  public Message sendCode() {
    Twilio.init(user.getAccountSID(), user.getAuthToken());

    
    String code = String.valueOf((int) (Math.random() * 8999) + 1000);
    // Send the code to the user's phone number
    user.setCode(code);
    Message message = Message.creator(
            new PhoneNumber(user.getPhoneNumber()),
            new PhoneNumber(user.getTwilioNumber()),
            "Your verification code is " + code)
        .create();
    
    user.setMessage(message);
    twilioResultMap.put(user.getUsername(), user);
  
    
    //System.out.println(message.getSid());
    return message;
  }

}
