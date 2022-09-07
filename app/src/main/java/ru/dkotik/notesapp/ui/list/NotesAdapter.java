package ru.dkotik.notesapp.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.dkotik.notesapp.R;
import ru.dkotik.notesapp.domain.entity.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private List<Note> data = new ArrayList<>();
    
    private OnClick onClick;
    private Fragment fragment;

    public NotesAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setData(Collection<Note> notes) {
        data.clear();
        data.addAll(notes);
    }

    public int addItem(Note note) {
        data.add(note);
        return data.size();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = data.get(position);
        holder.getTitle().setText(note.getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public OnClick getOnClick() {
        return onClick;
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public int removeItem(Note selectedNote) {
        int index = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) != null && data.get(i).getId().equals(selectedNote.getId())) {
                index = i;
                break;
            }
        }
        data.remove(index);
        return index;
    }

    public int updateItem(Note selectedNote) {
        int index = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) != null && data.get(i).getId().equals(selectedNote.getId())) {
                index = i;
                break;
            }
        }
        data.set(index, selectedNote);
        return index;
    }

    public interface OnClick {
        void onClick(Note note);

        void onLongClick(Note note);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            fragment.registerForContextMenu(itemView);

            title = itemView.findViewById(R.id.note_title);

            itemView.findViewById(R.id.note_title).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Note note = data.get(getAdapterPosition());

                    if (getOnClick() != null) {
                        getOnClick().onClick(note);
                    }
                }
            });

            itemView.findViewById(R.id.note_title).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemView.showContextMenu();

                    Note note = data.get(getAdapterPosition());

                    if (note != null) {
                        if (getOnClick() != null) {
                            getOnClick().onLongClick(note);
                        }
                    }

                    return true;
                }
            });
        }

        public TextView getTitle() {
            return title;
        }
    }

}
