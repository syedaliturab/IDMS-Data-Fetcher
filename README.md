# IDMS Data Fetcher

This project is designed to interact with the IDMS  API to authenticate users, fetch account details, and store them for further processing. The application integrates with Spring Boot, Hibernate, and MySQL to ensure seamless data management and API interactions.

## Table of Contents


## Project Overview

This application authenticates users with the IDMS system, fetches account details from it, and stores the data in a MySQL database. The goal is to ensure no duplicate entries while maintaining secure authentication and data fetching.

## Technologies Used

- **Spring Boot** - Framework for building the backend REST API.
- **Spring Security** - For authentication and authorization.
- **Hibernate** - ORM framework to interact with MySQL.
- **MySQL** - Database for storing account details.
- **REST API** - Communication with the IDMS API to fetch user and account details.
- **Maven** - Build tool for dependency management and project packaging.
- **Logback** - Logging framework for logging application activity.
- **Swagger** - Swagger for the documentation.

## Setup Instructions


### Prerequisites

Before running the application, ensure you have the following installed on your machine:

- **Java 17 or higher**
- **SpringBoot 3+**
- **Maven**
- **MySQL**
- **Git**
- **Postman**

### Clone the Repository

```bash
git clone https://github.com/yourusername/idms-data-fetcher.git
cd idms-data-fetcher



### SQL Query

- a file is added as sqlScript run that for creating table or copy queries from below

CREATE TABLE user
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)

INSERT INTO `user`( `name`, `password`) VALUES ('adm','3hbW0QMkJQvSjpFHteqz3A==');


###### Here password is different from provided (DriveSoft@@!) as it is in encrypted form

### FOR DOCUMENTATION REFER SWAGGER

-http://localhost:8081/swagger-ui/index.html#/

