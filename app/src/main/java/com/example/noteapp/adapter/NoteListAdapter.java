package com.example.noteapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.noteapp.R;
import com.example.noteapp.models.Note;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteListViewHolder>{
    private ArrayList<Note> mDataset;
    private NoteListAdapter.NotesListInterface listInterface;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class NoteListViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView noteTitleTxtView;
        public TextView noteDescTxtView;
        public Button deleteBtn;
        public NoteListViewHolder(View view) {
            super(view);
            noteTitleTxtView = view.findViewById(R.id.noteTitle);
            noteDescTxtView = view.findViewById(R.id.noteDetails);
            deleteBtn = view.findViewById(R.id.deleteBtn);
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public NoteListAdapter(Context context, ArrayList<Note> myDataset) {
        super();
        mDataset = myDataset;
    }

    public void setNotesListInterface(NoteListAdapter.NotesListInterface listInterface){
        this.listInterface = listInterface;
    }


    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public NoteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);

        return new NoteListAdapter.NoteListViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull NoteListViewHolder holder,final int position) {
        Note note = mDataset.get(position);
        holder.noteDescTxtView.setText(note.getDetail());
        holder.noteTitleTxtView.setText(note.getTitle());

        //set onclick listener for the row
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note selectedNote = mDataset.get(position);
                listInterface.getSelectedNote(selectedNote);
            }
        });


        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listInterface.deleteNote(mDataset.get(position));
            }
        });
//
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public interface NotesListInterface {
         void getSelectedNote(Note selectedNote);
         void deleteNote(Note note);
    }

}



