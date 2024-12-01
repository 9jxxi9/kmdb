package com.example.moviesapi.controller;

import com.example.moviesapi.dto.GenreRequest;
import com.example.moviesapi.model.Genre;
import com.example.moviesapi.service.GenreService;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class GenreControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @Autowired
    private ObjectMapper objectMapper;

    private Genre genre;
    private GenreRequest genreRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        genre = new Genre("Action");
        genreRequest = new GenreRequest("Action");
    }

    @Test
    void testCreateGenre_Valid() throws Exception {
        Mockito.when(genreService.createGenre(any(Genre.class))).thenReturn(genre);

        mockMvc.perform(post("/api/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Action"));
    }

    @Test
    void testCreateGenre_Invalid() throws Exception {
        genreRequest.setName(null);

        mockMvc.perform(post("/api/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetGenres_Valid() throws Exception {
        mockMvc.perform(get("/api/genres")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetGenres_Invalid() throws Exception {
        mockMvc.perform(get("/api/genres")
                        .param("page", "-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testModifyGenre_Valid() throws Exception {
        Mockito.when(genreService.findById(anyLong())).thenReturn(Optional.of(genre));
        Mockito.when(genreService.modifyGenre(anyLong(), any(Genre.class))).thenReturn(Optional.of(genre));

        mockMvc.perform(patch("/api/genres/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Action"));
    }

    @Test
    void testModifyGenre_Invalid() throws Exception {
        genreRequest.setName(null);

        mockMvc.perform(patch("/api/genres/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteGenre_Valid() throws Exception {
        Mockito.when(genreService.findById(anyLong())).thenReturn(Optional.of(genre));

        mockMvc.perform(delete("/api/genres/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteGenre_Invalid() throws Exception {
        Mockito.when(genreService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/genres/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetGenreById_Valid() throws Exception {
        Mockito.when(genreService.findById(anyLong())).thenReturn(Optional.of(genre));

        mockMvc.perform(get("/api/genres/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Action"));
    }

    @Test
    void testGetGenreById_Invalid() throws Exception {
        Mockito.when(genreService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/genres/{id}", 1L))
                .andExpect(status().isNotFound());
    }
}