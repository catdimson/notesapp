package ru.dkotik.notesapp.presenter;

import java.util.List;

import ru.dkotik.notesapp.domain.entity.Note;
import ru.dkotik.notesapp.data.repository.Callback;
import ru.dkotik.notesapp.data.repository.NotesRepository;
import ru.dkotik.notesapp.ui.list.NotesListView;

public class NotesListPresenter {

    private NotesListView view;
    private NotesRepository repository;

    public NotesListPresenter(NotesListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void requestNotes() {
        view.showProgress();

        repository.getAllNotes(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> result) {
                view.showNotes(result);
                view.hideProgress();
            }

            @Override
            public void onError(Throwable error) {
                view.hideProgress();
            }
        });
    }

    public void onNoteAdded(Note note) {
        view.onNoteAdded(note);
    }

    public void removeNote(Note selectedNote) {
        view.showProgress();

        repository.delete(selectedNote, new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
                view.hideProgress();
                view.onNoteRemoved(selectedNote);
            }

            @Override
            public void onError(Throwable error) {
                view.hideProgress();
            }
        });
    }

    public void onUpdateAdded(Note note) {
        view.onNoteUpdated(note);
    }
}
