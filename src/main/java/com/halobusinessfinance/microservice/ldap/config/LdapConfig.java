/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.halobusinessfinance.microservice.ldap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
//@PropertySource("classpath:application.properties")
//@ComponentScan(basePackages = {"com.halobusinessfinance.microservice.ldap.*"})
//@Profile("default")
//@EnableLdapRepositories(basePackages = "com.halobusinessfinance.microservice.ldap.**")
public class LdapConfig {

    @Autowired
    private Environment env;

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();

        contextSource.setUrl(env.getRequiredProperty("ldap.url"));
        contextSource.setBase(
                env.getRequiredProperty("ldap.partitionSuffix"));
        contextSource.setUserDn(
                env.getRequiredProperty("ldap.username"));
        contextSource.setPassword(
                env.getRequiredProperty("ldap.password"));

        /*contextSource.setUrl("ldap://45.79.102.106:389");
        contextSource.setBase("dc=halobusinessfinance,dc=com");
        contextSource.setUserDn("cn=admin,dc=halobusinessfinance,dc=com");
        contextSource.setPassword("Goniopora@1234");
         */
        return contextSource;
    }

   
}
