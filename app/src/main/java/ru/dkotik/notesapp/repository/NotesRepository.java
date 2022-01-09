package ru.dkotik.notesapp.repository;

import java.util.List;

import ru.dkotik.notesapp.model.Note;

public interface NotesRepository {

    void getAllNotes(Callback<List<Note>> callback);

    void save(String title, String description, Callback<Note> callback);

    void update(Note note, String title, String description, Callback<Note> callback);

    void delete(Note note, Callback<Void> callback);

}
