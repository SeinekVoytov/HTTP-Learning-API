# HTTP Learning API

The HTTP Learning API is a Java-based tool designed to assist newcomers in learning and experimenting 
with HTTP requests. It provides users with fake data and supports all types of HTTP requests. 
Notably, non-idempotent requests are handled gracefully, simulating successful responses without altering data.

## Features:

1) Support for all types of HTTP requests.
2) Simulates successful responses for non-idempotent requests.
3) Utilizes Java, Hibernate, Apache Tomcat Server, Jackson and JUnit.
4) Data is stored in a local MySQL database.
5) Returns all responses in JSON format

## Technologies

- Java 17
- Hibernate 6.3.1
- Apache Tomcat Server 10.1.13
- Jackson 2.15.2
- JUnit 5.9.1
- MySQL 8.0.33

## Fake-Data Model

HttpLearningAPI comes with a set of 4 common resources:

- /users	10 users
- /prescriptions 100 prescriptions
- /posts	100 posts
- /comments	500 comments

***Note***: resources have relations. For example: posts have many comments, users have many prescriptions etc.

## Usage

The HTTP Study API is designed to be user-friendly and educational. You can perform the following tasks with the API:

- Send various types of HTTP requests.
- Explore the provided fake data, headers and body corresponding to request.

### Example usage:
```
GET /HttpLearningApi/users/1
POST /HttpLearningApi/users/5/prescriptions
DELETE /HttpLearningApi/posts/34
```
