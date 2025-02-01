package com.medilabo.noteservice.controller;

import com.medilabo.noteservice.model.Note;
import com.medilabo.noteservice.service.NoteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/notes")
public class NoteController {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/{patId}")
    public ResponseEntity<List<Note>> getNotesByPatId(@PathVariable String patId) {
        logger.info("Requête pour obtenir les notes du patient avec l'ID: {}", patId);
        List<Note> notes = noteService.getNotesByPatId(patId);
        logger.info("Notes du patient avec l'ID: {} récupérées", patId);
        return ResponseEntity.ok(notes);
    }

    @PostMapping("/add")
    public ResponseEntity<Note> addNoteToPatient(@RequestBody @Valid Note newNote) {
        logger.info("Requête pour ajouter une note au patient avec l'ID: {}", newNote.getPatId());
        Note note = noteService.addNoteToPatient(newNote);
        logger.info("Note ajoutée au patient avec l'ID: {}", newNote.getPatId());
        return ResponseEntity.status(HttpStatus.CREATED).body(note);
    }
}
