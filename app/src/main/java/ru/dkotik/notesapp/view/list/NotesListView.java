package ru.dkotik.notesapp.view.list;

import java.util.List;

import ru.dkotik.notesapp.model.Note;

public interface NotesListView {

    void showNotes(List<Note> notes);

    void showProgress();

    void hideProgress();

    void onNoteAdded(Note note);

    void onNoteRemoved(Note selectedNote);

    void onNoteUpdated(Note note);
}
