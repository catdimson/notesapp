package ru.dkotik.notesapp.presenter;

import java.util.List;

import ru.dkotik.notesapp.model.Note;
import ru.dkotik.notesapp.repository.NotesRepository;
import ru.dkotik.notesapp.view.list.NotesListView;

public class NotesListPresenter {

    private NotesListView view;
    private NotesRepository repository;

    public NotesListPresenter(NotesListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void refresh() {
        List<Note> result = repository.getAllNotes();
        view.showNotes(result);
    }
}
