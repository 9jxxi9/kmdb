package com.example.moviesapi.controller;

import com.example.moviesapi.dto.ActorRequest;
import com.example.moviesapi.model.Actor;
import com.example.moviesapi.service.ActorService;
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

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class ActorControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private ActorService actorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Actor actor;
    private ActorRequest actorRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        actor = new Actor("John Doe", new Date(), null);
        actorRequest = new ActorRequest("John Doe", new Date(), null);
    }

    @Test
    void testCreateActor_Valid() throws Exception {
        Mockito.when(actorService.createActor(any(Actor.class))).thenReturn(actor);

        mockMvc.perform(post("/api/actors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testCreateActor_Invalid() throws Exception {
        actorRequest.setName(null);

        mockMvc.perform(post("/api/actors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetActors_Valid() throws Exception {
        mockMvc.perform(get("/api/actors")
                        .param("name", "John")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetActors_Invalid() throws Exception {
        mockMvc.perform(get("/api/actors")
                        .param("name", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testModifyActor_Valid() throws Exception {
        Mockito.when(actorService.findById(anyLong())).thenReturn(Optional.of(actor));
        Mockito.when(actorService.modifyActor(anyLong(), any(Actor.class))).thenReturn(Optional.of(actor));

        mockMvc.perform(patch("/api/actors/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testModifyActor_Invalid() throws Exception {
        actorRequest.setName(null);

        mockMvc.perform(patch("/api/actors/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actorRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteActor_Valid() throws Exception {
        Mockito.when(actorService.findById(anyLong())).thenReturn(Optional.of(actor));

        mockMvc.perform(delete("/api/actors/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteActor_Invalid() throws Exception {
        Mockito.when(actorService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/actors/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetActorById_Valid() throws Exception {
        Mockito.when(actorService.findById(anyLong())).thenReturn(Optional.of(actor));

        mockMvc.perform(get("/api/actors/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testGetActorById_Invalid() throws Exception {
        Mockito.when(actorService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/actors/{id}", 1L))
                .andExpect(status().isNotFound());
    }
}