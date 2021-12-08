package ru.dkotik.notesapp.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.dkotik.notesapp.R;
import ru.dkotik.notesapp.model.Note;

public class InMemoryNotesRepository implements NotesRepository {


    @Override
    public List<Note> getAllNotes() {
        List<Note> result = new ArrayList<>(
                Arrays.asList(
                        // в дальнейшем дата будет браться как текущая при добавлении заметки
                        new Note(R.string.note1, R.string.simple_data, R.string.fish_text),
                        new Note(R.string.note2, R.string.simple_data, R.string.fish_text),
                        new Note(R.string.note3, R.string.simple_data, R.string.fish_text),
                        new Note(R.string.note4, R.string.simple_data, R.string.fish_text),
                        new Note(R.string.note5, R.string.simple_data, R.string.fish_text),
                        new Note(R.string.note6, R.string.simple_data, R.string.fish_text),
                        new Note(R.string.note7, R.string.simple_data, R.string.fish_text),
                        new Note(R.string.note8, R.string.simple_data, R.string.fish_text),
                        new Note(R.string.note9, R.string.simple_data, R.string.fish_text),
                        new Note(R.string.note6, R.string.simple_data, R.string.fish_text),
                        new Note(R.string.note7, R.string.simple_data, R.string.fish_text),
                        new Note(R.string.note8, R.string.simple_data, R.string.fish_text),
                        new Note(R.string.note9, R.string.simple_data, R.string.fish_text)
                )
        );
        return result;
    }

    @Override
    public void addNote(Note note) {

    }

    @Override
    public void removeNote(Note note) {

    }
}
