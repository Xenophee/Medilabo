package com.medilabo.riskreportservice.feign;

import com.medilabo.riskreportservice.DTO.NoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "note-service", url = "http://gateway:8080")
public interface NoteClient {

    @GetMapping("/notes/{patId}")
    List<NoteDTO> getNotesByPatId(@PathVariable String patId);
}

