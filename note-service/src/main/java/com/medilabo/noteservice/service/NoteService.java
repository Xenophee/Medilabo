package com.medilabo.noteservice.service;

import com.medilabo.noteservice.model.Note;
import com.medilabo.noteservice.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NoteService {

    private static final Logger logger = LoggerFactory.getLogger(NoteService.class);

    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    public List<Note> getNotesByPatId(String patId) {
        return noteRepository.findByPatId(patId);
    }


    public Note addNoteToPatient(Note note) {
        return noteRepository.save(note);
    }
}
