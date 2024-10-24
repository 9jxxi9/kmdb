# Movie Database API

## Project Description

This API is designed to manage a database of movies, actors, and genres. It implements Create, Read, Update, and Delete (CRUD) functions for movies, actors, and genres, and also provides capabilities for filtering and searching.

## Instructions for Installing and Running the Application

1. **Clone the repository:**
   ```bash
   git clone <https://gitea.kood.tech/juriboikov/kmdb.git>
   cd movie-api
Run the application:
./mvnw spring-boot:run
API Usage Examples

Get all actors:
GET /api/actors

Search actors by name:
GET /api/actors/search?name=Tom

Create a new actor:
POST /api/actors
Content-Type: application/json

{
  "name": "Tom Hanks",
  "birthDate": "1956-07-09"
}

Get a movie by ID:
GET /api/movies/{id}

Delete a genre:
DELETE /api/genres/{genreId}

Filter movies by genre:
GET /api/movies?genreId={genreId}

Filter movies by title:
GET /api/movies?title=Matrix

Pagination:
GET /api/movies?page=0&size=10



Additional Features
Case-insensitive search for actors and movies.
Filtering movies by genres and actors.
Pagination for retrieving large amounts of data.


Testing
Test the API using Postman or cURL to ensure its functionality. 



Technologies Used
Spring Boot: The main framework for developing the application.
Spring Data JPA: For working with the database and performing CRUD operations.
SQLite: A lightweight relational database for data storage.
Maven: A project management system for building and dependencies.