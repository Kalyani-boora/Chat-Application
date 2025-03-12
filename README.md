# project Name : Chat-Application
## Description : 
This is a real-time chat application built using Spring Boot and WebSockets. 
It allows users to enter a chatroom, send messages, and receive updates instantly. 
The application uses STOMP over WebSockets for real-time communication.
## Features :
✅ WebSocket-based real-time chat

✅ User-friendly interface

✅ Supports multiple users in a chatroom

✅ Built with Spring Boot, STOMP, and SockJS

✅ Simple and lightweight frontend
## Tech Stack:
**Backend :** Java,SpringBoot,Hibernate

**Frontend :** HTML,CSS,JavaScript

**Database :** MySQL

## Installation & Setup :
### Prerequisites
✅Java 17+

✅MySQL installed

✅Maven
### Steps to Run
-> Clone the repository

git clone

https://github.com/Kalyani-boora/Chat-Application.git
## Database configuration :
### Create Database :
-> CREATE DATABASE Chat-Application;

-> SHOW DATABASES;
### Configure application.properties :
spring.application.name=Chat-Application

server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/Chat-Application?createDatabaseIfNotExist=true

spring.datasource.username=root

spring.datasource.password=Boora12@sql

spring.jpa.hibernate.ddl-auto=update

spring.jpa.properties.hibernate.format_sql=true

spring.jpa.open-in-view=false

### Steps to Run :
mvn spring-boot:run
## Contributing :
To contribute:

Fork the repository.

Create a new branch (feature-branch-name).

Commit your changes with a meaningful message.

Push your changes to your forked repository.

Open a Pull Request with a clear description of your changes.
## Contact Information :
-> GitHub:https ://github.com/kalyani-boora

-> LinkedIn : www.linkedin.com/in/boora-kalyani-96b655314

