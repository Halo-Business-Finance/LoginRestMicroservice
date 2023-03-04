
# Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
# Click nbfs://nbhost/SystemFileSystem/Templates/Other/Dockerfile to edit this template

From openjdk:8
copy ./target/rest-ldap-1.0.1.jar rest-ldap-1.0.1.jar
CMD ["java","-jar","rest-ldap-1.0.1.jar"]
