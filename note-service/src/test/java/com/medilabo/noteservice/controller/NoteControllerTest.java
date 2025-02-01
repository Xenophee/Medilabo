package com.medilabo.noteservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.noteservice.model.Note;
import com.medilabo.noteservice.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private NoteService noteService;


    @Test
    void testGetNotesByPatId() throws Exception {
        String patId = "123";
        List<Note> notes = List.of(new Note(patId, "John Doe", "Note 1"), new Note(patId, "John Doe", "Note 2"));

        when(noteService.getNotesByPatId(patId)).thenReturn(notes);

        mockMvc.perform(get("/notes/{patId}", patId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(notes)));
    }


    @Test
    void testAddNoteToPatient() throws Exception {
        Note note = new Note("1", "123", "John Doe", "New Note");

        when(noteService.addNoteToPatient(any(Note.class))).thenReturn(note);

        mockMvc.perform(post("/notes/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(note)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(note)));
    }
}