package ru.dkotik.notesapp.view.about;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.dkotik.notesapp.R;
import ru.dkotik.notesapp.model.Note;
import ru.dkotik.notesapp.view.CustomActions;
import ru.dkotik.notesapp.view.detail.NoteDetailFragment;

public class AboutFragment extends Fragment implements CustomActions {

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String IS_DRAWEL = "IS_DRAWEL";
    public static final String KEY_GO_BACK_FROM_DRAWER = "AboutFragment_KEY_GO_BACK_FROM_DRAWER";

    private FragmentManager fm;
    private Note note;

    public static AboutFragment newInstance(Note note) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        note = this.getArguments().getParcelable(ARG_NOTE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fm = getParentFragmentManager();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Bundle data = new Bundle();
        fm.setFragmentResult(IS_DRAWEL, data);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onBackPressed() {
        Bundle data = new Bundle();
        if (note != null) {
            data.putParcelable(ARG_NOTE, note);
        }
        fm.setFragmentResult(KEY_GO_BACK_FROM_DRAWER, data);
        return true;
    }
}