package ru.dkotik.notesapp.ui.list.impl;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import ru.dkotik.notesapp.R;
import ru.dkotik.notesapp.domain.entity.Note;
import ru.dkotik.notesapp.presenter.NotesListPresenter;
import ru.dkotik.notesapp.data.repository.impl.FirestoreNotesRepository;
import ru.dkotik.notesapp.ui.actions.AddNoteBottomSheetDialogFragment;
import ru.dkotik.notesapp.ui.actions.AddNotePresenter;
import ru.dkotik.notesapp.ui.actions.UpdateNotePresenter;
import ru.dkotik.notesapp.ui.list.NotesAdapter;
import ru.dkotik.notesapp.ui.list.NotesListView;

public class NotesListFragment extends Fragment implements NotesListView {

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String RESULT_KEY = "NotesListFragment_RESULT";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView notesContainer;
    private NotesListPresenter presenter;
    private NotesAdapter adapter;
    private ProgressBar progressBar;
    private Note selectedNote;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NotesListPresenter(this, FirestoreNotesRepository.INSTANCE);
        adapter = new NotesAdapter(this);

        adapter.setOnClick(new NotesAdapter.OnClick() {
            @Override
            public void onClick(Note note) {
                Bundle data = new Bundle();
                data.putParcelable(ARG_NOTE, note);
                getParentFragmentManager().setFragmentResult(RESULT_KEY, data);
            }

            @Override
            public void onLongClick(Note note) {
                selectedNote = note;
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
        progressBar = view.findViewById(R.id.progress);
        notesContainer = view.findViewById(R.id.notes_container);

        notesContainer.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        notesContainer.setAdapter(adapter);

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNoteBottomSheetDialogFragment.addInstance()
                        .show(getParentFragmentManager(), AddNoteBottomSheetDialogFragment.TAG);
            }
        });

        presenter.getAllNotes();
        getParentFragmentManager().setFragmentResultListener(AddNotePresenter.KEY, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note note = result.getParcelable(AddNotePresenter.ARG_NOTE);

                presenter.createNote(note);
            }
        });
        getParentFragmentManager().setFragmentResultListener(UpdateNotePresenter.KEY, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Note note = result.getParcelable(UpdateNotePresenter.ARG_NOTE);

                presenter.updateNote(note);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showNotes(List<Note> notes) {
        adapter.setData(notes);
        adapter.notifyDataSetChanged(); // полностью всё перериовать без анимашки
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onNoteAdded(Note note) {
        int index = adapter.addItem(note);

        adapter.notifyItemInserted(index - 1); // добавит анимациюю при добавлении

        notesContainer.smoothScrollToPosition(index - 1);
    }

    @Override
    public void onNoteRemoved(Note selectedNote) {

        int index = adapter.removeItem(selectedNote);

        adapter.notifyItemRemoved(index); // добавит анимацию при удалении
    }

    @Override
    public void onNoteUpdated(Note note) {
        int index = adapter.updateItem(selectedNote);

        adapter.notifyItemChanged(index); // добавит анимацию при обновлении
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.notes_list_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            presenter.removeNote(selectedNote);
            //Toast.makeText(requireContext(), "Удалена " + selectedNote.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.action_update) {
            //Toast.makeText(requireContext(), "Обновлена " + selectedNote.getTitle(), Toast.LENGTH_SHORT).show();
            AddNoteBottomSheetDialogFragment.updateInstance(selectedNote)
                    .show(getParentFragmentManager(), AddNoteBottomSheetDialogFragment.TAG);
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
