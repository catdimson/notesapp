package ru.dkotik.notesapp.view.actions;

import android.os.Bundle;

import ru.dkotik.notesapp.R;
import ru.dkotik.notesapp.model.Note;
import ru.dkotik.notesapp.repository.Callback;
import ru.dkotik.notesapp.repository.NotesRepository;

public class AddNotePresenter implements NotePresenter {

    public static final String KEY  = "AddNoteBottomSheetDialogFragment_ADDNOTE";
    public static final String ARG_NOTE  = "ARG_NOTE";

    private AddNoteView view;
    private NotesRepository repository;

    public AddNotePresenter(AddNoteView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
        view.setActionButtonText(R.string.btn_save);
    }

    @Override
    public void onActionPressed(String title, String description) {
        view.showProgress();

        repository.save(title, description, new Callback<Note>() {
            @Override
            public void onSuccess(Note result) {
                view.hideProgress();
                Bundle bundle = new Bundle();
                bundle.putParcelable(ARG_NOTE, result);
                view.actionCompleted(KEY, bundle);
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }
}
