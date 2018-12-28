package com.example.hibernate.demo.controller;

import com.example.hibernate.demo.exception.ResourceNotFoundException;
import com.example.hibernate.demo.model.Note;
import com.example.hibernate.demo.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {

    @Autowired
    NoteRepository noteRepository;

    @GetMapping("/notes")
    public List<Note> getAllNotes(){
        return noteRepository.findAll();
    }

    /**
     * Create a Note
     * @param note
     * @return
     */
    @PostMapping("/notes")
    public Note createNote(@Valid @RequestBody Note note){
        return noteRepository.save(note);
    }

    /**
     * Get Note by ID
     * @param noteId
     * @return
     */
    @GetMapping("/notes/{id}")
    public Note getNoteById(@PathVariable(value = "id") Long noteId){
        return noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
    }


    /**
     * Update a Note
     * @param id
     * @param note
     * @return
     */
    @PutMapping("/notes/{id}")
    public Note updateNote(@PathVariable(value = "id") Long id, @Valid @RequestBody Note note){

        Note noteDetail = noteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));

        noteDetail.setContent(note.getContent());
        noteDetail.setTitle(note.getTitle());

        Note updatedNote = noteRepository.save(noteDetail);

        return updatedNote;
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long id){

        Note note = noteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));
        noteRepository.delete(note);
        return ResponseEntity.ok().build();
    }
}
