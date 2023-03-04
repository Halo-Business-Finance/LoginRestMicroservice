/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.halobusinessfinance.microservice.ldap.repository;

import java.util.List;

import org.springframework.data.ldap.repository.LdapRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends LdapRepository<UserLdap> {

    UserLdap findByUsername(String username);

    UserLdap findByUsernameAndPassword(String username, String password);

    List<UserLdap> findByUsernameLikeIgnoreCase(String username);

}