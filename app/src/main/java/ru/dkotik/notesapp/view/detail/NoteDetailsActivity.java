package ru.dkotik.notesapp.view.detail;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import ru.dkotik.notesapp.R;
import ru.dkotik.notesapp.model.Note;

public class NoteDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE = "EXTRA_NOTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        // чтобы в landscape закрывалась активити (как должно быть короче)
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
        } else {
            // т.к андроид пытается восстановить состояние активити, который через фрагмент менеджер пытается
            // восстановить состояние фрагментов. Чтобы не перезатереть раннее состояние
            if (savedInstanceState == null) {

                FragmentManager fm = getSupportFragmentManager();

                Note note = getIntent().getParcelableExtra(EXTRA_NOTE);

                fm.beginTransaction()
                        .replace(R.id.detail_container, NoteDetailFragment.newInstance(note))
                        .commit();
            }
        }
    }
}