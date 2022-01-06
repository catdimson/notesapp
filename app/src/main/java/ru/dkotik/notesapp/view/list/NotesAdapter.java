package ru.dkotik.notesapp.view.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.dkotik.notesapp.R;
import ru.dkotik.notesapp.model.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private List<Note> data = new ArrayList<>();
    
    private OnClick onClick;

    public void setData(Collection<Note> notes) {
        data.clear();
        data.addAll(notes);
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

    public interface OnClick {
        void onClick(Note note);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

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
        }

        public TextView getTitle() {
            return title;
        }
    }

}
