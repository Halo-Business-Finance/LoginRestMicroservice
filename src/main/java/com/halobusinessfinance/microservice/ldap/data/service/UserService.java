/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.halobusinessfinance.microservice.ldap.data.service;

import com.halobusinessfinance.microservice.ldap.repository.UserLdap;
import com.halobusinessfinance.microservice.ldap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.ldap.core.LdapTemplate;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LdapTemplate ldapTemplate;

    public Boolean authenticate(final String username, final String password) {
        UserLdap user = userRepository.findByUsernameAndPassword(username, password);
        return user != null;
    }

    public List<String> search(final String username) {
        List<UserLdap> userList = userRepository.findByUsernameLikeIgnoreCase(username);
        if (userList == null) {
            return Collections.emptyList();
        }

        return userList.stream()
                .map(UserLdap::getUsername)
                .collect(Collectors.toList());
    }

    public void create(final String username, final String password) {
        UserLdap newUser = new UserLdap(username, digestSHA(password));
        newUser.setUsername(LdapUtils.emptyLdapName().toString());
        userRepository.save(newUser);

    }

    public void modify(final String username, final String password) {
        UserLdap user = userRepository.findByUsername(username);
        user.setPassword(password);
        userRepository.save(user);
    }

    private String digestSHA(final String password) {
        String base64;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.update(password.getBytes());
            base64 = Base64.getEncoder()
                    .encodeToString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return "{SHA}" + base64;
    }
}
