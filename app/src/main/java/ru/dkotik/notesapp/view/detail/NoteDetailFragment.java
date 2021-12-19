package ru.dkotik.notesapp.view.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import ru.dkotik.notesapp.view.CustomActions;
import ru.dkotik.notesapp.R;
import ru.dkotik.notesapp.model.Note;
import ru.dkotik.notesapp.view.list.impl.NotesListFragment;

public class NoteDetailFragment extends Fragment implements CustomActions {

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String KEY_RESULT = "NoteDetailFragment_KEY_RESULT";
    public static final String KEY_GO_TO_MAIN = "NoteDetailFragment_KEY_GO_TO_MAIN";

    private TextView noteTitle;
    private TextView noteDate;
    private TextView noteDescription;
    private FragmentManager fm;
    private Note note;

    public static NoteDetailFragment newInstance(Note note) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    public NoteDetailFragment() {
        super(R.layout.fragment_note_detail);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noteTitle = view.findViewById(R.id.note_title);
        noteDate = view.findViewById(R.id.note_date);
        noteDescription = view.findViewById(R.id.note_description);

        if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {
            displayDetails(requireArguments().getParcelable(ARG_NOTE));
        }

        fm = getParentFragmentManager();

        fm.setFragmentResultListener(KEY_RESULT, getViewLifecycleOwner(), (requestKey, result) -> {
                note = result.getParcelable(NotesListFragment.ARG_NOTE);
                displayDetails(note);
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void displayDetails(Note note) {
        noteTitle.setText(note.getTitle());
        noteDate.setText(note.getDate());
        noteDescription.setText(note.getDescription());
    }

    @Override
    public boolean onBackPressed() {
        Bundle data = new Bundle();
        data.putParcelable(ARG_NOTE, note);
        fm.setFragmentResult(KEY_GO_TO_MAIN, data);
        return true;
    }
}
