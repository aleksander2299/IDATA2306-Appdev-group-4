# IDATA2306-Appdev-group-4
Group project for group 4 


About this project : This is a project in courses IDATA2306 and IDATA2301 
we were tasked with creating a hotel site where you can log in as user and admin that have their own roles that has their own 
authorities, the project is to be secured with JWT authentication and the user of the project shall be able to book rooms,
search for rooms, change settings and login and logout etc. it is supposed to be a hotel room aggregator like trivago

## Website
To go to the website with backend and frontend combined the url is: [[http://10.212.27.153/](http://10.212.27.153/)](https://stayfinder.no/)
also url for swagger online will be:  [http://10.212.27.153/swagger-ui/index.html#/](http://10.212.27.153:8080/swagger-ui/index.html#/)

## Local swagger 
swagger url if you're running locally: http://localhost:8080/swagger-ui/index.html#/

## Tech Stack
- Java 21
- Spring Boot 3.4.3
- Spring Data JPA
- Spring Security with JWT
- MySQL
- Lombok
- OpenAPI (Swagger UI)

# Running locally

## Prerequisites

Before running this project, ensure you have:

- **Java 21**
- **Maven**
- **MySQL Server** running locally or remotely
- A `.env` file in the root directory with the following variables:

# MySQL Database credentials
  DB_USERNAME=your_mysql_username
  DB_PASSWORD=your_mysql_password

# JWT secret key for HS256 signing
  jwt.secret=your_generated_secret_key
  this must be generated HS256

### Database Setup

This project uses MySQL as the database.

Before running the backend, ensure:

1. MySQL Server is installed and running (locally or remotely).
2. Create the database:

```sql
CREATE DATABASE appdev4;
```

### Running the project
after all prerequisites are met you can then run in IDE of your choice and go on the main file and run it.



