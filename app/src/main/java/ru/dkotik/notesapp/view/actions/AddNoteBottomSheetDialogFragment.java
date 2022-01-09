package ru.dkotik.notesapp.view.actions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ru.dkotik.notesapp.R;
import ru.dkotik.notesapp.model.Note;
import ru.dkotik.notesapp.repository.impl.FirestoreNotesRepository;

public class AddNoteBottomSheetDialogFragment extends BottomSheetDialogFragment implements AddNoteView {

    public static final String TAG = "AddNoteBottomSheetDialogFragment";
    public static final String ARG_NOTE = "ARG_NOTE";

    public static AddNoteBottomSheetDialogFragment addInstance() {
        return new AddNoteBottomSheetDialogFragment();
    }
    public static AddNoteBottomSheetDialogFragment updateInstance(Note note) {
        AddNoteBottomSheetDialogFragment fragment = new AddNoteBottomSheetDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    private Button btnSave;
    private ProgressBar progressBar;
    private NotePresenter presenter;

    private EditText editTitle;
    private EditText editDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_note_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress);
        btnSave = view.findViewById(R.id.btn_save);

        editTitle = view.findViewById(R.id.title);
        editDescription = view.findViewById(R.id.description);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onActionPressed(editTitle.getText().toString(), editDescription.getText().toString());
            }
        });

        if (getArguments() == null) {
            //presenter = new AddNotePresenter(this, InMemoryNotesRepository.INSTANCE);
            presenter = new AddNotePresenter(this, FirestoreNotesRepository.INSTANCE);
        } else {
            Note note = getArguments().getParcelable(ARG_NOTE);
            presenter = new UpdateNotePresenter(this, FirestoreNotesRepository.INSTANCE, note);
            //presenter = new UpdateNotePresenter(this, InMemoryNotesRepository.INSTANCE, note);
        }


    }

    @Override
    public void showProgress() {
        btnSave.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        btnSave.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setActionButtonText(int title) {
        btnSave.setText(title);
    }

    @Override
    public void setTitle(String title) {
        this.editTitle.setText(title);
    }

    @Override
    public void setDescription(String description) {
        this.editDescription.setText(description);
    }

    @Override
    public void actionCompleted(String key, Bundle bundle) {
        getParentFragmentManager().setFragmentResult(key, bundle);

        dismiss();
    }
}
