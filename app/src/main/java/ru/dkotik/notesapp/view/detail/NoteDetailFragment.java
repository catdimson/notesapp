package ru.dkotik.notesapp.view.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import ru.dkotik.notesapp.R;
import ru.dkotik.notesapp.model.Note;
import ru.dkotik.notesapp.view.list.impl.NotesListFragment;

public class NoteDetailFragment extends Fragment {
    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String KEY_RESULT = "NoteDetailFragment_KEY_RESULT";
    private TextView noteTitle;
//    private ImageView noteImage;
    private TextView noteDate;
    private TextView noteDescription;

    public static NoteDetailFragment newInstance(Note note) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    // LayoutInflater - вызывается за нас с помощью "чудо-метода"
    public NoteDetailFragment() {
        super(R.layout.fragment_note_detail);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noteTitle = view.findViewById(R.id.note_title);
//        noteImage = view.findViewById(R.id.note_image);
        noteDate = view.findViewById(R.id.note_date);
        noteDescription = view.findViewById(R.id.note_description);


        if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {
            displayDetails(requireArguments().getParcelable(ARG_NOTE));
        }

        getParentFragmentManager()
                .setFragmentResultListener(KEY_RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = result.getParcelable(NotesListFragment.ARG_NOTE);
                        displayDetails(note);
                    }
                });
    }

    private void displayDetails(Note note) {
        noteTitle.setText(note.getTitle());
//        noteImage.setImageResource(note.getImage());
        noteDate.setText(note.getDate());
        noteDescription.setText(note.getDescription());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
