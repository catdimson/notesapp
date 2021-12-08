package ru.dkotik.notesapp.repository;

import java.util.List;

import ru.dkotik.notesapp.model.Note;

public interface NotesRepository {

    List<Note> getAllNotes();

    void addNote(Note note);

    void removeNote(Note note);

}
