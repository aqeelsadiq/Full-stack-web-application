# Full-stack Web Application

A full-stack web application that collects user information to store in backend database and provide analysis reports based on data collected.

## Table of Contents

- Project Overview
- Features
- Technologies Used
- Setup and Installation


## Project Overview

This is a web application that connects household information on applicances owend (such as air handlers and water heaters), and public utilities used. It also provides data analysis reports on varies topics based on data collected, including household averages by postalcode and off-the-grid households data etc.

## Features

- Data validation
- Form data processing
- User session management
- Database integration

## Technologies Used

Outline the technologies, frameworks, libraries, and languages you used in both the front-end and back-end development. You can categorize them:

### Front-End

- HTML/CSS
- JavaScript

### Back-End

- Programming Language: Java
- Framework: Spring Boot
- Spring Boot Web for building web applications
- RESTful API design principles for exposing endpoints
- Database: MySQL database
- Database Interaction: JDBC

## Setup and Installation
To run this project locally on your machine, follow these steps:

1. **Prerequisites:**
   - Ensure you have Java, Maven, and MySQL installed

2. **Clone the Repository:**
   - git clone https://github.com/sophytao912/Full-stack-web-application.git

3. ** Build the database:**
   - Open a terminal window and make sure MySQL is in your system's path
   - Down load the db_schema.sql script provided in the repo
   - Build the database using the script: mysql -u username -p < path\to\db_schema.sql

4. ** Build and Run the Project:**
   - Open a terminal window and navigate to the project directory
   - Build the project using Maven: mvn clean install
   - Run the application using the Maven Spring Boot plugin: mvn spring-boot:run
   - Access the Application: Open your web browser and navigate to http://localhost:8082
  
