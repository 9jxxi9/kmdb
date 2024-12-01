package com.example.moviesapi.controller;

import com.example.moviesapi.dto.MovieRequest;
import com.example.moviesapi.model.Movie;
import com.example.moviesapi.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Time;
import java.time.Year;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class MovieControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    private Movie movie;
    private MovieRequest movieRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Time duration = Time.valueOf("02:00:00");
        movie = new Movie("Test Movie", Year.of(2022), duration, null, null);
        movieRequest = new MovieRequest("Test Movie", Year.of(2022), duration, null, null);
    }

    @Test
    void testCreateMovie_Valid() throws Exception {
        Mockito.when(movieService.create(any(Movie.class))).thenReturn(movie);

        mockMvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Movie"));
    }

    @Test
    void testCreateMovie_Invalid() throws Exception {
        movieRequest.setTitle(null);

        mockMvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetMovies_Valid() throws Exception {
        mockMvc.perform(get("/api/movies")
                        .param("year", "2022")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetMovies_Invalid() throws Exception {
        mockMvc.perform(get("/api/movies")
                        .param("year", "invalid-year"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testModifyMovie_Valid() throws Exception {
        Mockito.when(movieService.findById(anyLong())).thenReturn(Optional.of(movie));
        Mockito.when(movieService.modifyMovie(anyLong(), any(Movie.class))).thenReturn(Optional.of(movie));

        mockMvc.perform(patch("/api/movies/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Movie"));
    }

    @Test
    void testModifyMovie_Invalid() throws Exception {
        movieRequest.setTitle(null);

        mockMvc.perform(patch("/api/movies/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteMovie_Valid() throws Exception {
        Mockito.when(movieService.findById(anyLong())).thenReturn(Optional.of(movie));

        mockMvc.perform(delete("/api/movies/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteMovie_Invalid() throws Exception {
        Mockito.when(movieService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/movies/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetMovieById_Valid() throws Exception {
        Mockito.when(movieService.findById(anyLong())).thenReturn(Optional.of(movie));

        mockMvc.perform(get("/api/movies/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Movie"));
    }

    @Test
    void testGetMovieById_Invalid() throws Exception {
        Mockito.when(movieService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/movies/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetMovieWithActors_Valid() throws Exception {
        Mockito.when(movieService.findById(anyLong())).thenReturn(Optional.of(movie));

        mockMvc.perform(get("/api/movies/{id}/actors", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Movie"));
    }

    @Test
    void testGetMovieWithActors_Invalid() throws Exception {
        Mockito.when(movieService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/movies/{id}/actors", 1L))
                .andExpect(status().isNotFound());
    }
}