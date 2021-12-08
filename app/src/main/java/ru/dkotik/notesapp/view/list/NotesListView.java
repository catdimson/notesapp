package ru.dkotik.notesapp.view.list;

import java.util.List;

import ru.dkotik.notesapp.model.Note;

public interface NotesListView {

    void showNotes(List<Note> notes);

}
