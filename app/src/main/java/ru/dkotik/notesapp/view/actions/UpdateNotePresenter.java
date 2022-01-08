package ru.dkotik.notesapp.view.actions;

import android.os.Bundle;

import ru.dkotik.notesapp.R;
import ru.dkotik.notesapp.model.Note;
import ru.dkotik.notesapp.repository.Callback;
import ru.dkotik.notesapp.repository.NotesRepository;

public class UpdateNotePresenter implements NotePresenter {

    public static final String KEY  = "AddNoteBottomSheetDialogFragment_UPDATENOTE";
    public static final String ARG_NOTE  = "ARG_NOTE";

    private AddNoteView view;
    private NotesRepository repository;
    private Note note;

    public UpdateNotePresenter(AddNoteView view, NotesRepository repository, Note note) {
        this.view = view;
        this.repository = repository;
        this.note = note;

        view.setActionButtonText(R.string.btn_update);

        view.setTitle(note.getTitle());
        view.setDescription(note.getDescription());
    }

    @Override
    public void onActionPressed(String title, String description) {
        view.showProgress();

        repository.update(note.getId(), title, description, new Callback<Note>() {
            @Override
            public void onSuccess(Note result) {
                view.hideProgress();
                Bundle bundle = new Bundle();
                bundle.putParcelable(ARG_NOTE, result);
                view.actionCompleted(KEY, bundle);
            }

            @Override
            public void onError(Throwable error) {
                view.hideProgress();
            }
        });
    }
}
