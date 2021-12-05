package ru.dkotik.notesapp.view.list.impl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import ru.dkotik.notesapp.R;
import ru.dkotik.notesapp.model.Note;
import ru.dkotik.notesapp.presenter.NotesListPresenter;
import ru.dkotik.notesapp.repository.InMemoryNotesRepository;
import ru.dkotik.notesapp.view.list.NotesListView;

public class NotesListFragment extends Fragment implements NotesListView {

    //
//    public interface OnNoteClicked {
//        void onNoteClicked(Note note);
//    }

    public static final String ARG_NOTE = "ARG_NOTE";
    public static final String RESULT_KEY = "NotesListFragment_RESULT";
    private LinearLayout notesContainer;
    private NotesListPresenter presenter;
    //private OnNoteClicked onNoteClicked;

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if (context instanceof  OnNoteClicked) {
//            onNoteClicked = (OnNoteClicked) context;
//        }
//    }

//    @Override
//    public void onDetach() {
//        onNoteClicked = null;
//        super.onDetach();
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NotesListPresenter(this, new InMemoryNotesRepository());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    // на данном этапе вью фрагмента уже создано
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // нужно получить ссылку на контейнет айтемов
        notesContainer = view.findViewById(R.id.notes_container);
        // для отображение списка заметок нужен notesContainer
        presenter.refresh();
    }

    @Override
    public void showNotes(List<Note> notes) {
        for (Note note : notes) {
            // во фрагменте не получится передат this во from()
            // мы либо получаем через getContext() - но если фрагмент не прикреплен к активити
            // но вернется null, либо юзаем requireContext(), который выбросит эксепшн, если null контект
            View itemView = LayoutInflater
                    .from(requireContext())
                    .inflate(R.layout.item_note, notesContainer, false);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle data = new Bundle();
                    data.putParcelable(ARG_NOTE, note);
                    // отдаем наружу данные при совершении события
                    // RESULT_KEY - ключ события, которое мы будем слушать из вне
                    getParentFragmentManager().setFragmentResult(RESULT_KEY, data);

                    //Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();

//                    Intent intent = new Intent(requireContext(), NoteDetailsActivity.class);
//                    intent.putExtra(NoteDetailsActivity.EXTRA_NOTE, note);
//                    startActivity(intent);

//                    if (onNoteClicked != null) {
//                        onNoteClicked.onNoteClicked(note);
//                    }
                }
            });

            TextView noteTitle = itemView.findViewById(R.id.note_title);
            noteTitle.setText(note.getTitle());

            notesContainer.addView(itemView);
        }
    }
}
