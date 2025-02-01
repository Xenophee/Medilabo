package com.medilabo.noteservice.service;

import com.medilabo.noteservice.model.Note;
import com.medilabo.noteservice.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetNotesByPatId() {
        String patId = "123";
        List<Note> notes = List.of(new Note(patId, "John Doe", "Note 1"), new Note(patId, "John Doe", "Note 2"));

        when(noteRepository.findByPatId(patId)).thenReturn(notes);

        List<Note> result = noteService.getNotesByPatId(patId);
        assertThat(result).isEqualTo(notes);
    }

    @Test
    void testGetNotesByPatId_NotFound() {
        String patId = "123";

        when(noteRepository.findByPatId(patId)).thenReturn(Collections.emptyList());

        List<Note> result = noteService.getNotesByPatId(patId);
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testAddNoteToPatient() {
        Note note = new Note("123", "John Doe", "New Note");

        when(noteRepository.save(any(Note.class))).thenReturn(note);

        Note result = noteService.addNoteToPatient(note);

        assertThat(result).isEqualTo(note);
    }
}