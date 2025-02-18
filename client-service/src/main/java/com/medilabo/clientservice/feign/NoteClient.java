package com.medilabo.clientservice.feign;

import com.medilabo.clientservice.DTO.NoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@FeignClient(name = "note-service", url = "http://gateway:8080")
public interface NoteClient {

    @GetMapping("/notes/{patId}")
    List<NoteDTO> getNotesByPatientId(@PathVariable String patId);

    @PostMapping("/notes/add")
    NoteDTO addNoteToPatient(NoteDTO newNote);
}
