package ru.dkotik.notesapp.view.list.impl;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.dkotik.notesapp.R;
import ru.dkotik.notesapp.model.Note;
import ru.dkotik.notesapp.presenter.NotesListPresenter;
import ru.dkotik.notesapp.repository.impl.InMemoryNotesRepository;
import ru.dkotik.notesapp.view.list.NotesAdapter;
import ru.dkotik.notesapp.view.list.NotesListView;

public class NotesListFragment extends Fragment implements NotesListView {

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String RESULT_KEY = "NotesListFragment_RESULT";

    private RecyclerView notesContainer;
    private NotesListPresenter presenter;
    private NotesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NotesListPresenter(this, new InMemoryNotesRepository());
        adapter = new NotesAdapter();

        adapter.setOnClick(new NotesAdapter.OnClick() {
            @Override
            public void onClick(Note note) {
                //Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
                Bundle data = new Bundle();
                data.putParcelable(ARG_NOTE, note);
                getParentFragmentManager().setFragmentResult(RESULT_KEY, data);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notesContainer = view.findViewById(R.id.notes_container);

        notesContainer.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        notesContainer.setAdapter(adapter);

        presenter.refresh();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showNotes(List<Note> notes) {

        adapter.setData(notes);
        adapter.notifyDataSetChanged();

//        for (Note note : notes) {
//            View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_note, notesContainer, false);
//
//            itemView.setOnClickListener(v -> {
//                Bundle data = new Bundle();
//                data.putParcelable(ARG_NOTE, note);
//                getParentFragmentManager().setFragmentResult(RESULT_KEY, data);
//            });
//
//            TextView noteTitle = itemView.findViewById(R.id.note_title);
//            noteTitle.setText(note.getTitle());
//
//            notesContainer.addView(itemView);
//        }
    }
}
