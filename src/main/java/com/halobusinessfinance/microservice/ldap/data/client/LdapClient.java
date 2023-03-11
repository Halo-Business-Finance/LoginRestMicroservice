/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.halobusinessfinance.microservice.ldap.data.client;

import com.halobusinessfinance.microservice.controller.RestSpringController;
import com.halobusinessfinance.microservice.ldap.repository.User;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.support.LdapContextSource;

public class LdapClient {

    private DirContext context;

    public DirContext authenticate(String path,
            String password,
            LdapContextSource contextSource) {
          // Initialize the context source
        contextSource.afterPropertiesSet();

        // Use the context source to perform LDAP operations
        ContextSource ldapContextSource = contextSource;
        
        context = ldapContextSource.getContext(path, password);//dc=example,dc=org

        return context;
    }

    public User search(String path,
            String password,
            String searchFilterCN,
            LdapContextSource contextSource,
            String partitionSuffix) {
        context = this.authenticate(path, password, contextSource);

        User user = new User();
        // String searchFilter = "cn=michaelr";
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        controls.setReturningAttributes(new String[]{"uid", 
                                                     "employeeNumber",
                                                     "displayName",
                                                     "givenName",
                                                     "mail",
                                                     "telephoneNumber",
                                                     "mobile",
                                                     "sn",
                                                     "cn"});
        try {
            NamingEnumeration<SearchResult> results = context.search("ou=users,"
                    + partitionSuffix, searchFilterCN, controls);
            while (results.hasMoreElements()) {
                SearchResult result = (SearchResult) results.nextElement();
                Attributes attributes = result.getAttributes();
                user.setAccountSID(attributes.get("uid").get().toString());
                user.setAuthToken(attributes.get("employeeNumber").get().toString());
                user.setDisplayName(attributes.get("displayName").get().toString());
                user.setFirstName(attributes.get("givenName").get().toString());
                user.setEmail(attributes.get("mail").get().toString());
                user.setTwilioNumber(attributes.get("telephoneNumber").get().toString());
                user.setPhoneNumber(attributes.get("mobile").get().toString());
                user.setLastName(attributes.get("sn").get().toString());
                user.setUsername(attributes.get("cn").get().toString());
                /*
                private String telephoneNumber;
                private String email;
                private String code;
                private Payload payload;
                private String firstName;
                private String lastName;
                private String userid;
                */
                

            }
        } catch (NamingException ex) {
            Logger.getLogger(RestSpringController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                context.close();
            } catch (NamingException ex) {
                Logger.getLogger(RestSpringController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return user;
    }

}
