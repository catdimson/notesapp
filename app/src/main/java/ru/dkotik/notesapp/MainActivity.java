package ru.dkotik.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import ru.dkotik.notesapp.model.Note;
import ru.dkotik.notesapp.view.detail.NoteDetailFragment;
import ru.dkotik.notesapp.view.detail.NoteDetailsActivity;
import ru.dkotik.notesapp.view.list.impl.NotesListFragment;

public class MainActivity extends AppCompatActivity {//implements NotesListFragment.OnNoteClicked {

    private static final String ARG_NOTE = "ARG_NOTE";

    private Note selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_NOTE)) {
            selectedNote = savedInstanceState.getParcelable(ARG_NOTE);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                showDetails();
            }
        }

        getSupportFragmentManager().setFragmentResultListener(NotesListFragment.RESULT_KEY,this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        selectedNote = result.getParcelable(NotesListFragment.ARG_NOTE);

                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            showDetails();
                        } else {
                            // иначе открываем просто новый экран
                            Intent intent = new Intent(MainActivity.this, NoteDetailsActivity.class);
                            intent.putExtra(NoteDetailsActivity.EXTRA_NOTE, selectedNote);
                            startActivity(intent);
                        }
                    }
                }
        );
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (selectedNote != null) {
            outState.putParcelable(ARG_NOTE, selectedNote);
        }
    }

    private void showDetails() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(NoteDetailFragment.ARG_NOTE, selectedNote);
        getSupportFragmentManager()
                .setFragmentResult(NoteDetailFragment.KEY_RESULT, bundle);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.details_container, NoteDetailFragment.newInstance(selectedNote))
//                .commit();
    }
//    @Override
//    public void onNoteClicked(Note note) {
//        Intent intent = new Intent(this, NoteDetailsActivity.class);
//        intent.putExtra(NoteDetailsActivity.EXTRA_NOTE, note);
//        startActivity(intent);
//    }
}