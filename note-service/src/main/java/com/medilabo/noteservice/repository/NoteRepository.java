package com.medilabo.noteservice.repository;

import com.medilabo.noteservice.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findByPatId(String patId);
}
