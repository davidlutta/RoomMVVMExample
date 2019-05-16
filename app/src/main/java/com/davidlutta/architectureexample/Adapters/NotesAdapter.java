package com.davidlutta.architectureexample.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.davidlutta.architectureexample.Entities.Note;
import com.davidlutta.architectureexample.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends ListAdapter<Note,NotesAdapter.NoteViewHolder>{

    private OnItemClickListener listener;

    public NotesAdapter() {
        super(DIFF_CALLBACK);
    }

    //Comparison Logic for the notes list
    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item,parent,false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote = getItem(position);
        holder.titleTextView.setText(currentNote.getTitle());
        holder.descriptionTextView.setText(currentNote.getDescription());
        holder.priorityTextView.setText(String.valueOf(currentNote.getPriority()));
    }


    public Note getNoteAt(int position){
        return getItem(position);
    }


    class NoteViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView priorityTextView;


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            priorityTextView = itemView.findViewById(R.id.priorityTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClicked(getItem(position));
                    }
                }
            });
        }
    }


    public interface OnItemClickListener{
        void onItemClicked(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
