# Movie Database API

## Project Overview

This project is a robust REST API for managing a movie database using Spring Boot and JPA. It allows users to store information about movies, including their genres and the actors who starred in them. The API supports CRUD operations for movies, genres, and actors, and provides endpoints to retrieve movies by genre or release year, and to manage associations between movies and actors.

## Setup and Installation

### Prerequisites

- Java 17 or higher
- Maven 3.6.3 or higher
- SQLite

### Installation Steps

1. **Clone the repository:**
   ```sh
   git clone https://gitea.kood.tech/juriboikov/kmdb
   ```

2. **Build the project:**
   ```sh
   mvn clean install
   ```

3. **Run the application:**
   ```sh
   mvn spring-boot:run
   ```

4. **Database Configuration:**
   The application uses SQLite as the database. The configuration is set in the `application.properties` file:
   ```properties
   spring.datasource.url=jdbc:sqlite:movies.db
   spring.datasource.driver-class-name=org.sqlite.JDBC
   spring.jpa.database-platform=org.hibernate.dialect.SQLiteDialect
   spring.jpa.hibernate.ddl-auto=update
   ```

## Usage Guide

### API Endpoints

#### Genre Endpoints

- **Create Genre:**
  ```http
  POST /api/genres
  ```
  Request Body:
  ```json
  {
  "name": "Action"
  }
  ```

- **Get All Genres:**
  ```http
  GET /api/genres
  ```

- **Get Genre by ID:**
  ```http
  GET /api/genres/{id}
  ```

- **Update Genre:**
  ```http
  PATCH /api/genres/{id}
  ```
  Request Body:
  ```json
  {
  "name": "Adventure"
  }
  ```

- **Delete Genre:**
  ```http
  DELETE /api/genres/{id}
  ```

#### Movie Endpoints

- **Create Movie:**
  ```http
  POST /api/movies
  ```
  Request Body:
  ```json
  {
  "title": "Inception",
  "releaseYear": "2010",
  "duration": "02:28:00",
  "genres": [1, 2],
  "actors": [1, 2, 3]
  }
  ```

- **Get All Movies:**
  ```http
  GET /api/movies
  ```

- **Get Movie by ID:**
  ```http
  GET /api/movies/{id}
  ```

- **Update Movie:**
  ```http
  PATCH /api/movies/{id}
  ```
  Request Body:
  ```json
  {
  "title": "Inception Updated",
  "releaseYear": "2020",
  "duration": "01:20:20"
  }
  ```

- **Delete Movie:**
  ```http
  DELETE /api/movies/{id}
  ```

- **Search Movies by Title:**
  ```http
  GET /api/movies?title=Inception
  ```

- **Filter Movies by Genre:**
  ```http
  GET /api/movies?genreId=1
  ```

- **Filter Movies by Release Year:**
  ```http
  GET /api/movies?releaseYear=2010
  ```

#### Actor Endpoints

- **Create Actor:**
  ```http
  POST /api/actors
  ```
  Request Body:
  ```json
  {
  "name": "Leonardo DiCaprio",
  "birthDate": "1974-11-11"
  }
  ```

- **Get All Actors:**
  ```http
  GET /api/actors
  ```

- **Get Actor by ID:**
  ```http
  GET /api/actors/{id}
  ```

- **Update Actor:**
  ```http
  PATCH /api/actors/{id}
  ```
  Request Body:
  ```json
  {
  "name": "Leonardo DiCaprio Updated",
  "birthDate": "1974-11-12"
  }
  ```

- **Delete Actor:**
  ```http
  DELETE /api/actors/{id}
  ```

- **Search Actors by Name:**
  ```http
  GET /api/actors?name=Leonardo
  ```

## Additional Features

- **Pagination:** Implemented for GET requests that return multiple entities.
- **Basic Search:** Implemented for movies by title.
- **Error Handling:** Includes input validation, custom exceptions, and global exception handling.
- **Tests:** Implemented test cases for each controller.


